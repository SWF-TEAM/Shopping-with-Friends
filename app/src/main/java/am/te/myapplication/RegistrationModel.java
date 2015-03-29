package am.te.myapplication;
import java.util.ArrayList;

import am.te.myapplication.Model.User;


/**
 * Serves as a local model of the database to hold user.
 *
 * @author Mike Adkison
 * @version 1.0
 * @since 2015 February 9
 */
class RegistrationModel {

    private static final ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }
}
