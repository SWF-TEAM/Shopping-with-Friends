package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;

import am.te.myapplication.AddListing;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.State;

/**
 * Registers a deal in the database. This is a singleton because we want only
 * one (deal-registering) link to the database at any one time. More than one
 * connection is unnecessary, as there will never be any need to register two
 * deals at the same time, nor should it be possible.
 *
 * @author Mitchell Manguno, Mike Adkison
 * @version 2.0
 * @since 2015 March 22
 */
public class RegisterDealTask extends UserTask {

    private static String mName;
    private static Double mPrice;
    private static String mLocation;
    private static Activity mActivity;

    /**
     * Creates the RegisterDealTask instance.
     *
     * @param name the name of the initial deal to send
     * @param price the price of the initial deal to send
     * @param location the location of the initial deal to send
     * @param activity the initial activity that calls this task
     */
    public RegisterDealTask(String name, Double price, String location,
                            Activity activity) {
        mName = name;
        mPrice = price;
        mLocation = location;
        mActivity = activity;

    }
    @Override
    protected Boolean doInBackground(Void... params) {
        if (!State.local) {//Doesn't make sense to use database in local sense
            return registerProduct();
        }
        sanitize();
        return false;
    }

    /**
     * Sends a request to a php get handler to add a listing's deal to the database
     *
     * @return boolean - true if the query does not throw an exception, else returns false.
     **/
    protected boolean registerProduct() {
        String TAG = AddListing.class.getSimpleName();
        String link = null;
        try {
            link = server_url + "/adddeal2.php?Title=" + encode(mName)
                          + "&Location=" + encode(mLocation)
                          + "&Price=" + mPrice
                          + "&userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();
        } catch(UnsupportedEncodingException e){
            Log.e(TAG, "url encoding failed");
        }
        try {
            String response = fetchHTTPResponseAsStr(TAG, link);
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                                          + ">>>>>>>>>>>>>>>>");
            Log.d(TAG, response);
            return response.equals("Deal Values have been inserted"
                                                          + " successfully\\n");
        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
            return false;
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        sanitize();
        if (success) {
            mActivity.finish();
        }
    }

    @Override
    protected void onCancelled() {
        sanitize();
    }

    /**
     * Resets all the fields of the task to prevent mixing data, and sets it
     * to new data.
     *
     * @param name the name of the deal to send
     * @param price the price of the deal to send
     * @param location the location of the deal to send
     * @param activity the activity that calls this task
     */
    private static void sanitizeAndReset(String name, Double price,
                                           String location, Activity activity) {
        sanitize();
        mName = name;
        mPrice = price;
        mLocation = location;
        mActivity = activity;
    }

    /**
     * Resets all the fields of the task to prevent mixing data.
     */
    private static void sanitize() {
        mName = null;
        mPrice = null;
        mLocation = null;
        mActivity = null;
    }

}
