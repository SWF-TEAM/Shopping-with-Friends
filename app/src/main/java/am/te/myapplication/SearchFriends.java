package am.te.myapplication;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Set;

import am.te.myapplication.Model.User;
import am.te.myapplication.Service.AddFriendTask;


public class SearchFriends extends Activity {

    private EditText mNameView;
    private EditText mEmailView;
    private AddFriendTask mUserAddTask;

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
        mUserAddTask = new AddFriendTask(name, email, this);
        mUserAddTask.execute();
        if (mUserAddTask.getSuccess()) {
            finish();
        } else {
            mEmailView.setError("try a different user");
        }

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

