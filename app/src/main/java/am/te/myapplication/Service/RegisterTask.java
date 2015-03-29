package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import am.te.myapplication.Register;

/**
 * The task used to register a new user.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterTask extends UserTask {

    private final String mEmail;
    private final String mPassword;
    private final String mUsername;
    private final String mName;
    private final AutoCompleteTextView mEmailView;
    private final Activity mActivity;

    public RegisterTask(String username, String name, String email,
                String password, Activity act, AutoCompleteTextView emailView) {
        mUsername = username;
        mName = name;
        mEmail = email;
        mPassword = password;
        mActivity = act;
        mEmailView = emailView;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return registerUser();
    }

    /**
     * Sends a request to a php post handler to register a user
     *
     * @return true if the query does not throw an exception, else returns false
     **/
    private boolean registerUser() {
        String TAG = Register.class.getSimpleName();

        try {
            String link = server_url + "/adduser.php?username=" + mUsername
                                     +"&password=" + mPassword
                                     + "&email=" + mEmail
                                     +"&name=" + encode(mName);

            String response = fetchHTTPResponseAsStr(TAG, link);

            return !response.contains("failed")
                && !response.contains("already in use");

        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
            return false;
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        if (success){
            mActivity.finish();
        } else {
            //database says this username already exists
            mEmailView.setError("Try a different username or email");
            mEmailView.requestFocus();
        }
    }
}