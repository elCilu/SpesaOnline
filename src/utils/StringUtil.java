package utils;

public final class StringUtil {
    private StringUtil() {
    }

    public static String formatName(String string) {
        String result = String.valueOf(string.charAt(0)).toUpperCase();

        result = result.concat(string.substring(1).toLowerCase());

        return result;
    }

}
