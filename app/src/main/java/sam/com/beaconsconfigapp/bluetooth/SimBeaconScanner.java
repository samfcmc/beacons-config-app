package sam.com.beaconsconfigapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import sam.com.beaconsconfigapp.models.Beacon;

/**
 * SimBeaconScanner: Scanner for simulated beacons
 */
public class SimBeaconScanner implements BeaconScanner<Beacon> {

    private BluetoothAdapter bluetoothAdapter;
    private static final int BYTES_IGNORE = 9;
    private static final int BYTES_UUID = 16;
    private static final int BYTES_MAJOR = 2;
    private static final int BYTES_MINOR= 2;

    public SimBeaconScanner(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public void startScan(final BeaconCallback<Beacon> beaconCallback) {
        this.bluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                byte[] uuid = new byte[BYTES_UUID];
                byte[] major = new byte[BYTES_MAJOR];
                byte[] minor = new byte[BYTES_MINOR];
                int uuidIndex = BYTES_IGNORE;
                int majorIndex = uuidIndex + BYTES_UUID;
                int minorIndex = majorIndex + BYTES_MAJOR;
                int powerIndex = minorIndex + BYTES_MINOR;

                System.arraycopy(bytes, uuidIndex, uuid, 0, BYTES_UUID);
                System.arraycopy(bytes, majorIndex, major, 0, BYTES_MAJOR);
                System.arraycopy(bytes, minorIndex, minor, 0, BYTES_MINOR);
                byte power = bytes[powerIndex];

                beaconCallback.onBeaconFound(new Beacon(bluetoothDevice, uuid, major, minor, power));
            }
        });
    }

    @Override
    public void stopScan() {
        this.bluetoothAdapter.stopLeScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {

            }
        });
    }


}
