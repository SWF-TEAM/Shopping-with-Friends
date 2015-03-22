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
import am.te.myapplication.Encoder;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.State;

/**
 * @author Mitchell Manguno, Mike Adkison
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterListingTask extends UserTask {

    private static String mName;
    private static Double mPrice;
    private static String mDescription;
    private static Activity mActivity;

    private static volatile RegisterListingTask INSTANCE;

    /**
     * Returns the RegisterListingTask instance, and resets the fields to
     * accommodate new data being sent.
     *
     * @param name the name of the listing to send
     * @param price the price of the listing to send
     * @param descript the location of the listing to send
     * @param act the activity that calls this task
     * @return the RegisterListingTask instance
     */
    public static RegisterListingTask getInstance(String name, Double price,
                                               String descript, Activity act) {
        synchronized (RegisterListingTask.class) {
            if (INSTANCE == null) {
                INSTANCE = new RegisterListingTask(name, price, descript, act);
            } else {
                sanitizeAndReset(name, price, descript, act);
            }
        }

        return INSTANCE;
    }

    private RegisterListingTask(String name, Double price, String description,
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

    protected boolean registerProduct() {
        String TAG = AddListing.class.getSimpleName();
        String link = null;
        try {
            link = server_url + "/addlisting.php?title=" + Encoder.encode(mName)
                          + "&description=" + Encoder.encode(mDescription)
                          + "&price=" + mPrice
                          + "&userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();
        } catch(UnsupportedEncodingException e){
            Log.e(TAG, "url encoding failed");
        }
        try {
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                            response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                                            + ">>>>>>>>>>>>>>");
            Log.d(TAG, sb.toString());
            return sb.toString().equals("success");
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
