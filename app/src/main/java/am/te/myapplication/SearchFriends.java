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
    private EditText queryView;
    private List<User> matches;
    private ListView lv;
    private ArrayAdapter<User> arrayAdapter;
    private final String server_url = "http://artineer.com/sandbox";
    private UserGetPossibleFriendsTask mGetPossibleFriendsTask = null;
    private UserAddFriendTask mAddFriendTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        lv = (ListView) findViewById(R.id.search_friend_listview);
        queryView = (EditText) findViewById(R.id.search_editText);
        matches = new ArrayList<User>();
        populateUsers(); //fills up registeredUsers
        arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, matches);
        lv.setAdapter(arrayAdapter);

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //add friend to friend list here
                Log.d(SearchFriends.class.getSimpleName(), "position" + position + " id" + id);

                System.out.println("adding friend " + matches.get(position));
                User toAdd = matches.get(position);
                System.out.println(User.loggedIn.hasFriends());
                matches.remove(position);
                arrayAdapter.notifyDataSetChanged();

                if(State.local) { //add friend locally
                    User.loggedIn.addFriend(toAdd);
                } else { //add friend in database asynchronously

                    mAddFriendTask = new UserAddFriendTask(toAdd.getUsername());
                    mAddFriendTask.execute();
                }
            }
        });

        super.onStart();
    }


    /**
     * The OnClick listener for the "OK" button. Calls the real search method
     * with whatever is inside the textfield at the time.
     *
     * @param view Not really sure what this is for
     */
    public void search(View view) {
        matches = new ArrayList<User>();
        String query = queryView.getText().toString();
        search(query);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_friends, menu);
        return true;
    }

    /**
     * Searches through the registered users. Currently, it is a set of Users.
     *
     * @param query the String query that we will search with
     */
    private void search(String query) {
        matches = new ArrayList<User>();
        Iterator<User> iter = registeredUsers.iterator();
        User curr = null;
        boolean email = query.contains("@");
        while (iter.hasNext()) {
            curr = iter.next();
            System.out.println("User is:" + curr.getUsername());
            if (email) {
                if (curr != null && !User.loggedIn.isFriendsWith(curr) && query.equals(curr.getEmail())) {
                    //System.out.println("Found a match");
                    matches.add(curr);
                }
            } else {
                if (curr != null && !User.loggedIn.isFriendsWith(curr) && query.equals(curr.getUsername())) {
                    //System.out.println("Found a match");
                    matches.add(curr);
                }
            }
        }

        arrayAdapter.clear();
        arrayAdapter.addAll(matches);
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * Polls whatever powers that be to populate the Registered Users set
     *
     * @return a set of all registered users who are not your friends
     */
    private void populateUsers() {
        Set<User> users = new HashSet<User>();
        if (State.local) {
            ArrayList<User> toAdd = new ArrayList<>();
            toAdd.add(new User("Dog Man L", "woofwoof", "dog@man.com"));
            toAdd.add(new User("frog", "qwrg", "frog@leg.biz"));
            toAdd.add(new User("toad", "xfsdf", "collin@126.xxx"));
            toAdd.add(new User("cricket", "asrgh", "ypres@wat.ru"));

            for (User possibleFriend: toAdd) {
                if (!User.loggedIn.isFriendsWith(possibleFriend)) {

                    users.add(possibleFriend);
                }
            }
            registeredUsers = users;
        } else { //DATABASE SHIT (get a list of possible friends from database)
            mGetPossibleFriendsTask = new UserGetPossibleFriendsTask();
            mGetPossibleFriendsTask.execute();
        }
    }

    @Override
    public void onResume() {
        populateUsers();
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



    public class UserGetPossibleFriendsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            //DATABASE SHIT (get a list of possible friends from database)
            Set<User> users = new HashSet<User>();
            String TAG = Register.class.getSimpleName();
            String link = server_url + "/getpossiblefriends.php?username=";
            try {//kek
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                String result = sb.toString();
                JSONObject jsonResults = new JSONObject(result);
                //now need to populate users with the users in jsonResults
                JSONArray jArray = jsonResults.getJSONArray("AccountHolders");
                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject currentObject = jArray.getJSONObject(i);
                        String username = currentObject.getString("username");
                        String email = currentObject.getString("email");
                        User toAdd = new User(username, "", email);
                        users.add(toAdd);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                registeredUsers = users; //populates list of possible friends
                return true;
            } catch (Exception e) {
                Log.e(TAG, "EXCEPTION while getting friends from database>>>", e);
                return false;
            }
        }


        @Override
        protected void onCancelled() {
            mGetPossibleFriendsTask = null;
        }
    }

    public class UserAddFriendTask extends AsyncTask<Void, Void, Boolean> {

        private String mUsernameOfFriendToAdd;
        public UserAddFriendTask(String usernameOfFriendToAdd) {
            mUsernameOfFriendToAdd = usernameOfFriendToAdd;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            String TAG = Register.class.getSimpleName();

            String adder = "";
            String addee = mUsernameOfFriendToAdd;

            String link = server_url + "/addfriend.php?adder=" + adder + "&addee=" + addee;
            try {//kek
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                client.execute(request);
                return true;
            } catch(Exception e) {
                Log.e(TAG, "EXCEPTION while getting possible friends from database>>>", e);
                return false;
            }
        }


        @Override
        protected void onCancelled() {
            mAddFriendTask = null;
        }
    }

}
