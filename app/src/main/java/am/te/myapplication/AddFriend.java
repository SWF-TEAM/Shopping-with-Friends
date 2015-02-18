package am.te.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        lv = (ListView) findViewById(R.id.add_friend_listView);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
//        List<String> your_array_list = new ArrayList<String>();
//        your_array_list.add("Dog Man I");
//        your_array_list.add("Dog Man II");
//        your_array_list.add("Dog Man III");
//        your_array_list.add("Dog Man IV");
//        your_array_list.add("Dog Man V");
//        your_array_list.add("Dog Man VI");
//        your_array_list.add("Dog Man VII");
//        your_array_list.add("Dog Man IIX");
//        your_array_list.add("Dog Man IX");
//        your_array_list.add("Dog Man X");
//        your_array_list.add("Dog Man XI");
//        your_array_list.add("Dog Man XII");
//        your_array_list.add("Dog Man XIV");
        List<User> users = new ArrayList<User>();
        User user1 = new User("Dog Man L", "woofwoof");
        users.add(user1);



        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        return true;
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

    public class UsersAdapter extends ArrayAdapter<User> {
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
            // Lookup view for data population
            TextView userName = (TextView) convertView.findViewById(R.id.username);
            // Populate the data into the template view using the data object
            userName.setText(user.getUsername());
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
