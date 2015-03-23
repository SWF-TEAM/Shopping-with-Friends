package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;


/**
 * This displays the details of your friend that you clicked on
 *
 * @author Collin Caldwell
 * @version 1.0
 * @since 2015 - February - 26
 */
public class ListingDetails extends Activity {
    Listing currentListing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);


        Bundle extras = getIntent().getExtras();
        if (State.local) {
            if (extras != null) {
                // grab the listing here.
                currentListing = Agent.getLoggedIn().getListing(extras.getString("products"));
            }
        } else { //database access
            currentListing = Homepage.selectedListing;
        }
        /// this is where we'd grab the details to display on screen. Look at friend details for a better hint of what we're trying to do.
        TextView listingName = (TextView) findViewById(R.id.Name);
        listingName.setText(currentListing.getName());

        TextView desiredPrice = (TextView) findViewById(R.id.DesiredPrice);
        desiredPrice.setText(String.valueOf(currentListing.getDesiredPrice()));

        TextView additionalInfo = (TextView) findViewById(R.id.AdditionalInfo);
        additionalInfo.setText(currentListing.getAdditionalInfo());
    }

    public void expressIntrest(View view) {
        finish();
    }

}
