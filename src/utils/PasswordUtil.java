package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class PasswordUtil {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final String ALGORITHM = "SHA-256";

    private PasswordUtil() {
    }

    public static boolean checkPassword(String password, byte[] salt, String hash) {
        return hash.equals(generateHash(password, salt));
    } //in pagina di login

    public static String generateHash(String password, byte[] salt) {
        String hashString = null;
        System.out.println("Generating hash with SHA-256...");
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(salt);
            byte[] hash = digest.digest(password.getBytes());
            assert hash != null;
            hashString = bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashString;
    }

    public static byte[] createSalt() {
        System.out.println("Generating salt...");
        byte[] bytes = new byte[25];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
