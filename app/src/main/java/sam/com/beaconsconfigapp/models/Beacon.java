package sam.com.beaconsconfigapp.models;

import android.bluetooth.BluetoothDevice;

/**
 * Beacon
 */
public class Beacon {
    private BluetoothDevice device;
    private byte[] uuid;

    public Beacon(BluetoothDevice device, byte[] uuid) {
        this.device = device;
        this.uuid = uuid;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public String getUuidAsString() {
        String uuid = "";
        for(byte b : this.uuid) {
            String value = Integer.toHexString(b & 0xFF);
            if(b < 10) {
                value = "0" + value;
            }
            uuid += value + ":";
        }
        return uuid;
    }
}
