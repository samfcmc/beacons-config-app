package sam.com.beaconsconfigapp.storage;

/**
 * Web Storage Interface
 */
public interface WebStorage {
    void ping(WebStorageCallback<Boolean> callback);
}
