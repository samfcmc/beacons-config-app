package sam.com.beaconsconfigapp.models;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Beacon
 */
public class Beacon implements Parcelable {
    public static final int BYTES_UUID = 16;
    public static final int BYTES_MAJOR = 2;
    public static final int BYTES_MINOR = 2;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeByteArray(this.uuid);
        parcel.writeByteArray(this.major);
        parcel.writeByteArray(this.minor);
        parcel.writeByte(this.power);
    }

    public static final Creator<Beacon> CREATOR = new Creator<Beacon>() {

        @Override
        public Beacon createFromParcel(Parcel parcel) {
            return new Beacon(parcel);
        }

        @Override
        public Beacon[] newArray(int i) {
            return new Beacon[0];
        }
    };

    private Beacon(Parcel parcel) {
        this.uuid = new byte[BYTES_UUID];
        this.major = new byte[BYTES_MAJOR];
        this.minor = new byte[BYTES_MINOR];

        this.device = parcel.readParcelable(null);
        parcel.readByteArray(this.uuid);
        parcel.readByteArray(this.major);
        parcel.readByteArray(this.minor);
        this.power = parcel.readByte();
    }
}
