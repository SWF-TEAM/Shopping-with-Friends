package am.te.myapplication.service;

import android.app.Activity;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import am.te.myapplication.model.Listing;
import am.te.myapplication.presenter.AddListing;
import am.te.myapplication.model.Agent;

/**
 * The task used to register a new listing.
 *
 * @author Mitchell Manguno, Mike Adkison, Veronica LeBlanc
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterListingTask extends UserTask {

    private final Listing listing;
    private final Activity activity;

    /**
     * Constructs a RegisterListingTask instance.
     *
     * @param listing the listing that will be registered
     * @param activity the activity that calls this task
     */
    public RegisterListingTask(Listing listing,
                                Activity activity) {
        this.listing = listing;
        this.activity = activity;

    }
    @Override
    protected Boolean doInBackground(Void... params) {
        return registerListing();
    }

    /**
     * Sends a request to a php post handler to add a listing (listing) to the
     * database.
     *
     * @return true if the query does not throw an exception, else returns false
     **/
    private boolean registerListing() {
        String TAG = AddListing.class.getSimpleName();
        String link = null;
        try {
            link = server_url + "/addlisting.php?title=" + encode(listing.getName())
                          + "&description=" + encode(listing.getDescription())
                          + "&price=" + listing.getDesiredPrice()
                          + "&userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();
        } catch(UnsupportedEncodingException e){
            Log.e(TAG, "url encoding failed");
        }

        try {
            String response = fetchHTTPResponseAsStr(TAG, link);
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>" +
                    ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Log.d(TAG, response);
            return response.equals("success");
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
