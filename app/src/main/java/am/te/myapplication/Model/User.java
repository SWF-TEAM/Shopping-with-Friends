package am.te.myapplication.Model;

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

    private List<User> friendList;
    private List<Listing> itemList;
    private List<SalesReport> salesReports;
    private String description;
    private int rating;

    /**
     * Creates a User object with a given username, password, email, id
     * description, and name.
     *
     * @param username the User's username
     * @param password the User's password
     * @param email the User's email
     * @param id the User's id
     * @param description the User's description
     * @param name the User's name
     */
    public User(String username, String password, String email, String id,
                String description, String name) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setId(id);
        this.setName(name);
        this.description = description;
        friendList = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    /**
     * Creates a User object with a given username and password.
     *
     * @param username the User's username
     * @param password the User's password
     */
    public User(String username, String password) {
        this(username, password, null, null, null, null);
    }

    /**
     * Get's this User's description.
     *
     * @return this user's description.
     */
    public String getDescription() {
        return description;
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

    public List<SalesReport> getSalesReports() {
        return salesReports;
    }

    public void setFriendList(List friendList) {
        this.friendList = friendList;
    }

    public void setItemList(List itemList) {
        this.itemList = itemList;
    }

    public void setRating(int rating) {
        if (rating > 0 && rating < 6) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setSalesReports(List<SalesReport> salesReports) {
        this.salesReports = salesReports;
    }

    public void setDescription(String description) {
        this.description = description;
    }


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
        if (isFriendsWith(user)) {
            return friendList.get(friendList.indexOf(user));
        }

        return null;
    }

    public boolean isFriendsWith(User user) {
        return friendList.contains(user);
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


    public boolean hasSalesReports() {
        return !salesReports.isEmpty();
    }

    public boolean addSalesReport(SalesReport newReport) {
        if (!salesReports.contains(newReport)) {
            salesReports.add(newReport);
            return true;
        }
        return false;
    }

    public boolean hasSalesReport(SalesReport salesReport) {
        return salesReports.contains(salesReport);
    }

    public int getSalesReportNumber() {
        if (hasSalesReports()) {
            return salesReports.size();
        }

        return 0;
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
