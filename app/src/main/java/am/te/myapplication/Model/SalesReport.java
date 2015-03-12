package am.te.myapplication.Model;

/**
 * Created by Collin on 3/7/15.
 */
public class SalesReport {

    private String name;
    private double price;
    private String location;
    private int SalesReportID;

    public SalesReport(String name, double price, String location){
        this.name = name;
        this.price = price;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
