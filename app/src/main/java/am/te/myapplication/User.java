package am.te.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * The user class represents a user in the app.
 *
 * @author Mike Adkison, Mitchell Manguno
 * @since 2015 March 01
 * @version 1.2
 */
public class User {

    public static User loggedIn;
    private String username;
    private String password;
    private String email;
    private List<User> friendList;
    private List<Product> itemList;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        friendList = new ArrayList<>();
        itemList = new ArrayList<>();
    }


    public User(String username, String password) {
        this(username, password, null);
    }

    /* Getters and Setters */
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<User> getFriends() {
        return friendList;
    }

    public List<Product> getItemList() {
        return itemList;
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

    public void setFriendList(List friendList) {
        this.friendList = friendList;
    }

    public void setItemList(List itemList) {
        this.itemList = itemList;
    }

    /* Friend List Operations */
    public boolean hasFriends() {
        return !friendList.isEmpty();
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
        return friendList.contains(user);
    }

    /* Item List Operations */
    public boolean hasItems() {
        return !itemList.isEmpty();
    }

    public boolean addItem(Product newProduct) {
        if (!itemList.contains(newProduct)) {
            itemList.add(newProduct);
            return true;
        }
        return false;
    }

    public Product getProduct(Product product) {
        return hasItem(product) ? itemList.get(itemList.indexOf(product)) : null;
    }


    public boolean hasItem(Product product) {
        return itemList.contains(product);
    }

    /* Object Overrides */
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

}
