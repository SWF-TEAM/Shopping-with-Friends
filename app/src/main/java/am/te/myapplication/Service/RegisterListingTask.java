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

    private static String mName;
    private static Double mPrice;
    private static String mDescription;
    private static Activity mActivity;

//    private static volatile RegisterListingTask INSTANCE;
//
//    /**
//     * Returns the RegisterListingTask instance, and resets the fields to
//     * accommodate new data being sent.
//     *
//     * @param name the name of the listing to send
//     * @param price the price of the listing to send
//     * @param descript the description of the listing to send
//     * @param act the activity that calls this task
//     * @return the RegisterListingTask instance
//     */
//    public static RegisterListingTask getInstance(String name, Double price,
//                                                String descript, Activity act) {
//        synchronized (RegisterListingTask.class) {
//            if (INSTANCE == null) {
//                INSTANCE = new RegisterListingTask(name, price, descript, act);
//            } else {
//                sanitizeAndReset(name, price, descript, act);
//            }
//        }
//
//        return INSTANCE;
//    }

    /**
     * Constructs the initial RegisterListingTask instance.
     *
     * @param name the name of the initial listing to send
     * @param price the price of the initial listing to send
     * @param description the location of the initial listing to send
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
        if (!State.local) {//Doesn't make sense to use database in local sense
            return registerProduct();
        }
        sanitize();
        return false;
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
     * @param name the name of the listing to send
     * @param price the price of the listing to send
     * @param descript the location of the listing to send
     * @param activity the activity that calls this task
     */
    private static void sanitizeAndReset(String name, Double price,
                                           String descript, Activity activity) {
        sanitize();
        mName = name;
        mPrice = price;
        mDescription = descript;
        mActivity = activity;
    }

    /**
     * Resets all the fields of the task to prevent mixing data.
     */
    private static void sanitize() {
        mName = null;
        mPrice = null;
        mDescription = null;
        mActivity = null;
    }
}
