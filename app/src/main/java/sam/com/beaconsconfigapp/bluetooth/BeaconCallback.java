package sam.com.beaconsconfigapp.bluetooth;

import sam.com.beaconsconfigapp.models.Beacon;

/**
 * Callback for BeaconScanner
 */
public interface BeaconCallback<T> {
    public void onBeaconFound(T beacon);
}
