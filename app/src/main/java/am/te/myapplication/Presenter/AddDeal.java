package am.te.myapplication.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import am.te.myapplication.R;
import am.te.myapplication.Service.RegisterDealTask;
import am.te.myapplication.Service.UserTask;
import am.te.myapplication.Model.Deal;

public class AddDeal extends Activity {

    private EditText priceView;
    private EditText nameView;
    private EditText descriptionView;

    private String listingID;
    private String location = "0;0";
    private static final Logger log = Logger.getLogger("AddDeal");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);

        nameView = (EditText) findViewById(R.id.add_deal_name);
        priceView = (EditText) findViewById(R.id.add_deal_price);
        descriptionView = (EditText)
                findViewById(R.id.add_deal_additionalInfo);
        //friendListingId = FriendListings.selectedFriendListing.id;

        EditText latText = (EditText) findViewById(R.id.lat);
        latText.setText(String.valueOf(0.0));
        EditText lngText = (EditText) findViewById(R.id.lng);
        lngText.setText(String.valueOf(0.0));

        this.listingID = FriendListings.selectedFriendListing.id; //violates law of demeter??
    }

    /**
     * This method validates form entry from the view, then attempts to register it
     * 
     * @param v the addDeal view
     */
    public void submitDeal(View v) {
        log.log(Level.INFO, "Attempting to submit deal from view " + v.toString());

        boolean cancel = false; // If an error occurs, cancel the operation
        double price = 0.0;

        String name = nameView.getText().toString();
        String description = descriptionView.getText().toString();

        try {
            price = Double.valueOf(priceView.getText().toString());
        } catch (NumberFormatException e) {
            priceView.setError(getString(R.string.error_invalid_price));
            cancel = true;
        } catch (NullPointerException e) {
            priceView.setError(getString(R.string.error_null_price));
            cancel = true;
        }

        //listingID, dealID, name, description, price, location, claimed
        Deal newDeal = new Deal(listingID, "", name, description, price, location, false);

        if (!cancel) {
            UserTask mRegisterDealTask = new RegisterDealTask(newDeal, this);
            mRegisterDealTask.execute();
            mRegisterDealTask = null;
            finish();
        }

    }

    public void openMap(View v) {

        log.log(Level.INFO, "Openning map from view " + v.toString());

        Intent intent = new Intent(this, Map.class);
        startActivityForResult(intent, 1);

    }
    @Override
    protected void onActivityResult( int aRequestCode, int aResultCode,
                                     Intent data) {
//        if (data != null) {
//            Listing newListing = Listing.getListingFromIntent(data);
//            listings.add(newListing);
//            arrayAdapter.notifyDataSetChanged();
//        }
        Bundle extras = data.getExtras();
        String lat = String.valueOf(extras.getDouble("lat"));
        String lng = String.valueOf(extras.getDouble("lng"));
        System.out.println("Lat: " + lat);
        System.out.println("Lng: " + lng);
        EditText latText = (EditText) findViewById(R.id.lat);
        latText.setText(lat);
        EditText lngText = (EditText) findViewById(R.id.lng);
        lngText.setText(lng);
        this.location = lat+";"+lng;


    }
}