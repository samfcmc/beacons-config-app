package sam.com.beaconsconfigapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import sam.com.beaconsconfigapp.models.Beacon;

/**
 * SimBeaconScanner: Scanner for simulated beacons
 */
public class SimBeaconScanner implements BeaconScanner<Beacon> {
    private BluetoothAdapter bluetoothAdapter;

    public SimBeaconScanner(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public void startScan(final BeaconCallback<Beacon> beaconCallback) {
        this.bluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                beaconCallback.onBeaconFound(new Beacon(bluetoothDevice, bytes));
            }
        });
    }
}
