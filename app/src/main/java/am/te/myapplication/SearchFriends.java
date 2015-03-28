package am.te.myapplication;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import am.te.myapplication.Service.AddFriendTask;
import am.te.myapplication.Service.UserTask;


public class SearchFriends extends Activity {

    private EditText mNameView;
    private EditText mEmailView;

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
        UserTask mUserAddTask = new AddFriendTask(name, email, mEmailView,
                                                  this);
        mUserAddTask.execute();
        mUserAddTask = null;
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

