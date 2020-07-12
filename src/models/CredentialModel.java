package models;

public class CredentialModel {
    private int id;
    private String credential;
    private String hash;
    private byte[] salt;

    public CredentialModel(int id, String credential, String hash, byte[] salt) {
        this.id = id;
        this.credential = credential;
        this.hash = hash;
        this.salt = salt;
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
}
