package sam.com.beaconsconfigapp.storage;

import sam.com.beaconsconfigapp.storage.entities.BeaconEntity;

/**
 * Web Storage Interface
 */
public interface WebStorage {
    void ping(WebStorageCallback<Boolean> callback);
    void login(String username, String password, WebStorageCallback<Void> callback);
    boolean isUserLoggedIn();
    void logout();
    void getBeacons(WebStorageCallback<BeaconEntity[]> callback);
}
