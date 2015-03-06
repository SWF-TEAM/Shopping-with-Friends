package am.te.myapplication;

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
import java.net.URI;
import java.net.URL;


public class AddListing extends ActionBarActivity {

    private EditText nameView;
    private EditText priceView;
    private EditText additionalInfoView;
    private UserRegisterProductTask mRegisterProductTask = null;
    private final String server_url = "http://artineer.com/sandbox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        nameView = (EditText) findViewById(R.id.add_product_name);
        priceView = (EditText) findViewById(R.id.add_product_price);
        additionalInfoView = (EditText) findViewById(R.id.add_product_additionalInfo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_product, menu);
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

    public void addProduct(View view) {


        boolean cancel = false; /* If an error occurs, cancel the operation */
        String name = nameView.getText().toString();
        Double price = 0.0;

        try {
            price = Double.valueOf(priceView.getText().toString());
        } catch (NumberFormatException e) {
            priceView.setError(getString(R.string.error_invalid_price));
            cancel = true;
        } catch (NullPointerException e) {
            priceView.setError(getString(R.string.error_null_price));
            cancel = true;
        }

        String additionalInfo = additionalInfoView.getText().toString();

        if (!cancel) {
            if (State.local) {
                Listing newProduct = new Listing(name, price, additionalInfo);
                System.out.println(User.loggedIn.addItem(newProduct));
                System.out.println("Added new item: " + newProduct);
                System.out.println("User items is now " + User.loggedIn.getItemList());
            } else {
                //databasey stuff 
                mRegisterProductTask = new UserRegisterProductTask(name, price, additionalInfo);
                mRegisterProductTask.execute();
            }
            finish();
        }
    }










    public class UserRegisterProductTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final Double mPrice;
        private final String mDescription;

        public UserRegisterProductTask(String name, Double price, String description) {
            mName = name;
            mPrice = price;
            mDescription = description;
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
                //already in system
            }
            /*
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            */

            // Authentication with local list of registered users (will be replaced with database auth soon^(TM))
            /*User userToAuthenticate = new User(mEmail, mPassword);
            return RegistrationModel.getUsers().contains(userToAuthenticate);*/
        }

        protected boolean registerProduct() {
            String TAG = Register.class.getSimpleName();

            String link = server_url + "/addlisting.php?title=" + mName + "&description=" + mDescription + "&price=" + mPrice;
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
            mRegisterProductTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                //database says this product already exists
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterProductTask= null;
        }
    }
}
