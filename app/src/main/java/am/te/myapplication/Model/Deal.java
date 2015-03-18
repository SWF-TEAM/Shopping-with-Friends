package am.te.myapplication.Model;

/**
 * Created by Collin on 3/12/15.
 */
public class Deal {
    private String name;
    private double desiredPrice;
    private String location;
    private int productID;

    public Deal(String name, double desiredPrice, String location) {
        this.name = name;
        this.desiredPrice = desiredPrice;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
