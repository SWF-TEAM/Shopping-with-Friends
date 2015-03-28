package am.te.myapplication.Service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.EditText;


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

        String TAG = Register.class.getSimpleName();
        String link = server_url + "/getuserlogin.php?username=" + mUsername
                                 + "&password=" + mPassword;

        return fetchHTTPResponseAsStr(TAG, link);
    }
    @Override
    protected void onPostExecute(final Boolean success) {
       showProgress(false);
        if (success) {
//            mActivity.finish();
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
