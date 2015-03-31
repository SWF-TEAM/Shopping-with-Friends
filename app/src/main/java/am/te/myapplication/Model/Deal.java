package am.te.myapplication.Model;

import android.support.annotation.NonNull;

/**
 * Defines a deal that meets the price threshold of a listing.
 *
 * @author Mitchell Manguno, Collin Caldwell
 * @version 1.1
 * @since 2015 March 12
 */
public class Deal implements Comparable<Deal> {
    private String listingID;
    private String id;

    private String name;
    private String description;
    private double price;
    private String location;
    private boolean claimed;

    /**
     * Constructor for the Deal class
     *
     * @param listingID id of the listing which is associated with the deal
     * @param id id of the deal
     *
     * @param name name of the deal
     * @param description description of the deal
     * @param price price of the deal
     * @param location latitude/longitude of the deal
     * @param claimed whether or not the deal has been claimed
     **/
    public Deal(String listingID, String id, String name, String description, double price,
                String location, boolean claimed) {
        this.listingID = listingID;
        this.id = id;

        this.name = name;
        this.description = description;
        this.price = price;
        this.location = location;
        this.claimed = claimed;
    }

    /**
     * Constructor for deal class that generates ID code in java, rather than on php server code
     * @param listingID id of the listing which is associated with the deal
     * @param name name of the deal
     * @param price price of the deal
     * @param location latitude/longitude of the deal
     */
    public Deal(String listingID, String name, double price, String location) {
        this(listingID, "", name, "", price, location, false);
        id = "D" + this.hashCode();
    }

    //getters
    public String getListingID(){
        return listingID;
    }
    public String getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public String getLocation() {
        return location;
    }
    public boolean getClaimed() {
        return claimed;
    }

    /*
    //setters
    public void setListingID(String listingID) {
        this.listingID = listingID;
    }
    public void setID(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        return deal.getID() == id && deal.getListingID() == listingID;
    }

    @Override
    public int hashCode() {
        int ret;
        long temp;
        ret = name != null ? name.hashCode() : 0;
        ret = 31 * ret + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        ret = 31 * ret + (int) (temp ^ (temp >>> 32));
        ret = 31 * ret + (location != null ? location.hashCode() : 0);
        ret = 31 * ret + (claimed ? 1 : 0);
        return ret;
    }

    @Override
    public int compareTo(@NonNull Deal other) {
        return this.name.compareTo(other.name);
    }
}
