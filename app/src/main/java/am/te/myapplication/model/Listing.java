package am.te.myapplication.model;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * The listing class represents a listing that a user desires.
 *
 * @author Mitchell Manguno
 * @since 2015 February 23
 */

public class Listing implements Comparable<Listing>{

    private List<Deal> associatedDeals;
    private final String name;
    private final double desiredPrice;
    private final String description;
    public String id;

    public Listing(String name, double desiredPrice, String description, String id) {
        this.name = name;
        this.desiredPrice = desiredPrice;
        this.description = description;
        this.id = id;
    }

    public Listing(String name, double desiredPrice, String description) {
        this.name = name;
        this.description = description;
        this.desiredPrice = desiredPrice;
    }

    public String getID() { return id; }

    public String getName() {
        return name;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public String getDescription() {
        return description;
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
            && o.description.equals(this.description);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 31 * hash + name.hashCode();
        hash += 127 * hash + desiredPrice;
        int adHash = description == null ? 0 : description.hashCode();
        hash += 8191 * hash + adHash;
        return hash;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: " + desiredPrice
            + ", Additional Info: " + description;
    }

    public static Listing getListingFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        return new Listing(extras.getString("Name"), extras.getDouble("Price"), extras.getString("Additional"));
    }

    @Override
    public int compareTo(@NonNull Listing another) {
        return this.id.compareTo(another.getID());
    }

 }
