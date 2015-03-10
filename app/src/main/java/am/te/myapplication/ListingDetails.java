package am.te.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


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
        if (getResources().getString(R.string.state).equals("local")) {
            if (extras != null) {
                // grab the listing here.
                currentListing = User.loggedIn.getListing(extras.getString("listing"));
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

    /**
     * Claims an item
     *
     * @param view
     */
    public void claimItem(View view) {

       /// ????
        finish();
    }
}
