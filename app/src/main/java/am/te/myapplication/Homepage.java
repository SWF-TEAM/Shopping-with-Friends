package am.te.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
import am.te.myapplication.Service.PopulateDealsTask;
import am.te.myapplication.Service.PopulateProductsTask;
import am.te.myapplication.Util.AlertListingAdapter;

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
    private AlertListingAdapter arrayAdapter;
    List<Listing> products = new ArrayList<Listing>();
    List<Deal> deals = new ArrayList<>();
    private PopulateProductsTask mPopulateProductsTask;
    private PopulateDealsTask mPopulateDealsTask;
    static Listing selectedListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.product_listView);
        arrayAdapter = new AlertListingAdapter(this, products);

        //local

        if (State.local && Agent.getLoggedIn() != null
                && Agent.getLoggedIn().hasItems()) {
            products = RegistrationModel.getUsers().get(
                                           RegistrationModel.getUsers().indexOf(
                                            Agent.getLoggedIn())).getItemList();
        } else {
            /* Get products from the database. */
            mPopulateProductsTask = new PopulateProductsTask(products, arrayAdapter, this, User.getUniqueIDofCurrentlyLoggedIn());
            mPopulateProductsTask.execute();

        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(),
                                                          ListingDetails.class);
                products.get(position).setHasBeenSeen(true);
                if (State.local) {
                    i.putExtra("products", products.get(position).getName());
                } else {
                    selectedListing = products.get(position);
                }
                startActivity(i);

            }
        });

        arrayAdapter.notifyDataSetChanged();

        boolean foundDeal = false;

        System.out.println("Deals: " + deals.size());
        System.out.println("Listing: " + products.size());


        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu items for use in the action bar
        //see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homepage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle presses on the action bar
        //Opens the friends menu if the user presses the 'friends' button
        //see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        switch (item.getItemId()) {
            case R.id.friend_menu:
                openFriends();
                return true;
            case R.id.add_product:
                addProduct();
                return true;
            case R.id.add_deal:
                addDeal();
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
        super.onResume();
        arrayAdapter.notifyDataSetChanged();
        lv.requestLayout();
    }
    public void openFriendsListings() {
        Intent intent = new Intent(this, FriendListings.class);
        startActivity(intent);
    }

    public void openFriends() {
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void addProduct() {
        Intent intent = new Intent(this, AddListing.class);
        startActivityForResult(intent, 1);
        //arrayAdapter.clear();
        //arrayAdapter.addAll(User.loggedIn.getItemList());
        arrayAdapter.notifyDataSetChanged();
    }

    public void addDeal() {
        Intent intent = new Intent(this, AddDeal.class);
        startActivity(intent);
        //arrayAdapter.clear();
        //arrayAdapter.addAll(User.loggedIn.getItemList());
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult( int aRequestCode, int aResultCode,
                                                                  Intent data) {
        if (data != null) {
            Listing newListing = Listing.getListingFromIntent(data);
            products.add(newListing);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
