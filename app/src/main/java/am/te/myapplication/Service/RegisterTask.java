package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;

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

import am.te.myapplication.Model.User;
import am.te.myapplication.Register;
import am.te.myapplication.RegistrationModel;
import am.te.myapplication.State;

/**
 * The task used to register a new user.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 22
 */
public class RegisterTask extends UserTask {

    private static boolean success;
    private static String mEmail;
    private static byte[] mPassword;
    private static String mUsername;
    private static String mName;
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
                                                byte[] password, Activity act) {
        mUsername = username;
        mName = name;
        mEmail = email;
        mPassword = password;
        mActivity = act;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!State.local) {
            return registerUser();
        }

        sanitize();
        return false;
    }

    protected boolean registerUser() {
        String TAG = Register.class.getSimpleName();

        try {
            String link = server_url + "/adduser.php?username=" + mUsername
                                     +"&password=" + mPassword
                                     + "&email=" + mEmail
                                     +"&name=" + encode(mName);
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                            response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line="";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            success = true;
            in.close();
            Log.d(TAG, sb.toString());
            return !sb.toString().contains("failed")
                && !sb.toString().contains("already in use");
        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
            return false;
        }
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        sanitize();
        this.success = success;
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
    }

    /**
     * Returns the value of 'success'. This is necessary, as the calling
     * activity needs to know whether or not the task has succeeded in order
     * to know what to do next.
     *
     * @return true if the task was successful, false if otherwise.
     */
    public static boolean getSuccess() {
        return success;
    }
}