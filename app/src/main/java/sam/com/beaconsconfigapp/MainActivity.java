package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class MainActivity extends Activity implements AskLoginFragment.OnFragmentInteractionListener, MyBeaconsFragment.OnFragmentInteractionListener {

    private BeaconConfigApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.application = (BeaconConfigApplication) getApplication();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
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
