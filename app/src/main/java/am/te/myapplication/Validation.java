package am.te.myapplication;

import java.util.PriorityQueue;

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

    protected static boolean arePasswordsSame(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
