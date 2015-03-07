package am.te.myapplication;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class SearchFriends extends Activity {

    private static Set<User> registeredUsers;
    private EditText mNameView;
    private EditText mEmailView;
    private UserAddTask mUserAddTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        mNameView = (EditText) findViewById(R.id.name);
        mEmailView = (EditText) findViewById(R.id.email);

        super.onStart();
    }


    /**
     * The OnClick listener for the "OK" button. Calls the real search method
     * with whatever is inside the textfield at the time.
     *
     * @param view Not really sure what this is for
     */
    public void search(View view) {
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        mUserAddTask = new UserAddTask(name, email);
        mUserAddTask.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_friends, menu);
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

















    public class UserAddTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mEmail;
        private User userToAuthenticate;
        UserAddTask(String name, String email) {
            mName = name;
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (getResources().getString(R.string.state).equals("local")) {
                //oh no y r u not using database
                return true;
            } else {
                //attempt authentication against a network service.
                /*
                try {
                    // Simulate network access.
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return false;
                }

                for (String credential : DUMMY_CREDENTIALS) {
                    String[] pieces = credential.split(":");
                    if (pieces[0].equals(mUsername)) {
                        // Account exists, return true if the password matches.
                        return pieces[1].equals(mPassword);
                    }
                }

                */
                //database stuff
                String userToAddKey = getUserKey();
                if (!userToAddKey.equals("*NOSUCHUSER")) {
                    System.out.println("ADDING USER");
                    String link = getResources().getString(R.string.server_url) + "/addfriend.php?userID=" + Login.uniqueIDofCurrentlyLoggedIn + "&friendID=" + userToAddKey;
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

                        return sb.toString().equals("success"); //whether or not user has become friends with the other in database
                    }catch(Exception e){
                        return false;
                    }
                }
                return false;
            }
        }
        protected String getUserKey() {
            String TAG = Register.class.getSimpleName();

            try {
                String link = "http://artineer.com/sandbox/getuser.php?name=" + Encoder.encode(mName) + "&email=" + Encoder.encode(mEmail);
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
                Log.e(TAG, sb.toString());

                return sb.toString();
            }catch(Exception e){
                Log.e(TAG, "EXCEPTION>>>>", e);
                return "";
            }
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mUserAddTask = null;

            if (success) {
                finish();
            } else {
                mEmailView.setError("try a different user");
            }
        }

        @Override
        protected void onCancelled() {
            mUserAddTask = null;
        }
    }






}

