package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import sam.com.beaconsconfigapp.models.Beacon;
import sam.com.beaconsconfigapp.storage.WebStorageCallback;
import sam.com.beaconsconfigapp.storage.entities.BeaconEntity;


public class MainActivity extends Activity implements AskLoginFragment.OnFragmentInteractionListener,
        MyBeaconsFragment.OnFragmentInteractionListener, ScanBeaconsFragment.OnFragmentInteractionListener,
        ConfigBeaconFragment.OnFragmentInteractionListener{

    private BeaconConfigApplication application;

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private BeaconEntity selectedBeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.application = (BeaconConfigApplication) getApplication();
        this.progressDialog = new ProgressDialog(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        initAlertDialog();
    }

    private void initAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String message = getString(R.string.my_beacons_delete_dialog_message);

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBeacon();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.alertDialog.dismiss();
            }
        });

        this.alertDialog = alertDialogBuilder.create();
    }

    private void deleteBeacon() {
        this.application.getWebStorage().deleteBeacon(this.selectedBeacon, new WebStorageCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                String message = getString(R.string.error_beacon_delete);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Void response) {
                String message = getString(R.string.success_beacon_delete);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                updateFragment();
            }
        });
    }

    private void updateFragment() {
        if(this.application.getWebStorage().isUserLoggedIn()) {
            changeFragment(MyBeaconsFragment.newInstance());
        }
        else {
            changeFragment(AskLoginFragment.newInstance());
        }
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_logout) {
            logout();
            return true;
        }

        if(id == R.id.action_my_beacons) {
            goToMyBeacons();
            return true;
        }

        if(id == R.id.action_add_beacon) {
            changeFragment(ScanBeaconsFragment.newInstance());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMyBeacons() {
        changeFragment(MyBeaconsFragment.newInstance());
    }

    private void logout() {
        this.application.getWebStorage().logout();
        updateFragment();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onMyBeaconsLongClick(BeaconEntity beacon) {
        this.selectedBeacon = beacon;
        this.alertDialog.show();
    }

    @Override
    public void onBeaconSelected(Beacon beacon) {
        changeFragment(ConfigBeaconFragment.newInstance(beacon));
    }

    @Override
    public void deviceDoesNotSupportBLE() {

    }

    @Override
    public void onConfigBeaconCancel() {
        updateFragment();
    }

    @Override
    public void onConfigBeaconDone(Beacon beacon, String name, String description, String url) {
        // Progress dialog
        String loadingMessage = getString(R.string.loading_config_beacon);
        this.progressDialog.setMessage(loadingMessage);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();

        BeaconEntity beaconEntity = new BeaconEntity();
        String uuid = Utils.byteArrayToHexString(beacon.getUuid());
        String major = Utils.byteArrayToHexString(beacon.getMajor());
        String minor = Utils.byteArrayToHexString(beacon.getMinor());

        beaconEntity.setName(name);
        beaconEntity.setDescription(description);
        beaconEntity.setUuid(uuid);
        beaconEntity.setMajor(major);
        beaconEntity.setMinor(minor);
        beaconEntity.setContent(url);

        this.application.getWebStorage().configBeacon(beaconEntity, new WebStorageCallback<BeaconEntity>() {
            @Override
            public void onFailure(Throwable throwable) {
                error();
            }

            @Override
            public void onSuccess(BeaconEntity response) {
                onBeaconConfigured();
            }
        });
    }

    private void error() {
        this.progressDialog.dismiss();
        String error = getString(R.string.error_config_beacon);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void onBeaconConfigured() {
        this.progressDialog.dismiss();
        String message = getString(R.string.success_config_beacon);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        updateFragment();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private BeaconConfigApplication application;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            application = (BeaconConfigApplication) getActivity().getApplication();

            if(!userLoggedIn()) {
                goToLoginActivity();
            }
        }

        private boolean userLoggedIn() {
            return this.application.getWebStorage().isUserLoggedIn();
        }

        private void goToLoginActivity() {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
