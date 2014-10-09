package sam.com.beaconsconfigapp.storage;

import android.content.Context;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.java.core.KinveyClientCallback;

/**
 * Kinvey Web Storage: Implementation of web storage for Kinvey
 */
public class KinveyWebStorage implements WebStorage {
    private static final String appKey = "";
    private static final String appSecret = "";
    private final Client client;

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
}
