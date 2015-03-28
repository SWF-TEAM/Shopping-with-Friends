package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import am.te.myapplication.FriendListings;
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
     * @param name the name of the deal to send
     * @param price the price of the deal to send
     * @param location the location of the deal to send
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
        return false;
    }

    /**
     * Sends a request to a php get handler to add a listing's deal to the database
     *
     * @return boolean - true if the query does not throw an exception, else returns false.
     */
    protected boolean registerProduct() {
        String TAG = RegisterDealTask.class.getSimpleName();
        String link = null;
        try {
            link = server_url + "/adddeal.php?Title=" + encode(mName)
                          + "&Location=" + encode(mLocation)
                          + "&Price=" + mPrice
                          + "&userID=" + encode(Agent.getUniqueIDofCurrentlyLoggedIn())
                          + "&listingID=" + encode(FriendListings.selectedFriendListing.id);
            System.out.println("using link: " + link);
        } catch(UnsupportedEncodingException e){
            Log.e(TAG, "url encoding failed");
        }
        try {
            String response = fetchHTTPResponseAsStr(TAG, link);
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                                          + ">>>>>>>>>>>>>>>>");
            Log.d(TAG, "RESPONSE TO REGISTER DEAL: " + response);
            return !response.contains("fail");
        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
            return false;
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            mActivity.finish();
        }
    }
}
