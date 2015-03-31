package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import am.te.myapplication.Presenter.FriendListings;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;

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

    private final Activity activity;
    private final Deal deal;
    /**
     * Creates the RegisterDealTask instance.
     *
     * @param deal the deal which is to be added to the db
     */
    public RegisterDealTask(Deal deal, Activity activity) {
        this.activity = activity;
        this.deal = deal;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return registerListing();
    }

    /**
     * Sends a request to a php get handler to add a listing's deal to the database
     *
     * @return boolean - true if the query does not throw an exception, else returns false.
     */
    private boolean registerListing() {
        String TAG = RegisterDealTask.class.getSimpleName();
        String link = null;
        try {
            link = server_url + "/adddeal.php?title=" + encode(deal.getName())
                          + "&location=" + encode(deal.getLocation())
                          + "&price=" + deal.getPrice()
                          + "&description=" + encode(deal.getDescription())
                          + "&userID=" + encode(Agent.getUniqueIDofCurrentlyLoggedIn()) //violates law of demeter?
                          + "&listingID=" + encode(deal.getLocation());
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
            activity.finish();
        }
    }
}
