package am.te.myapplication.Model;

/**
 * The product class represents a product that a user desires.
 *
 * @author Mitchell Manguno
 * @since 2015 February 23
 */

public class Listing {

    private String name;
    private double desiredPrice;
    private String additionalInfo;
    private int productID;
    public String id;

    public Listing(String name, double desiredPrice, String additionalInfo, String id) {
        this.name = name;
        this.desiredPrice = desiredPrice;
        this.additionalInfo = additionalInfo;
        this.id = id;
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
        hash += 8191 * hash + additionalInfo.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: " + desiredPrice
            + ", Additional Info: " + additionalInfo;
    }

 }
