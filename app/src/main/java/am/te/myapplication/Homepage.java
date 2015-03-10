package am.te.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;

/**
 * The homepage class acts as a springboard to other areas of the app.
 * It displays a list of deals the user is shopping for.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 01
 */
public class Homepage extends ActionBarActivity {

    private ListView lv;
    private ArrayAdapter arrayAdapter;
    List<Listing> products = new ArrayList<Listing>();
    private PopulateProductsTask mPopulateProductsTask;
    static Listing selectedListing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.product_listView);

        //local

        if (State.local && User.loggedIn != null && User.loggedIn.hasItems()) {
            products = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(User.loggedIn)).getItemList();
        } else {
            /* Get products from the database. */
            mPopulateProductsTask = new PopulateProductsTask();
            mPopulateProductsTask.execute();
        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<Listing>(
                this,
                android.R.layout.simple_list_item_1,
                products);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(), ListingDetails.class);
                if (State.local) {
                    i.putExtra("products", products.get(position).getName());
                } else {
                    selectedListing = products.get(position);
                }
                startActivity(i);

            }
        });
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homepage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar
        // Opens the friends menu if the user presses the 'friends' button
        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        switch (item.getItemId()) {
            case R.id.friend_menu:
                openFriends();
                return true;
            case R.id.add_product:
                addProduct();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        arrayAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void openFriends() {
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void addProduct() {
        Intent intent = new Intent(this, AddListing.class);
        startActivity(intent);
        //arrayAdapter.clear();
        //arrayAdapter.addAll(User.loggedIn.getItemList());
        arrayAdapter.notifyDataSetChanged();
    }

    public class PopulateProductsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            //DATABASE SHIT (get a list of possible friends from database)
            ArrayList<Listing> theListings = new ArrayList<>();
            String TAG = Homepage.class.getSimpleName();
            String link = "http://artineer.com/sandbox" + "/getlistings.php?userID=" + Login.uniqueIDofCurrentlyLoggedIn;
            try {//kek
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                String result = sb.toString();
                //now need to populate friends with users from result of database query
                if (result.equals("0 results")) {
                    Log.d(TAG, result);
                    return false;
                }
                String[] resultLines = result.split("<br>");
                for(int i = 0; i < resultLines.length; i++) {
                    String[] fields = resultLines[i].split("~");
                    String title = fields[0];
                    String price = fields[1];
                    String description = fields[2];

                    Listing newListing = new Listing(title, Double.parseDouble(price), description);
                    theListings.add(newListing);
                }
                products.clear();
                products.addAll(theListings);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       arrayAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            } catch (Exception e) {
                Log.e(TAG, "EXCEPTION on homepage>>>", e);
                return false;
            }

        }


        @Override
        protected void onCancelled() {
            mPopulateProductsTask = null;
        }
    }
}
