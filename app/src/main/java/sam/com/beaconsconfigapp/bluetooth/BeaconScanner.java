package sam.com.beaconsconfigapp.bluetooth;

/**
 * BeaconScanner: Interface for scanning BLE devices.
 */
public interface BeaconScanner<T> {
    public void startScan(BeaconCallback<T> beaconCallback);
}
