package am.te.myapplication.Service;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Homepage;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Util.AlertListingAdapter;

/**
 * Created by Collin on 3/22/15.
 */
public class PopulateProductsTask extends UserTask {

    List<Listing> products;
    AlertListingAdapter arrayAdapter;

    public PopulateProductsTask(List<Listing> products, AlertListingAdapter arrayAdapter) {
        this.products = products;
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // get a list of possible friends from database
        ArrayList<Listing> theListings = new ArrayList<>();
        String TAG = Homepage.class.getSimpleName();
        String link = "http://artineer.com/sandbox" + "/getlistings.php?userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();
        try {//kek

            String result = fetchHTTPResponseAsStr(TAG, link);

            //now need to populate friends with users from result of database query
            if (result.equals("0 results")) {
                Log.d(TAG, result);
                return false;
            }
            String[] resultLines = result.split("<br>");
            System.out.println(result);
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject lineOfArray = jsonArray.getJSONObject(i);
                    String title = lineOfArray.getString("title");
                    String price = lineOfArray.getString("price");
                    String description = lineOfArray.getString("description");
                    String id = lineOfArray.getString("listingID");

                    Listing newListing = new Listing(title, Double.parseDouble(price), description, id);
                    theListings.add(newListing);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            products.clear();
            products.addAll(theListings);

            return true;
        } catch (Exception e) {
            Log.e(TAG, "EXCEPTION on homepage>>>", e);
            return false;
        }

    }
}
