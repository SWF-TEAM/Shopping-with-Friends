package am.te.myapplication.Model;

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
    private List<Listing> itemList;
    private int rating;
    private int salesReports;
    private String id;
    private String description;
    private String name;

    public User(String username, String password, String email, String id, String description, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
        this.description = description;
        this.name = name;
        /*Random rand = new Random();
        this.rating = rand.nextInt(10);
        this.salesReports = rand.nextInt(1000);*/
        friendList = new ArrayList<>();
        itemList = new ArrayList<>();
    }


    public User(String username, String password) {
        this(username, password, null, null, null, null);
    }

    /* Getters and Setters */
    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getPassword() {
        return password;
    }

    public String getName() {return name;}

    public String getEmail() {
        return email;
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

    public int getSalesReports() {
        return salesReports;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSalesReports(int salesReports) {
        this.salesReports = salesReports;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(String id) {
        this.id = id;
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

    /* Item List Operations */
    public boolean hasItems() {
        return !itemList.isEmpty();
    }

    public boolean addItem(Listing newProduct) {
        if (!itemList.contains(newProduct)) {
            itemList.add(newProduct);
            return true;
        }
        return false;
    }

    public Listing getListing(String product) {
        for (Listing listing: itemList) {
            if (product.equals(listing.getName())) {
                return listing;
            }
        }
        return null;
    }


    public boolean hasItem(Listing product) {
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
