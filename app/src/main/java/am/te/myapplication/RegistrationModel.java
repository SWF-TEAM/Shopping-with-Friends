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
public class RegistrationModel {

    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }
    public static boolean addUser(User user) {
       for(User currUser: getUsers()) {
           if (currUser.getUsername().equals(user.getUsername())) {
               return false;
           }
       }
       users.add(user);
       return true;

    }
    public static void removeUser(User user) {
        users.remove(user);
    }
}
