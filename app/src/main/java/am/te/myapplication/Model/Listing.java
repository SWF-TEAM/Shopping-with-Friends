package am.te.myapplication.Model;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

/**
 * The product class represents a product that a user desires.
 *
 * @author Mitchell Manguno
 * @since 2015 February 23
 */

public class Listing implements Comparable<Listing>{

    private List<Deal> associatedDeals;
    private String name;
    private double desiredPrice;
    private String additionalInfo;
    private boolean hasBeenSeen;
    private int productID;
    public String id;

    public Listing(String name, double desiredPrice, String additionalInfo, String id) {
        this.name = name;
        this.desiredPrice = desiredPrice;
        this.additionalInfo = additionalInfo;
        this.id = id;
    }

    public Listing(String name, double desiredPrice, String additionalInfo) {
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.desiredPrice = desiredPrice;
    }

    public Listing(String name, double desiredPrice) {
        this(name, desiredPrice, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<Deal> getAssociatedDeals() {
        return associatedDeals;
    }

    public void setAssociatedDeals(List<Deal> associatedDeals) {
        this.associatedDeals = associatedDeals;
    }

    public boolean hasBeenSeen() {
        return hasBeenSeen;
    }

    public void setHasBeenSeen(boolean hasBeenSeen) {
        this.hasBeenSeen = hasBeenSeen;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Listing)) {
            return false;
        }

        Listing o = (Listing) other;

        return o.name.equals(this.name) && o.desiredPrice == this.desiredPrice
            && o.additionalInfo.equals(this.additionalInfo);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 31 * hash + name.hashCode();
        hash += 127 * hash + desiredPrice;
        int adHash = additionalInfo == null ? 0 : additionalInfo.hashCode();
        hash += 8191 * hash + adHash;
        return hash;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: " + desiredPrice
            + ", Additional Info: " + additionalInfo;
    }

    public static Listing getListingFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        return new Listing(extras.getString("Name"), extras.getDouble("Price"), extras.getString("Additional"));
    }

    @Override
    public int compareTo(Listing another) {
        return this.name.compareTo(another.name);
    }

 }
