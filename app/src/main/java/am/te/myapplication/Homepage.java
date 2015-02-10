package am.te.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * The homepage class acts as a springboard to other areas of the app.
 * It displays a list of deals the user is shopping for.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 - February - 09
 */
public class Homepage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homepage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar
        // Opens the friends menu if the user presses the 'friends' button
        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        switch (item.getItemId()) {
            case R.id.friend_menu:
                openFriends();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openFriends() {
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }
}
