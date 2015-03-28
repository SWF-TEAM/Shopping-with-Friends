package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import am.te.myapplication.Register;
import am.te.myapplication.State;

/**
 * The task used to register a new user.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterTask extends UserTask {

    private String mEmail;
    private String mPassword;
    private String mUsername;
    private String mName;
    private AutoCompleteTextView mEmailView;
    private Activity mActivity;

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
        return !State.local && registerUser();
    }

    /**
     * Sends a request to a php post handler to register a user
     *
     * @return boolean - true if the query does not throw an exception, else returns false.
     **/
    protected boolean registerUser() {
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