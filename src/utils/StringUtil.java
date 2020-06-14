package utils;

public final class StringUtil {
    private StringUtil() {
    }


    public static String formatName(String string) {
        String result = String.valueOf(string.charAt(0)).toUpperCase();

        if (string.contains(" ")) {
            result = result.concat(string.substring(1, string.indexOf(" ")));
            result = result.concat(String.valueOf(string.indexOf(" ") + 1).toUpperCase());
        }

        result = result.concat(string.substring(1).toLowerCase());

        return result;
    }

    public static boolean isValidName(String name){
        return name.matches("([A-Za-z]+ [A-Za-z]+)|([A-Za-z]+)$");
    }

    public static boolean isValidSurname(String surname){
        return isValidName(surname);
    }

    public static boolean isValidZip(String zip){
        return zip.matches("[(0-9)]{5}$");
    }

    public static boolean isValidPhone(String phoneNumber){
        return phoneNumber.matches("[(0-9)]{9,10}$");
    }

    public static boolean isValidEmail(String email){
        return email.matches("[a-zA-Z.0-9]+@[a-zA-Z]+\\.[a-z]{2,3}$");
    }

}
