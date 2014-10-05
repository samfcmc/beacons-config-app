package sam.com.beaconsconfigapp.models;

import android.bluetooth.BluetoothDevice;

/**
 * Beacon
 */
public class Beacon {
    private BluetoothDevice device;
    private byte[] uuid;
    private byte[] major;
    private byte[] minor;
    private byte power;

    public Beacon(BluetoothDevice device, byte[] uuid, byte[] major, byte[] minor, byte power) {
        this.device = device;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.power = power;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public byte[] getMajor() {
        return major;
    }

    public byte[] getMinor() {
        return minor;
    }

    public byte getPower() {
        return power;
    }

}
