package am.te.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * The homepage class acts as a springboard to other areas of the app.
 * It displays a list of deals the user is shopping for.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 01
 */
public class Homepage extends ActionBarActivity {

    private ListView lv;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.product_listView);
        List<Product> products = new ArrayList<Product>();
        //local

        if (State.local && User.loggedIn != null && User.loggedIn.hasItems()) {
            products = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(User.loggedIn)).getItemList();
        } else {
            /* Get products from the database. */
        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<Product>(
                this,
                android.R.layout.simple_list_item_1,
                products);

        lv.setAdapter(arrayAdapter);
        super.onStart();
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
            case R.id.add_product:
                addProduct();
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

    public void openFriends() {
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void addProduct() {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
        //arrayAdapter.clear();
        //arrayAdapter.addAll(User.loggedIn.getItemList());
        arrayAdapter.notifyDataSetChanged();
    }
}
