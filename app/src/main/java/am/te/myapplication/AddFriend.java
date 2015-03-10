package am.te.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AddFriend extends ActionBarActivity {
    private ListView lv;
    protected List<User> possibleFriends = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        lv = (ListView) findViewById(R.id.add_friend_listView);

        populate();


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
                if (getResources().getString(R.string.state).equals("local")) {
                    User.loggedIn.addFriend(possibleFriends.get(position));
                    possibleFriends.remove(position);
                    arrayAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }

    private void populate() {
        // Instantiating an array list
        possibleFriends = new ArrayList<User>();

        if (getResources().getString(R.string.state).equals("local")) {
            ArrayList<User> toAdd = new ArrayList<>();
            toAdd.add(new User("Dog Man L", "woofwoof", "dog@man.com", null, null, null));
            toAdd.add(new User("frog", "qwrg", "frog@leg.biz", null, null, null));
            toAdd.add(new User("toad", "xfsdf", "collin@126.xxx", null, null, null));
            toAdd.add(new User("cricket", "asrgh", "ypres@wat.ru", null, null, null));

            for (User possibleFriend: toAdd) {
                if (!User.loggedIn.isFriendsWith(possibleFriend)) {

                    possibleFriends.add(possibleFriend);
                }
            }
        }
    }

    //@Override
    //public void onResume() {
    //    populate();
    //    super.onResume();
    //}



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

    public class UsersAdapter<User> extends ArrayAdapter<User> {
        public UsersAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
            }
            //Lookup view for data population
            TextView userName = (TextView) convertView.findViewById(R.id.username);
            //Populate the data into the template view using the data object
            //userName.setText(user.getUsername());
            // Return the completed view to render on screen
            return convertView;
       }
    }

}
