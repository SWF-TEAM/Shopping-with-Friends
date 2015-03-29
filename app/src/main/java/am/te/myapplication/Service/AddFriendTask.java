package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import am.te.myapplication.Model.Agent;
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

    private final String mName;
    private final String mEmail;
    private final EditText mEmailView;
    private final Activity mActivity;

    /**
     * Constructs the initial AddFriendTask.
     *
     * @param name the name of the initial friend to add
     * @param email the email of the initial friend to add
     * @param act the activity that called this task
     */
    public AddFriendTask(String name, String email, EditText mEmailView,
                                                                 Activity act) {
        this.mName = name;
        this.mEmail = email;
        this.mActivity = act;
        this.mEmailView = mEmailView;
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
                String TAG = "login: ";
                String link = server_url + "/addfriend.php?userID="
                                        + Agent.getUniqueIDofCurrentlyLoggedIn()
                                        + "&friendID=" + userToAddKey;
                try {
                    String response = fetchHTTPResponseAsStr(TAG, link);

                    //whether or not user has become friends with the other
                    // in database
                    return response.equals("success");
                } catch(Exception e) {
                    return false;
                }
            }
            return false;
        }
    }

    private String getUserKey() {
        String TAG = Register.class.getSimpleName();

        try {
            String link = server_url + "/getuser.php?name=" + encode(mName)
                                     + "&email=" + encode(mEmail);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(
                      new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line;
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
        if (success) {
            mActivity.finish();
        } else {
            mEmailView.setError("try a different user");
        }
    }

}
