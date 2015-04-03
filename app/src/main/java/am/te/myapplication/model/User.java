package am.te.myapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The user class represents a user in the app.
 *
 * @author Mike Adkison, Mitchell Manguno
 * @since 2015 March 22
 * @version 2.0
 */
public class User extends Agent {

    private final List<User> friendList;
    private final List<Listing> itemList;
    private final String description;
    private int rating;

    /**
     * Creates a User object with a given username, password, email, id
     * description, and name.
     *
     * @param username the User's username
     * @param password the User's password
     * @param email the User's email
     * @param id the User's id
     * @param name the User's name
     */
    public User(String username, String password, String email, String id,
                String description, String name) {
        System.out.println("SETTING THE USERNAME: " + username);
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
        super.setId(id);
        this.description = description;
        super.setName(name);
        friendList = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    public List<User> getFriends() {
        return friendList;
    }

    public List<Listing> getItemList() {
        return itemList;
    }

    public int getRating() {
        return rating;
    }


    public boolean hasFriends() {
        return !friendList.isEmpty();
    }

    public void addFriend(User newFriend) {
        if (!friendList.contains(newFriend)) {
            friendList.add(newFriend);
        }
    }

    public User getFriend(String username) {
        for (User friend: friendList) {
            if (username.equals(friend.getUsername())) {
                System.out.println("Name");
                return friend;
            }
        }
        return null;
    }

    public void removeFriend(User user) {
        friendList.remove(user);
    }


    public boolean hasItems() {
        return !itemList.isEmpty();
    }

    public void addItem(Listing newListing) {
        if (!itemList.contains(newListing)) {
            itemList.add(newListing);
        }
    }

    public Listing getListing(String listing) {
        for (Listing aListing: itemList) {
            if (listing.equals(aListing.getName())) {
                return aListing;
            }
        }
        return null;
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
        return theOther.getUsername().equals(this.getUsername()) &&
            theOther.getPassword().equals(this.getPassword());
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
