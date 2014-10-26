package sam.com.beaconsconfigapp.storage;

import android.content.Context;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyDeleteCallback;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.model.KinveyDeleteResponse;

import sam.com.beaconsconfigapp.storage.entities.BeaconEntity;

/**
 * Kinvey Web Storage: Implementation of web storage for Kinvey
 */
public class KinveyWebStorage implements WebStorage {
    private final Client client;

    private static final String COLLECTION_NAME = "beacons";
    public KinveyWebStorage(Context context) {
        this.client = new Client.Builder(context.getApplicationContext()).build();
    }

    @Override
    public void ping(final WebStorageCallback<Boolean> callback) {
        this.client.ping(new KinveyPingCallback() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                callback.onSuccess(aBoolean);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    @Override
    public void login(String username, String password, final WebStorageCallback<Void> callback) {
        this.client.user().login(username, password, new KinveyClientCallback<User>() {
            @Override
            public void onSuccess(User user) {
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.client.user().isUserLoggedIn();
    }

    @Override
    public void logout() {
        this.client.user().logout().execute();
    }

    public void getBeacons(final WebStorageCallback<BeaconEntity[]> callback) {
        AsyncAppData<BeaconEntity> beacons = this.client.appData(COLLECTION_NAME, BeaconEntity.class);

        beacons.get(new Query(), new KinveyListCallback<BeaconEntity>() {
            @Override
            public void onSuccess(BeaconEntity[] beaconEntities) {
                callback.onSuccess(beaconEntities);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    @Override
    public void configBeacon(BeaconEntity beacon, final WebStorageCallback<BeaconEntity> callback) {
        AsyncAppData<BeaconEntity> beacons = this.client.appData(COLLECTION_NAME, BeaconEntity.class);
        beacons.save(beacon, new KinveyClientCallback<BeaconEntity>() {
            @Override
            public void onSuccess(BeaconEntity beaconEntity) {
                callback.onSuccess(beaconEntity);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    @Override
    public void deleteBeacon(BeaconEntity beacon, final WebStorageCallback<Void> callback) {
        AsyncAppData<BeaconEntity> appData = this.client.appData(COLLECTION_NAME, BeaconEntity.class);
        appData.delete(beacon.getId(), new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse kinveyDeleteResponse) {
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }
}
