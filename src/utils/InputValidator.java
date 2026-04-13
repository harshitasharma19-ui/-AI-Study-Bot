
package utils;

public class InputValidator {

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 20;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    public static boolean isValidNumber(String input, int min, int max) {
        try {
            int num = Integer.parseInt(input);
            return num >= min && num <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}