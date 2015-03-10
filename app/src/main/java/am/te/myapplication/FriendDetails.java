package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;


/**
 * This displays the details of your friend that you clicked on
 *
 * @author Collin Caldwell
 * @version 1.0
 * @since 2015 - February - 26
 */
public class FriendDetails extends Activity {
    User currentUser;
    private RemoveFriendTask mRemoveFriendTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_details);


        Bundle extras = getIntent().getExtras();
        if (getResources().getString(R.string.state).equals("local") && extras != null) {
            currentUser = User.loggedIn.getFriend(extras.getString("username"));
        } else { //if using database PLEASE USE THE DATABASE PLS
            currentUser = FriendList.selectedFriend;
        }
        // Put the username onto the details screen
        TextView usernameText = (TextView) findViewById(R.id.Username);
        usernameText.setText(currentUser.getUsername());
        // Put the email onto the details screen
        TextView emailText = (TextView) findViewById(R.id.Email);
        emailText.setText(currentUser.getEmail());
        // Put the rating onto the details screen
        TextView ratingText = (TextView) findViewById(R.id.Rating);
        ratingText.setText("Rating: "+String.valueOf(currentUser.getRating()));
        // Put the email onto the details screen
        TextView salesText = (TextView) findViewById(R.id.salesReports);
        salesText.setText("Sales Reports: "+String.valueOf(currentUser.getSalesReports()));


    }

    /**
     * Removes your friends while in details view
     *
     * @param view
     */
    public void removeFriend(View view) {

        if (getResources().getString(R.string.state).equals("local")) {
            User.loggedIn.removeFriend(currentUser);
        } else {
            mRemoveFriendTask = new RemoveFriendTask(currentUser.getId());
            mRemoveFriendTask.execute();
        }
        finish();
    }
















    public class RemoveFriendTask extends AsyncTask<Void, Void, Boolean> {
        String idOfFriend;
        public RemoveFriendTask(String id) {
            this.idOfFriend = id;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if (getResources().getString(R.string.state).equals("local")) {
                //local
                //dont be local
                return true;
            } else {
                // authentication against a network service.
                // check if user is in system
                // register user if not in system
                return removeFriend();
                //already in system
            }
            /*
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            */

            // Authentication with local list of registered users (will be replaced with database auth soon^(TM))
            /*User userToAuthenticate = new User(mEmail, mPassword);
            return RegistrationModel.getUsers().contains(userToAuthenticate);*/
        }

        protected boolean removeFriend() {
            String TAG = FriendDetails.class.getSimpleName();

            String link = "http://artineer.com/sandbox" + "/deletefriend.php?userID=" + Login.uniqueIDofCurrentlyLoggedIn + "&friendID=" + idOfFriend;
            try {
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                Log.d(TAG, sb.toString());
                return !sb.toString().contains("Friend pair failed to be deleted.");
            }catch(Exception e){
                Log.e(TAG, "EXCEPTION>>>>", e);
                return false;
            }
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mRemoveFriendTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mRemoveFriendTask = null;
        }
    }
}
