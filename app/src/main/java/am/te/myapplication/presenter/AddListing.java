package am.te.myapplication.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import java.util.logging.Level;
import java.util.logging.Logger;

import am.te.myapplication.model.Listing;
import am.te.myapplication.R;
import am.te.myapplication.service.RegisterListingTask;
import am.te.myapplication.service.UserTask;


public class AddListing extends Activity {

    private EditText nameView;
    private EditText priceView;
    private EditText additionalInfoView;
    private static final Logger log = Logger.getLogger("AddListing");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        nameView = (EditText) findViewById(R.id.add_listing_name);
        priceView = (EditText) findViewById(R.id.add_listing_price);
        additionalInfoView = (EditText) findViewById(
                R.id.add_listing_additionalInfo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListing(View v) {

        log.log(Level.INFO, "Submitting listing from view " + v.toString());

        boolean cancel = false; /* If an error occurs, cancel the operation */
        String name = nameView.getText().toString();
        Double price = 0.0;
        String description = additionalInfoView.getText().toString();

        try {
            price = Double.valueOf(priceView.getText().toString());
        } catch (NumberFormatException e) {
            priceView.setError(getString(R.string.error_invalid_price));
            cancel = true;
        } catch (NullPointerException e) {
            priceView.setError(getString(R.string.error_null_price));
            cancel = true;
        }

        Listing newListing = new Listing(name, price, description);

        if (!cancel) {
            UserTask task = new RegisterListingTask(newListing, this);
            task.execute();
            Intent listingData = new Intent();
            listingData.putExtra("Name", name);
            listingData.putExtra("Price", price);
            listingData.putExtra("Additional", description);
            setResult(1, listingData);
            finish();
        }
    }
}