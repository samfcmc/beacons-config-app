package sam.com.beaconsconfigapp;

/**
 *
 */
public class Utils {
    public static String byteArrayToString(byte[] bytes) {
        String s = "";
        for(byte b : bytes) {
            String value = byteToString(b);
            s += value + ":";
        }
        return s;
    }

    public static String byteToString(byte b) {
        return String.format("%02X", b & 0xFF).toLowerCase();
    }

    public static String byteArrayToHexString(byte[] bytes) {
        String s = "";

        for(byte b: bytes) {
            String value = byteToString(b);
            s += value;
        }

        return s;
    }
}
