package models;

public class CredentialModel {
    private int id;
    private String email;
    private String hash;
    private byte[] salt;

    public CredentialModel(int id, String email, String hash, byte[] salt) {
        this.id = id;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
