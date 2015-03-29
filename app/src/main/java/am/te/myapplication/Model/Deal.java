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
    private final String name;
    private final String description;
    private final double desiredPrice;
    private final String location;
    private final boolean claimed;
    private int id;

    public Deal(String name, String description, double desiredPrice,
                             String location, boolean claimed, int id) {
        this.name = name;
        this.description = description;
        this.desiredPrice = desiredPrice;
        this.location = location;
        this.claimed = claimed;
        this.id = id;
    }

    public Deal(String name, double price, String location) {
        this(name, "", price, location, false, 0);
        id = this.hashCode();
    }

    public String getName() {
        return name;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        return claimed == deal.claimed && Double.compare(deal.desiredPrice, desiredPrice) == 0 && id == deal.id && !(description != null ? !description.equals(deal.description) : deal.description != null) && !(location != null ? !location.equals(deal.location) : deal.location != null) && !(name != null ? !name.equals(deal.name) : deal.name != null);
    }

    @Override
    public int hashCode() {
        int ret;
        long temp;
        ret = name != null ? name.hashCode() : 0;
        ret = 31 * ret + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(desiredPrice);
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
