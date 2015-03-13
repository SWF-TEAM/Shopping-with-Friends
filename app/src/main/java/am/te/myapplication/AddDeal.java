package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;


public class AddDeal extends Activity {

    private EditText nameView;
    private EditText priceView;
    private EditText locationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);
        nameView = (EditText) findViewById(R.id.add_deal_name);
        priceView = (EditText) findViewById(R.id.add_price);
        locationView = (EditText) findViewById(R.id.add_location);
    }



    public void submitDeal(View view) {


        boolean cancel = false; /* If an error occurs, cancel the operation */
        String name = nameView.getText().toString();
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
        finish();
        if (!cancel) {
            if (State.local) {
                Deal newDeal = new Deal(name, price, location);

            } else {
                //databasey stuff

            }
            finish();
        }


    }
}