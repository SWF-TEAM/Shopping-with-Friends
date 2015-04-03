package am.te.myapplication.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.R;
import am.te.myapplication.Service.PopulateAssociatedDealsTask;
import am.te.myapplication.Service.UserTask;
import am.te.myapplication.Util.AlertDealAdapter;

/**
 * This displays the details of your listing that you clicked on.
 *
 * @author Mitchell Manguno, Collin Caldwell
 * @version 2.0
 * @since 2015 March 25
 */
public class ListingDetails extends Activity {

    private Listing currentListing;
    private List<Deal> deals;
    private double latitude;
    private double longitude;

    @Override
    public void onStart() {
        deals = new ArrayList<>();
        final ListView lv = (ListView) findViewById(R.id.deals_listView);
//        adapter = new ArrayAdapter<Deal>(this, R.layout.card_contents, deals);
        AlertDealAdapter adapter = new AlertDealAdapter(this, deals);

        /* Get listings from the database. */
        UserTask mPopulateTask = new PopulateAssociatedDealsTask(deals,
                                                             currentListing,
                                                                 adapter,
                                                                 this);
        mPopulateTask.execute();

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                String latLong = deals.get(position).getLocation();
                String[] latLongArr = latLong.split(";");
                System.out.println(latLong);
                latitude = Double.valueOf(latLongArr[0]);
                longitude = Double.valueOf(latLongArr[1]);
                openMap();
            }
        });

        adapter.notifyDataSetChanged();

        super.onStart();
    }

    void openMap() {
        Intent intent = new Intent(this, Map.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);


        //Bundle extras = getIntent().getExtras();
        currentListing = Homepage.selectedListing;
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
