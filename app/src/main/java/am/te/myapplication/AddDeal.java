package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import am.te.myapplication.Service.RegisterDealTask;
import am.te.myapplication.Service.UserTask;


public class AddDeal extends Activity {

    private EditText priceView;
    private String listingName;
    private String location = "0;0";
    private static final Logger log = Logger.getLogger("AddDeal");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);
        priceView = (EditText) findViewById(R.id.add_price);
//        friendListingId = FriendListings.selectedFriendListing.id;
        EditText latText = (EditText) findViewById(R.id.lat);
        latText.setText(String.valueOf(0.0));
        EditText lngText = (EditText) findViewById(R.id.lng);
        lngText.setText(String.valueOf(0.0));

        this.listingName = getIntent().getExtras().getString("listing");
    }

    public void submitDeal(View v) {

        log.log(Level.INFO, "Submitting deal from view " + v.toString());

        boolean cancel = false; // If an error occurs, cancel the operation
        //String name = nameView.getText().toString();
        double price = 0.0;

        try {
            price = Double.valueOf(priceView.getText().toString());
        } catch (NumberFormatException e) {
            priceView.setError(getString(R.string.error_invalid_price));
            cancel = true;
        } catch (NullPointerException e) {
            priceView.setError(getString(R.string.error_null_price));
            cancel = true;
        }


        if (!cancel) {
            UserTask mRegisterDealTask = new RegisterDealTask(listingName,
                                                         price, location, this);
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
//            products.add(newListing);
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