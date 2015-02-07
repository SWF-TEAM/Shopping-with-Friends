package am.te.myapplication;

/**
 * Created by Collin on 2/7/15.
 */
public class Validation {
    protected static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    protected static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }
}
