package am.te.myapplication.service;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import am.te.myapplication.model.Agent;
import am.te.myapplication.presenter.Register;

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

                //whether or not user has become friends with the other in db
                return response.equals("success");
            } catch(Exception e) {
                return false;
            }
        }
        return false;
    }

    private String getUserKey() {
        String TAG = Register.class.getSimpleName();
        String response = "";

        try {
            String link = server_url + "/getuser.php?name=" + encode(mName)
                                     + "&email=" + encode(mEmail);
            response = fetchHTTPResponseAsStr(TAG, link);
        }catch(Exception e){ //checks for encoding exception
            Log.e(TAG, "EXCEPTION>>>>", e);
        }

        return response;
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
