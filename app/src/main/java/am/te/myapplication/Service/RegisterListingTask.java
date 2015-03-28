package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import am.te.myapplication.AddListing;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.State;

/**
 * The task used to register a new listing.
 *
 * @author Mitchell Manguno, Mike Adkison
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterListingTask extends UserTask {

    private String mName;
    private Double mPrice;
    private String mDescription;
    private Activity mActivity;

    /**
     * Constructs a RegisterListingTask instance.
     *
     * @param name the name of the listing to send
     * @param price the price of the listing to send
     * @param description the location of the listing to send
     * @param activity the activity that calls this task
     */
    public RegisterListingTask(String name, Double price, String description,
                                Activity activity) {
        mName = name;
        mPrice = price;
        mDescription = description;
        mActivity = activity;

    }
    @Override
    protected Boolean doInBackground(Void... params) {
        return !State.local && registerProduct();
    }

    /**
     * Sends a request to a php post handler to add a listing (product) to the database.
     *
     * @return boolean - true if the query does not throw an exception, else returns false.
     **/
    protected boolean registerProduct() {
        String TAG = AddListing.class.getSimpleName();
        String link = null;
        try {
            link = server_url + "/addlisting.php?title=" + encode(mName)
                          + "&description=" + encode(mDescription)
                          + "&price=" + mPrice
                          + "&userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();
        } catch(UnsupportedEncodingException e){
            Log.e(TAG, "url encoding failed");
        }

        try {
            String response = fetchHTTPResponseAsStr(TAG, link);
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                                            + ">>>>>>>>>>>>>>");
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
            mActivity.finish();
        }
    }
}
