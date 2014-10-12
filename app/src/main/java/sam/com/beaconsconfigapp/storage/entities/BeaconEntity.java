package sam.com.beaconsconfigapp.storage.entities;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * BeaconEntity: Entity that represents Beacons json objects
 */
public class BeaconEntity extends GenericJson {

    @Key("_id")
    private String id;

    @Key
    private String name;

    @Key
    private byte[] uuid;

    @Key
    private byte[] major;

    @Key
    private byte[] minor;

    @Key
    private String content;

    public BeaconEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

    public byte[] getMajor() {
        return major;
    }

    public void setMajor(byte[] major) {
        this.major = major;
    }

    public byte[] getMinor() {
        return minor;
    }

    public void setMinor(byte[] minor) {
        this.minor = minor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
