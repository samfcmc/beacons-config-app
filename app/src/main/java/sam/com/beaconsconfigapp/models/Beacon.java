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
            String value = String.format("%02X", b & 0xFF);
            uuid += value + ":";
        }
        return uuid;
    }
}
