package am.te.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendList extends ActionBarActivity {

    // Creates the listview to hold the users.
    private ListView lv;
    private ArrayAdapter<User> arrayAdapter;
    List<User> friends = new ArrayList<User>();

    private final String server_url = "http://artineer.com/sandbox";
    UserPopulateFriendsTask mUserPopulateFriendsTask = null;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_friend_list);
    }
    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.add_friend_listView);

        //local

        if (State.local && User.loggedIn != null && User.loggedIn.hasFriends()) {
            friends = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(User.loggedIn)).getFriends();
        } else { //database
            mUserPopulateFriendsTask = new UserPopulateFriendsTask();
            mUserPopulateFriendsTask.execute();
        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                friends);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(), FriendDetails.class);
                i.putExtra("username", friends.get(position).getUsername());
                startActivity(i);

            }
        });
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar
        // Opens the friends menu if the user presses the 'friends' button
        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        switch (item.getItemId()) {
            case R.id.friend_menu:
                openAddFriends();
                //arrayAdapter.clear();
                //arrayAdapter.addAll(User.loggedIn.getFriends());
                arrayAdapter.notifyDataSetChanged();
                return true;
            case R.id.search_friend:
                openSearchFriends();
                //arrayAdapter.clear();
                //arrayAdapter.addAll(User.loggedIn.getFriends());
                arrayAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        arrayAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public boolean onSearchRequested() {
        Intent intent = new Intent(this, SearchFriends.class);
        startActivity(intent);
        return true;
    }

    public void openAddFriends() {
        Intent intent = new Intent(this, AddFriend.class);
        startActivity(intent);
    }

    public void openSearchFriends() {
        Intent intent = new Intent(this, SearchFriends.class);
        startActivity(intent);
    }


    public class UserPopulateFriendsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            //DATABASE SHIT (get a list of possible friends from database)
            ArrayList<User> friends = new ArrayList<>();
            String TAG = Register.class.getSimpleName();
            String link = server_url + "/getfriendsof.php?UID=" + Login.uniqueIDofCurrentlyLoggedIn;
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
                        friends.add(toAdd);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                friends = friends; //populates list of possible friends
                return true;
            } catch (Exception e) {
                Log.e(TAG, "EXCEPTION while getting friends from database>>>", e);
                return false;
            }
        }


        @Override
        protected void onCancelled() {
            mUserPopulateFriendsTask = null;
        }
    }
}
