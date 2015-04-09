package am.te.myapplication.service;

import android.app.Activity;
import android.util.Log;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import am.te.myapplication.presenter.Homepage;
import am.te.myapplication.model.Deal;
import am.te.myapplication.model.Listing;
import am.te.myapplication.util.AlertDealAdapter;

/**
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 25
 */
public class PopulateAssociatedDealsTask extends UserTask {

    private final List<Deal> deals;
    private final Listing listing;
    private final BaseAdapter arrayAdapter;
    private final Activity activity;

    public PopulateAssociatedDealsTask(List<Deal> deals, Listing listing,
                                       BaseAdapter arrayAdapter, Activity activity) {
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
        String link = "http://artineer.com/sandbox" + "/getdeals.php?listingID="
                                                    + listing.id;
        System.out.println(listing.id);
        try {//kek

            String result = fetchHTTPResponseAsStr(TAG, link);

            if (result.equals("0 results")) {
                Log.d(TAG, result);
                return false;
            }

            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject lineOfArray = jsonArray.getJSONObject(i);
                    String listingID = lineOfArray.getString("listingID");
                    String title = lineOfArray.getString("title");
                    String description = lineOfArray.getString("description");
                    String price = lineOfArray.getString("price");
                    System.out.println(title);
                    System.out.println(price);
                    String location = lineOfArray.getString("location");
                    String claimed = lineOfArray.getString("claimed");
                    Deal newDeal = new Deal(listingID, "", title, description,
                                            Double.valueOf(price), location,
                                 Boolean.valueOf(claimed));
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