package am.te.myapplication.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.R;
import am.te.myapplication.model.Listing;
import am.te.myapplication.model.User;
import am.te.myapplication.service.PopulateListingsTask;
import am.te.myapplication.service.UserTask;
import am.te.myapplication.util.AlertListingAdapter;
import am.te.myapplication.util.NavigationHandler;

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
    private final List<Listing> listings = new ArrayList<>();
    private BaseAdapter arrayAdapter;
    static Listing selectedListing;
    private NavigationHandler nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        arrayAdapter = new AlertListingAdapter(
                this, listings);
        nav = new NavigationHandler(this,arrayAdapter);

        Button mAddListingButton = (Button) findViewById(R.id.add_listing_button);
        mAddListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.launchActivity(AddListing.class);
            }
        });
    }

    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.listing_listView);
        lv.setEmptyView(findViewById(R.id.empty_wishlist));


        /* Get products from the database. */
        UserTask mPopulateProductsTask = new PopulateListingsTask(listings,
                                                                  arrayAdapter,
                                                                  this,
                                         User.getUniqueIDofCurrentlyLoggedIn());
        mPopulateProductsTask.execute();

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(),
                                                          ListingDetails.class);
                    selectedListing = listings.get(position);
                startActivity(i);

            }
        });

        arrayAdapter.notifyDataSetChanged();
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
        return nav.openMenuItem(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayAdapter.notifyDataSetChanged();
        lv.requestLayout();
    }


    @Override
    protected void onActivityResult( int aRequestCode, int aResultCode,
                                                                  Intent data) {
        if (data != null) {
            Listing newListing = Listing.getListingFromIntent(data);
            listings.add(newListing);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
