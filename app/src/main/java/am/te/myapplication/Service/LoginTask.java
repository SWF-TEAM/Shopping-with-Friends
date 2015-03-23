package am.te.myapplication.Service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.User;
import am.te.myapplication.Register;
import am.te.myapplication.RegistrationModel;
import am.te.myapplication.State;

/**
 * Logs a user from the database into the app.
 *
 * @author Mitchell Manguno, Mike Adkison
 * @version 1.0
 * @since 2015 March 22
 */
public class LoginTask extends UserTask {

    private static View mProgressView;
    private static View mLoginFormView;
    private static String mUsername;
    private static String mPassword;
    private static User userToAuthenticate;
    private static Activity mActivity;
    private static EditText mPasswordView;


//    private static volatile LoginTask INSTANCE;
//
//    /**
//     * Returns the RegisterListingTask instance, and resets the fields to
//     * accommodate new data being sent.
//     *
//     * @param name the name of the user to login
//     * @param password the price of the listing to login
//     * @param act the activity that calls this task
//     * @return the RegisterListingTask instance
//     */
//    public static LoginTask getInstance(String name, String password,
//                                        View mLoginFormView, View mProgressView,
//                                                                 Activity act) {
//
//        success = false;
//        synchronized (LoginTask.class) {
//            if (INSTANCE == null) {
//                INSTANCE = new LoginTask(name, password, act, mLoginFormView,
//                                                                 mProgressView);
//            } else {
//                sanitizeAndReset(name, password, act, mLoginFormView,
//                                                                 mProgressView);
//            }
//        }
//
//        return INSTANCE;
//    }

    public LoginTask(String username, String password, Activity act,
                View loginFormView, View progressView, EditText mPasswordView) {
        this.mUsername = username;
        this.mPassword = password;
        this.userToAuthenticate = new User(mUsername, mPassword);
        this.mActivity = act;
        this.mLoginFormView = loginFormView;
        this.mProgressView = progressView;
        this.mPasswordView = mPasswordView;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (State.local) {
            return RegistrationModel.getUsers().contains(userToAuthenticate);
        } else {
            String loginKey = getLoginKey();
            if (!(loginKey.equals("*NOSUCHUSER") || loginKey.equals("")
                                                 || loginKey == null)) {
                Agent.setUniqueIDofCurrentlyLoggedIn(loginKey);
                return true;
            }
            return false;
        }
    }
    protected String getLoginKey() {

        byte[] digest = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(mPassword.getBytes("UTF-8")); //Change to "UTF-16" if needed
            digest = md.digest();

        } catch (NoSuchAlgorithmException e) {
            Log.e(Register.class.getSimpleName(), "EXCEPTION>>>>", e);
        } catch (UnsupportedEncodingException e) {
            Log.e(Register.class.getSimpleName(), "EXCEPTION>>>>", e);
        }

        String TAG = Register.class.getSimpleName();
        String link = server_url + "/getuserlogin.php?username=" + mUsername
                                 + "&password=" + digest;

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
            Log.e(TAG, sb.toString());
            return sb.toString();
        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
            return "";
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
       showProgress(false);
        if (success) {
            mActivity.finish();
        } else {
            mPasswordView.setError("Invalid password or username.");
            mPasswordView.requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        showProgress(false);
    }

    /**
     * Resets all the fields of the task to prevent mixing data, and sets it
     * to new data.
     *
     * @param name the name of the user to login
     * @param password the password of the user to login
     * @param activity the activity that calls this task
     */
    private static void sanitizeAndReset(String name, String password,
                  Activity activity, View loginFormView, View progressView) {
        sanitize();
        mUsername = name;
        mPassword = password;
        userToAuthenticate = new User(mUsername, mPassword);
        mActivity = activity;
        mLoginFormView = loginFormView;
        mProgressView = progressView;
    }

    /**
     * Resets all the fields of the task to prevent mixing data.
     */
    private static void sanitize() {
        mUsername = null;
        mPassword = null;
        userToAuthenticate = null;
        mActivity = null;
        mProgressView = null;
        mLoginFormView = null;
        mPasswordView = null;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = mActivity.getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
