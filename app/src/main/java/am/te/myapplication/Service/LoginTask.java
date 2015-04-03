package am.te.myapplication.Service;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;


import am.te.myapplication.Model.Agent;
import am.te.myapplication.Presenter.Register;

/**
 * Logs a user from the database into the app.
 *
 * @author Mitchell Manguno, Mike Adkison
 * @version 1.0
 * @since 2015 March 22
 */
public class LoginTask extends UserTask {

    private final View mProgressView;
    private final View mLoginFormView;
    private final String mUsername;
    private final String mPassword;
    private final Activity mActivity;
    private final EditText mPasswordView;

    public LoginTask(String username, String password, Activity act,
                View loginFormView, View progressView, EditText mPasswordView) {
        this.mUsername = username;
        this.mPassword = password;
        this.mActivity = act;
        this.mLoginFormView = loginFormView;
        this.mProgressView = progressView;
        this.mPasswordView = mPasswordView;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //showProgress(true);
        String loginKey = getLoginKey();
        if (!(loginKey.equals("*NOSUCHUSER") || loginKey.equals(""))) {
            Agent.setUniqueIDofCurrentlyLoggedIn(loginKey);
            return true;
        }
        return false;
    }

    String getLoginKey() {

        String TAG = Register.class.getSimpleName();
        String link = server_url + "/getuserlogin.php?username=" + mUsername
                                 + "&password=" + mPassword;

        return fetchHTTPResponseAsStr(TAG, link);
    }
    @Override
    protected void onPostExecute(final Boolean success) {
       //showProgress(false);
        if (!success) {
            mPasswordView.setError("Invalid password or username.");
            mPasswordView.requestFocus();
        }
    }

    /*
    @Override
    protected void onCancelled() {
        showProgress(false);
    }
    */

/*
    /**
     * Shows the progress UI and hides the login form.
     ** /
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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
*/
}
