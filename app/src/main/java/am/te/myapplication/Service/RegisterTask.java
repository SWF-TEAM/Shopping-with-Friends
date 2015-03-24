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

    private static String mEmail;
    private static byte[] mPassword;
    private static String mUsername;
    private static String mName;
    private static AutoCompleteTextView mEmailView;
    private static Activity mActivity;

    //private static volatile RegisterTask INSTANCE;
    //public static RegisterTask getInstance(String username, String name,
    //                                              String email, byte[] password,
    //                                              Activity act) {
    //
    //    success = false;
    //    synchronized (RegisterListingTask.class) {
    //        if (INSTANCE == null) {
    //            INSTANCE = new RegisterTask(username, name, email, password,
    //                                                                       act);
    //        } else {
    //            sanitizeAndReset(username, name, email, password, act);
    //        }
    //    }
    //
    //    return INSTANCE;
    //}

    public RegisterTask(String username, String name, String email,
               byte[] password, Activity act, AutoCompleteTextView emailView) {
        mUsername = username;
        mName = name;
        mEmail = email;
        mPassword = password;
        mActivity = act;
        mEmailView = emailView;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!State.local) {
            return registerUser();
        }

        sanitize();
        return false;
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

            boolean noError = !response.contains("failed")
                           && !response.contains("already in use");
            return noError;

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

    @Override
    protected void onCancelled() {
        sanitize();
    }

    /**
     * Resets all the fields of the task to prevent mixing data, and sets it
     * to new data.
     *
     * @param username the username of the user to register
     * @param name the name of the user to register
     * @param email the email of the user to register
     * @param password the password of the user to register
     * @param act the activity that calls this task
     */
    private static void sanitizeAndReset(String username, String name,
                                         String email, byte[] password,
                                         Activity act) {
        sanitize();
        mUsername = username;
        mName = name;
        mEmail = email;
        mPassword = password;
        mActivity = act;
    }

    /**
     * Resets all the fields of the task to prevent mixing data.
     */
    private static void sanitize() {
        mUsername = null;
        mName= null;
        mEmail = null;
        mPassword = null;
        mActivity = null;
        mEmailView = null;
    }
}