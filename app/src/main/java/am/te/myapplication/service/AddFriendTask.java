package am.te.myapplication.service;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import am.te.myapplication.model.Agent;
import am.te.myapplication.model.User;
import am.te.myapplication.presenter.Register;

/**
 * The task used to add a friend.
 *
 *
 * @author Mitchell Manguno, Mike Adkison, Veronica Leblanc
 * @version 1.0
 * @since 2015 March 22
 */
public class AddFriendTask extends UserTask {
    private final User friend;
    private final Activity activity;
    private final EditText emailView;

    /**
     * Constructs the initial AddFriendTask.
     *
     * @param friend the friend that is going to be adde
     * @param activity the activity that called this task
     */
    public AddFriendTask(User friend, Activity activity, EditText emailView) {
        this.friend = friend;
        this.activity = activity;
        this.emailView = emailView;
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
            String link = server_url + "/getuser.php?"+
                    "email=" + encode(friend.getEmail()); //passing the username is useless
            response = fetchHTTPResponseAsStr(TAG, link);
        }catch(Exception e){ //checks for encoding exception
            Log.e(TAG, "EXCEPTION>>>>", e);
        }

        return response;
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            activity.finish();
        } else {
            emailView.setError("Try a different username or email");
            emailView.requestFocus();
        }
    }

}
