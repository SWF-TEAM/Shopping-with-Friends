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

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.User;
import am.te.myapplication.Register;
import am.te.myapplication.State;

/**
 * The task used to add a friend.
 *
 * //TODO: does this search for friends, or add them?
 *
 * @author Mitchell Manguno, Mike Adkison
 * @version 1.0
 * @since 2015 March 22
 */
public class AddFriendTask extends UserTask {

    private static boolean success;
    private static String mName;
    private static String mEmail;
    private static Activity mActivity;

//    private static volatile AddFriendTask INSTANCE;
//
//    public static AddFriendTask getInstance(String name, String email,
//                                                                 Activity act) {
//        success = false;
//        synchronized (RegisterListingTask.class) {
//            if (INSTANCE == null) {
//                INSTANCE = new AddFriendTask(name, email, act);
//            } else {
//                sanitizeAndReset(name, email, act);
//            }
//        }
//
//        return INSTANCE;
//    }

    /**
     * Constructs the initial AddFriendTask.
     *
     * @param name the name of the initial friend to add
     * @param email the email of the initial friend to add
     * @param act the activity that called this task
     */
    public AddFriendTask(String name, String email, Activity act) {
        mName = name;
        mEmail = email;
        mActivity = act;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (State.local) {
            //oh no y r u not using database
            return true;
        } else {
            //database stuff
            String userToAddKey = getUserKey();
            if (!userToAddKey.equals("*NOSUCHUSER")) {
                System.out.println("ADDING USER");
                String link = server_url + "/addfriend.php?userID="
                                        + Agent.getUniqueIDofCurrentlyLoggedIn()
                                        + "&friendID=" + userToAddKey;
                try {
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(
                      new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();

                    //whether or not user has become friends with the other
                    // in database
                    return sb.toString().equals("success");
                } catch(Exception e) {
                    return false;
                }
            }
            return false;
        }
    }
    protected String getUserKey() {
        String TAG = Register.class.getSimpleName();

        try {
            String link = server_url + "/getuser.php?name=" + encode(mName)
                                     + "&email=" + encode(mEmail);
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(
                      new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            Log.e(TAG, sb.toString());

            return sb.toString();
        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
            return "";
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        sanitize();
        this.success = success;
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
     * @param email the email of the friend to add
     * @param activity the activity that calls this task
     */
    private static void sanitizeAndReset(String name, String email,
                                                            Activity activity) {
        sanitize();
        mName = name;
        mEmail = email;
        mActivity = activity;
    }

    /**
     * Resets all the fields of the task to prevent mixing data.
     */
    private static void sanitize() {
        mName = null;
        mEmail = null;
        mActivity = null;
    }

    public boolean getSuccess() {
        return success;
    }
}
