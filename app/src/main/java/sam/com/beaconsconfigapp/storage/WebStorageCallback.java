package sam.com.beaconsconfigapp.storage;

/**
 * Web Storage Callback
 */
public interface WebStorageCallback<T> {
    public void onFailure(Throwable throwable);
    public void onSuccess(T response);
}
