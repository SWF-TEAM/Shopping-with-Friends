package am.te.myapplication.util;

/**
 * Provides validation services for login with emails and passwords.
 *
 * @author Mitchell Manguno
 * @version 2.0
 * @since 2015 March 28
 */
public class Validation {

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 4
                                && password.matches("[a-zA-Z0-9]++");
    }

    public static boolean isEmailValid(String email) {
        if (email != null) {
            int dot = email.lastIndexOf('.');
            String dom = email.substring(dot);
            return email.matches("[a-zA-Z]++@[a-zA-Z]++.[a-zA-Z]++")
                   && (dom.equals(".com") || dom.equals(".net")
                    || dom.equals(".edu") || dom.equals(".gov")
                    || dom.equals(".mil") || dom.equals("org"));
        }
        return false;
    }

    public static boolean arePasswordsSame(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
