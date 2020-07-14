package models;

public class CredentialModel {
    private int id;
    private String credential;
    private String hash;
    private byte[] salt;
    private int type;

    public CredentialModel(int id, String credential, String hash, byte[] salt, int type) {
        this.id = id;
        this.credential = credential;
        this.hash = hash;
        this.salt = salt;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
