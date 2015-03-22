package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import am.te.myapplication.FriendDetails;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.State;

/**
 * Removes a friend from a user.
 *
 * @author Mitchell Manguno, Mike Adkison
 * @version 1.0
 * @since 2015 March 22
 */
public class RemoveFriendTask extends UserTask {
    private static String idOfFriend;
    private static Activity mActivity;

    private static volatile RemoveFriendTask INSTANCE;

    /**
     * Returns the RegisterDealTask instance, and resets the fields to
     * accommodate new data being sent.
     *
     * @param id the id of the friend to remove
     * @param act the activity that calls this task
     * @return the RegisterDealTask instance
     */
    public static RemoveFriendTask getInstance(String id, Activity act) {
        synchronized (RemoveFriendTask.class) {
            if (INSTANCE == null) {
                INSTANCE = new RemoveFriendTask(id, act);
            } else {
                sanitizeAndReset(id, act);
            }
        }

        return INSTANCE;
    }

    /**
     * Creates the RemoveFriendTask instance.
     *
     * @param id the id of the initial friend to remove
     * @param activity the initial activity that calls this task
     */
    private RemoveFriendTask(String id, Activity activity) {
        this.idOfFriend = id;
        this.mActivity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!State.local) {
            return removeFriend();
        }

        sanitize();
        return false;
    }

    protected boolean removeFriend() {
        String TAG = FriendDetails.class.getSimpleName();

        String link = server_url + "/deletefriend.php?userID="
                                 + Agent.getUniqueIDofCurrentlyLoggedIn()
                                 + "&friendID=" + idOfFriend;
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
            Log.d(TAG, sb.toString());
            return !sb.toString().contains("Friend pair failed to be deleted.");
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
     * @param idOfFriendParam the id of the friend to remove
     */
    private static void sanitizeAndReset(String idOfFriendParam, Activity act) {
        sanitize();
        idOfFriend = idOfFriendParam;
        mActivity = act;
    }

    /**
     * Resets all the fields of the task to prevent mixing data.
     */
    private static void sanitize() {
        idOfFriend = null;
        mActivity = null;
    }
}
