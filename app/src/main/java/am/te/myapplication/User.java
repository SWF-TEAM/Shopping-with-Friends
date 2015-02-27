package am.te.myapplication;

import java.util.ArrayList;
import java.util.Random;

/**
 * The user class represents a user in the app.
 *
 * @author Mike Adkison, Mitchell Manguno
 * @since 2015 February 19
 */
public class User {

    public static User loggedIn;
    private String username;
    private String password;
    private String email;
    private ArrayList<User> friendList;
    private int rating;
    private int salesReports;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        Random rand = new Random();
        this.rating = rand.nextInt(10);
        this.salesReports = rand.nextInt(1000);
        friendList = new ArrayList<>();
    }


    public User(String username, String password) {
        this(username, password, null);
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasFriends() {
        return !friendList.isEmpty();
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }

        if (other == this) {
            return true;
        }
        User theOther = (User) other;
        return theOther.getUsername().equals(this.getUsername()) && theOther.getPassword().equals(this.getPassword());
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash += 37 * hash + getUsername().hashCode();
        hash += 37 * hash + getPassword().hashCode();
        return hash;
    }
    @Override
    public String toString() {
        return getUsername();
    }

    public boolean addFriend(User newFriend) {
        if (!friendList.contains(newFriend)) {
            friendList.add(newFriend);
            return true;
        }
        return false;
    }

    public User getFriend(User user) {
        return isFriendsWith(user) ? friendList.get(friendList.indexOf(user)) : null;
    }

    public boolean isFriendsWith(User user) {
        if(user == null) { System.out.println("ITS NULL, BITCH");}
        return friendList.contains(user);
    }

    public ArrayList<User> getFriends() {
        return friendList;
    }

    public String getDetails() {
        return username + '\n' + email;
    }

    public int getRating() {
        return rating;
    }

    public int getSalesReports(){
        return salesReports;
    }

    public User getFriend(String username) {
        for (User friend: friendList) {
            if (username.equals(friend.username)) {
                System.out.println("Name");
                return friend;
            }
        }
        return null;
    }
    
    public void removeFriend(User user) {
        friendList.remove(user);
    }


}

