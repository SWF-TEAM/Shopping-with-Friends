package am.te.myapplication;

import java.util.ArrayList;

/**
 * Created by elimonent on 2/9/15.
 */
public class User {
    public static User loggedIn;
    private String username;
    private String password;
    private ArrayList<User> friendList;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        friendList = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String pass) {
        this.password = pass;
    }
    public void setUsername(String username) {
        this.username = username;
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
        return isFriend(user) ? friendList.get(friendList.indexOf(user)) : null;
    }

    public boolean isFriend(User user) {
        return friendList.contains(user);
    }

    public ArrayList<User> getFriends() {
        return friendList;
    }
}

