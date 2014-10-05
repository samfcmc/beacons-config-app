package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import sam.com.beaconsconfigapp.models.Beacon;


public class NewBeaconActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 60;
    private BeaconsListAdapter beaconsListAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_beacon);

        this.beaconsListAdapter = new BeaconsListAdapter(this);
        this.listView = (ListView) findViewById(R.id.new_beacon_beacons_listview);
        this.listView.setAdapter(this.beaconsListAdapter);

        checkBluetoothLE();
        initBluetooth();
        scanBLEDevices();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_beacon, menu);
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
        return super.onOptionsItemSelected(item);
    }

    private void checkBluetoothLE() {
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initBluetooth() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();

        if(this.bluetoothAdapter == null || !this.bluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }
    }

    private void scanBLEDevices() {
        this.bluetoothAdapter.startLeScan(new BeaconsScanCallback());
    }

    private class BeaconsListAdapter extends ArrayAdapter<Beacon> {
        private BeaconsListAdapter(Context context) {
            super(context, R.layout.beacon_list_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.beacon_list_item, parent, false);
            }
            Beacon beacon = getItem(position);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.beacon_list_item_name_textview);
            TextView idTextView = (TextView) convertView.findViewById(R.id.beacon_list_item_id_textview);

            nameTextView.setText(beacon.getDevice().getName());
            idTextView.setText(beacon.getUuidAsString());

            return convertView;
        }
    }

    private class BeaconsScanCallback implements BluetoothAdapter.LeScanCallback {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, final byte[] bytes) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    beaconsListAdapter.add(new Beacon(bluetoothDevice, bytes));
                    beaconsListAdapter.notifyDataSetChanged();
                }
            });

        }
    }
}
