package models;

public class PasswordModel {
    private int id;
    private String hash;
    private byte[] salt;

    public PasswordModel(int id, String hash, byte[] salt) {
        this.id = id;
        this.hash = hash;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
