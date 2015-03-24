package am.te.myapplication.Service;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Homepage;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Service.UserTask;

/**
* Created by Collin on 3/23/15.
*/
public class PopulateDealsTask extends UserTask {

    List<Deal> deals;
    ArrayAdapter arrayAdapter;
    ArrayList<Deal> theDeals;

    public PopulateDealsTask(List<Deal> deals, ArrayAdapter arrayAdapter) {
        this.deals = deals;
        this.arrayAdapter = arrayAdapter;
    }

    /**
     * populates deals ArrayList with info from database
     *
     * @param params
     * @return true if population was successful
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        //DATABASE SHIT (get a list of possible friends from database)
        String TAG = Homepage.class.getSimpleName();
        String link = "http://artineer.com/sandbox" + "/getdeals2.php?userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();

        try {//kek
            Log.d(TAG, ">>>>>>>>>>>>>>>>>trying>>>>>");
            String result = fetchHTTPResponseAsStr(TAG,link);

            if (result.equals("0 results")) {
                Log.d(TAG, result);
                Log.d(TAG, "no results for getDeals");
                Log.d(TAG, Agent.getUniqueIDofCurrentlyLoggedIn());
                return false;
            }
            System.out.println(result);
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject lineOfArray = jsonArray.getJSONObject(i);
                    String title = lineOfArray.getString("Title");
                    String price = lineOfArray.getString("Price");
                    String location = lineOfArray.getString("Location");

                    Deal newDeal = new Deal(title, Double.parseDouble(price), location);
                    theDeals.add(newDeal);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            deals.clear();
            deals.addAll(theDeals);
            arrayAdapter.notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "EXCEPTION on homepage>>>", e);
            return false;
        }

    }

}
