package am.te.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.User;
import am.te.myapplication.Service.PopulateFriendsTask;

public class FriendList extends ActionBarActivity {

    // Creates the listview to hold the users.
    private ArrayAdapter<User> arrayAdapter;
    List<User> friends = new ArrayList<User>();
    static User selectedFriend;

    PopulateFriendsTask mPopulateFriendsTask = null;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        String TAG = Register.class.getSimpleName();
        setContentView(R.layout.activity_friend_list);
    }
    @Override
    public void onStart() {
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
        ListView lv = (ListView) findViewById(R.id.add_friend_listView);

        //local
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                friends);

        if (State.local && Agent.getLoggedIn() != null && Agent.getLoggedIn().hasFriends()) {
            friends = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(Agent.getLoggedIn())).getFriends();
        } else { //database

            mPopulateFriendsTask = new PopulateFriendsTask(friends, arrayAdapter, this);
            mPopulateFriendsTask.execute();
            mPopulateFriendsTask = null;

        }


        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(), FriendDetails.class);
                if (State.local) {
                    i.putExtra("username", friends.get(position).getUsername());
                    i.putExtra("email", friends.get(position).getEmail());
                } else { //derterbers
                    selectedFriend = friends.get(position);
                }
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

    public void refreshArrayAdapter() {
        arrayAdapter.notifyDataSetChanged();
    }
}
