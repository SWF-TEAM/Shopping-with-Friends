package am.te.myapplication.presenter;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import am.te.myapplication.R;
import am.te.myapplication.model.User;
import am.te.myapplication.service.AddFriendTask;
import am.te.myapplication.service.UserTask;


public class SearchFriends extends Activity {

    private EditText nameView;
    private EditText emailView;
    private static final Logger log = Logger.getLogger("SearchFriends");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        nameView = (EditText) findViewById(R.id.name);
        emailView = (EditText) findViewById(R.id.email);
        super.onStart();
    }


    /**
     * The OnClick listener for the "OK" button. Calls the real search method
     * with whatever is inside the text-field at the time.
     *
     */
    public void search(View v) {

        log.log(Level.INFO, "Searching, view is " + v.toString());

        String name = nameView.getText().toString();
        String email = emailView.getText().toString();

        //String username, String password, String email, String id,
        //String description, String name
        User newFriend = new User(name,email);
        UserTask addFriendTask = new AddFriendTask(newFriend, this);

        addFriendTask.execute();
        addFriendTask = null;
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
}

