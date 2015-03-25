package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
//import am.te.myapplication.Service.RegisterDealTask;


/**
 * This displays the details of your friend that you clicked on
 *
 * @author not Collin Caldwell
 * @version 1.0
 * @since not 2015 - February - 26
 */
public class FriendListingDetails extends Activity {
    Listing currentFriendListing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_listing_details);


        Bundle extras = getIntent().getExtras();
        if (State.local) {
            if (extras != null) {
                // grab the listing here.
               // currentListing = Agent.getLoggedIn().getListing(extras.getString("products"));
            }
        } else { //database access
            currentFriendListing = FriendListings.selectedFriendListing;
        }
        /// this is where we'd grab the details to display on screen. Look at friend details for a better hint of what we're trying to do.
        TextView listingName = (TextView) findViewById(R.id.Name);
        listingName.setText(currentFriendListing.getName());

        TextView desiredPrice = (TextView) findViewById(R.id.DesiredPrice);
        desiredPrice.setText(String.valueOf(currentFriendListing.getDesiredPrice()));

        TextView additionalInfo = (TextView) findViewById(R.id.AdditionalInfo);
        additionalInfo.setText(currentFriendListing.getAdditionalInfo());
    }

    public void registerDeal(View view) {
        Intent intent = new Intent(this, AddDeal.class);
        startActivity(intent);
        finish();
    }

}
