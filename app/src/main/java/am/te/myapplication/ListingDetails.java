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
        if (extras != null) {
            // grab the listing here.
            currentListing = null;
        }
        /// this is where we'd grab the details to display on screen. Look at friend details for a better hint of what we're trying to do.


    }
}
