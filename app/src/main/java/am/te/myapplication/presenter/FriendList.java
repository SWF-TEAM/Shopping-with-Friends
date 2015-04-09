package am.te.myapplication.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.model.User;
import am.te.myapplication.R;
import am.te.myapplication.service.UserTask;
import am.te.myapplication.util.ListFriendsAdapter;
import am.te.myapplication.service.PopulateFriendsTask;

public class FriendList extends ActionBarActivity {

    // Creates the list-view to hold the users.
    private BaseAdapter arrayAdapter;
    private final List<User> friends = new ArrayList<>();
    static User selectedFriend;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_friend_list);

        Button mAddFriendButton = (Button) findViewById(R.id.add_friend_button);
        mAddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchFriends();
            }
        });
    }
    @Override
    public void onStart() {
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
        ListView lv = (ListView) findViewById(R.id.add_friend_listView);

        lv.setEmptyView(findViewById(R.id.empty_friend_list));

        //local
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ListFriendsAdapter(this, friends);

        UserTask mPopulateFriendsTask = new PopulateFriendsTask(friends, arrayAdapter, this);
        mPopulateFriendsTask.execute();

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(), FriendDetails.class);
                    selectedFriend = friends.get(position);
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
            case R.id.search_friend:
                openSearchFriends();
                arrayAdapter.notifyDataSetChanged();
                return true;
            case R.id.friends_listings:
                openFriendsListings();
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

    void openSearchFriends() {
        Intent intent = new Intent(this, SearchFriends.class);
        startActivity(intent);
    }

    void openFriendsListings() {
        Intent intent = new Intent(this, FriendListings.class);
        startActivity(intent);
    }
}
