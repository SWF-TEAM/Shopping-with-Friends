package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import am.te.myapplication.Homepage;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Util.AlertListingAdapter;

/**
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 25
 */
public class PopulateAssociatedDealsTask extends UserTask {


    List<Deal> deals;
    Listing listing;
    ArrayAdapter arrayAdapter;
    Activity activity;

    public PopulateAssociatedDealsTask(List<Deal> deals, Listing listing,
                                 ArrayAdapter arrayAdapter, Activity activity) {
        this.deals = deals;
        this.listing = listing;
        this.arrayAdapter = arrayAdapter;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // get a list of possible friends from database
        ArrayList<Deal> theDeals = new ArrayList<>();
        String TAG = Homepage.class.getSimpleName();
        String link = "http://artineer.com/sandbox" + "/getdeals.php?listingid="
                                                    + listing.id;
        try {//kek

            String result = fetchHTTPResponseAsStr(TAG, link);

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
                    String id = lineOfArray.getString("dealID");
                    String title = lineOfArray.getString("Title");
                    String description = lineOfArray.getString("Description");
                    String price = lineOfArray.getString("Price");
                    String location = lineOfArray.getString("Location");
                    String claimed = lineOfArray.getString("claimed");
                    Deal newDeal = new Deal(title, description,
                                            Double.valueOf(price), location,
                                 Boolean.valueOf(claimed), Integer.valueOf(id));
                    theDeals.add(newDeal);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            deals.clear();
            deals.addAll(theDeals);
            Collections.sort(deals);
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    arrayAdapter.notifyDataSetChanged();
                }
            });


            return true;
        } catch (Exception e) {
            Log.e(TAG, "EXCEPTION on homepage>>>", e);
            return false;
        }
    }
}