package am.te.myapplication;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.User;


public class AddFriend extends ActionBarActivity {
    protected List<User> possibleFriends = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ListView lv = (ListView) findViewById(R.id.add_friend_listView);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        final ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, possibleFriends);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //add friend to friend list here
                Log.d(AddFriend.class.getSimpleName(), "position" + position + " id" + id);
                //local
                if (State.local) {
                    Agent.getLoggedIn().addFriend(possibleFriends.get(position));
                    possibleFriends.remove(position);
                    arrayAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
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
}
