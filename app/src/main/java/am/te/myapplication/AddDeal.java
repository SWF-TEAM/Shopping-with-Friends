package am.te.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;

import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;


public class AddDeal extends Activity {

    private EditText nameView;
    private EditText priceView;
    private EditText locationView;
    private UserRegisterDealTask mRegisterDealTask;
    private final String server_url = "http://artineer.com/sandbox";
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
                mRegisterDealTask = new UserRegisterDealTask(name, price, location);
                mRegisterDealTask.execute();

            }
            finish();
        }


    }





    public class UserRegisterDealTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final Double mPrice;
        private final String mLocation;

        public UserRegisterDealTask(String name, Double price, String location) {
            mName = name;
            mPrice = price;
            mLocation = location;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if (State.local) {
                //lol pls no local
                return false;
            } else {
                // authentication against a network service.
                // check if user is in system
                // register user if not in system
                return registerProduct();
            }
        }

        protected boolean registerProduct() {
            String TAG = AddListing.class.getSimpleName();
            String link = null;
            try {
                link = server_url + "/adddeal.php?Title=" + Encoder.encode(mName) + "&Location=" + Encoder.encode(mLocation) + "&Price=" + mPrice + "&userID=" + Login.uniqueIDofCurrentlyLoggedIn;
            } catch(UnsupportedEncodingException e){
                Log.e(TAG, "url encoding failed");
            }
            try {
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.d(TAG, sb.toString());
                return sb.toString().equals("success");
            }catch(Exception e){
                Log.e(TAG, "EXCEPTION>>>>", e);
                return false;
            }
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterDealTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                //database says this product already exists
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterDealTask= null;
        }

    }
}