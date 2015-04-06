package am.te.myapplication.service;

import android.app.Activity;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import am.te.myapplication.R;
import am.te.myapplication.model.User;
import am.te.myapplication.presenter.Register;

/**
 * The task used to register a new user.
 *
 * @author Mitchell Manguno, Veronica LeBlanc
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterTask extends UserTask {
    private final User user;
    private final Activity activity;

    public RegisterTask(User user, Activity activity, AutoCompleteTextView emailView) {
        this.activity = activity;
        this.user = user;
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
            String link = server_url + "/adduser.php?username=" + encode(user.getUsername())
                                     +"&password=" + encode(user.getPassword())
                                     + "&email=" + encode(user.getEmail())
                                     +"&name=" + encode(user.getName());

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
            activity.finish();
        } else {
            //database says this username already exists
            AutoCompleteTextView emailView = (AutoCompleteTextView) activity.findViewById(R.id.reg_email);
            emailView.setError("Try a different username or email");
            emailView.requestFocus();
        }
    }
}