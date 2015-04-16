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
import am.te.myapplication.model.Listing;
import am.te.myapplication.util.AlertListingAdapter;

/**
 * Populates an array with a user's listings.
 *
 * @author Collin Caldwell
 * @version 1.0
 * @since 2015 March 22
 */
public class PopulateListingsTask extends UserTask {

    private final List<Listing> listings;
    private final BaseAdapter arrayAdapter;
    private final Activity activity;
    private final String id;

    public PopulateListingsTask(List<Listing> listings,
               BaseAdapter arrayAdapter, Activity activity, String id) {
        this.listings = listings;
        this.arrayAdapter = arrayAdapter;
        this.activity = activity;
        this.id = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        System.out.println("Getting listings for " + id);
        // get a list of possible friends from database
        ArrayList<Listing> theListings = new ArrayList<>();
        String TAG = Homepage.class.getSimpleName();
        String link = server_url + "/getlistings.php?userID=" + id;
        try {//kek

            String result = fetchHTTPResponseAsStr(TAG, link);

            //populate friends with users from result of database query
            if (result.equals("0 results")) {
                Log.d(TAG, result);
                return false;
            }
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                Listing newListing = null;
                try {
                    JSONObject lineOfArray = jsonArray.getJSONObject(i);
                    String title = lineOfArray.getString("title");
                    String price = lineOfArray.getString("price");
                    String description = lineOfArray.getString("description");
                    String id = lineOfArray.getString("listingID");
                    newListing = new Listing(title, Double.parseDouble(price),
                                             description, id);
                    theListings.add(newListing);

                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                /*if (newListing != null) {
                    try {
                        //JSONObject lineOfArray = jsonArray.getJSONObject(i);
                        //String seen = lineOfArray.getString("hasSeenDeals");

                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                */
            }

            listings.clear();
            listings.addAll(theListings);
            System.out.println("the listings size: " + theListings.size());
            Collections.sort(listings);

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
