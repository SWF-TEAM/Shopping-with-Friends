package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
import am.te.myapplication.Service.PopulateAssociatedDealsTask;
import am.te.myapplication.Service.PopulateProductsTask;
import am.te.myapplication.Util.AlertDealAdapter;
import am.te.myapplication.Util.AlertListingAdapter;


/**
 * This displays the details of your listing that you clicked on.
 *
 * @author Mitchell Manguno, Collin Caldwell
 * @version 2.0
 * @since 2015 March 25
 */
public class ListingDetails extends Activity {

    private PopulateAssociatedDealsTask mPopulateTask;
    private Listing currentListing;
    private List<Deal> deals;
    private ListView lv;
    private AlertDealAdapter adapter;

    @Override
    public void onStart() {
        deals = new ArrayList<>();
        lv = (ListView) findViewById(R.id.deals_listView);
//        adapter = new ArrayAdapter<Deal>(this, R.layout.card_contents, deals);
        adapter = new AlertDealAdapter(this, deals);

        if (!(State.local)) {
            /* Get products from the database. */
            mPopulateTask = new PopulateAssociatedDealsTask(deals,
                                                 currentListing, adapter, this);
            mPopulateTask.execute();

        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                /*
                Pass the information to either a Deal details page, or to a map.
                 */
            }
        });

        adapter.notifyDataSetChanged();

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);


        Bundle extras = getIntent().getExtras();
        if (State.local) {
            if (extras != null) {
                // grab the listing here.
                currentListing = Agent.getLoggedIn().getListing(
                                                  extras.getString("products"));
            }
        } else { //database access
            currentListing = Homepage.selectedListing;
        }
        // This is where we'd grab the details to display on screen.
        // Look at friend details for a better hint of what we're trying to do.
        TextView listingName = (TextView) findViewById(R.id.Name);
        listingName.setText(currentListing.getName());

        TextView desiredPrice = (TextView) findViewById(R.id.DesiredPrice);
        desiredPrice.setText(String.valueOf(currentListing.getDesiredPrice()));

        TextView additionalInfo = (TextView) findViewById(R.id.AdditionalInfo);
        additionalInfo.setText(currentListing.getAdditionalInfo());
    }
}
