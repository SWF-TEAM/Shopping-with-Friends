package am.te.myapplication.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Collin on 3/28/15.
 */
public class Item {

    public static String formatPrice(Double price) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return "$"+formatter.format(price);
    }



}
