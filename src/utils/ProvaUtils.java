package utils;

public class ProvaUtils {
    public static void main(String[] args) {
        try {
            byte[] salt = HashUtil.createSalt();
            String hash = HashUtil.generateHash("provapassword", salt);
            System.out.println(hash);
            System.out.println(HashUtil.checkPassword("altrapass", salt, hash));
            System.out.println(HashUtil.checkPassword("provapassworD", salt, hash));
            System.out.println(HashUtil.checkPassword("provapassword", salt, hash));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
