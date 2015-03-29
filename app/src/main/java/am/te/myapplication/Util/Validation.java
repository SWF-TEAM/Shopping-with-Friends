package am.te.myapplication.Util;

/**
 * Provides validation services for login with emails and passwords.
 *
 * @author Collin Caldwell
 * @version 1.0
 * @since 2015 February 7
 */
public class Validation {
    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    public static boolean arePasswordsSame(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
