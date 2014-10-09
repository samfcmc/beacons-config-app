package sam.com.beaconsconfigapp;

import android.app.Application;

import sam.com.beaconsconfigapp.storage.KinveyWebStorage;
import sam.com.beaconsconfigapp.storage.WebStorage;

/**
 * sam.com.beaconsconfigapp.BeaconConfigApplication
 */
public class BeaconConfigApplication extends Application {
    private WebStorage webStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        this.webStorage = new KinveyWebStorage(this.getApplicationContext());
    }

    public WebStorage getWebStorage() {
        return webStorage;
    }
}
