package am.te.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class FriendList extends ActionBarActivity {

    // Creates the listview to hold the users.
    private ListView lv;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_friend_list);

    }
    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.add_friend_listView);
        List<User> friends = new ArrayList<User>();
        //local

        if (State.local) {
            friends = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(User.loggedIn)).getFriends();
        } else {

        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                friends);

        lv.setAdapter(arrayAdapter);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAddFriends() {
        Intent intent = new Intent(this, AddFriend.class);
        startActivity(intent);
    }

}
