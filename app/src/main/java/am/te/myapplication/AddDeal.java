package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import am.te.myapplication.Model.Deal;
import am.te.myapplication.Service.RegisterDealTask;
import am.te.myapplication.Service.UserTask;


public class AddDeal extends Activity {

    private EditText nameView;
    private EditText priceView;
    private EditText locationView;
    private UserTask mRegisterDealTask;

    private String friendListingId;
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
    }

    public void submitDeal(View view) {

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

        String location = locationView.getText().toString();

        if (!cancel) {
            if (State.local) {
                //Deal newDeal = new Deal(name, price, location);
            } else {
                mRegisterDealTask = new RegisterDealTask(name, price,
                                                                location, this);
                mRegisterDealTask.execute();
                mRegisterDealTask = null;
            }
            finish();
        }

    }

    public void openMap(View view) {
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
        Double lat = extras.getDouble("lat");
        Double lng = extras.getDouble("lng");
        EditText latText = (EditText) findViewById(R.id.lat);
        latText.setText(String.valueOf(lat));
        EditText lngText = (EditText) findViewById(R.id.lng);
        lngText.setText(String.valueOf(lng));


    }
}