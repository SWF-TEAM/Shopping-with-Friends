package am.te.myapplication;

import android.content.Context;
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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
import am.te.myapplication.Service.PopulateFriendsListingsTask;
import am.te.myapplication.Service.PopulateFriendsTask;
import am.te.myapplication.Service.PopulateProductsTask;
import am.te.myapplication.Util.AlertListingAdapter;

/**
 * list of friend's listings
 *
 * @author not Mitchell Manguno
 * @version 1.0
 * @since not 2015 March 01
 */
public class FriendListings extends ActionBarActivity {

    private ListView lv;
    private AlertListingAdapter arrayAdapter;
    List<Listing> friendListings = new ArrayList<Listing>();
    private PopulateFriendsListingsTask mPopulateFriendsListingsTask;
    List<User> friends = new ArrayList<User>();

    private PopulateFriendsTask mPopulateFriendsTask;

    public static Listing selectedFriendListing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_listings);
    }

    @Override
    public void onStart() {

        lv = (ListView) findViewById(R.id.activity_friends_listings_listView);

        //local
        arrayAdapter = new AlertListingAdapter(this, friendListings);
        if (State.local && Agent.getLoggedIn() != null && Agent.getLoggedIn().hasItems()) {
            friendListings = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(Agent.getLoggedIn())).getItemList();
        } else {
            /* get friends from database */
            mPopulateFriendsTask = new PopulateFriendsTask(friends);
            mPopulateFriendsTask.execute();
            try {
                mPopulateFriendsTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            /* Get friend listings from the database. */
            List<Listing> allListings = new ArrayList<>();
            System.out.println("number of friends to iterate thru: " + friends.size());
            for (User friend: friends) {
                String friendID = friend.getId();
                List<Listing> currFriendListings = new ArrayList<>();
                PopulateProductsTask mListingsTask = new PopulateProductsTask(currFriendListings, arrayAdapter, this, friendID);
                mListingsTask.execute(); //should update arrayAdapter automatically with fetch of each friend's listing data
                System.out.println("mListingsTask started execution");
                try {
                    mListingsTask.get();
                    System.out.println("mListingsTask finished execution");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                allListings.addAll(currFriendListings);
            }
            System.out.println(">>>>>>>>size of allListings: " + allListings.size());
            friendListings.addAll(allListings);
        }
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(), FriendListingDetails.class);
                if (State.local) {
                   // i.putExtra("products", products.get(position).getName());
                   // why are u local
                   // stop that
                } else {
                    selectedFriendListing = friendListings.get(position);
                }
                startActivity(i);

            }
        });

        arrayAdapter.notifyDataSetChanged();
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
            case R.id.add_deal:
                addDeal();
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

    public void addDeal() {
        Intent intent = new Intent(this, AddDeal.class);
        startActivity(intent);
        //arrayAdapter.clear();
        //arrayAdapter.addAll(User.loggedIn.getItemList());
        arrayAdapter.notifyDataSetChanged();
    }

    public void addProduct() {
        Intent intent = new Intent(this, AddListing.class);
        startActivity(intent);
        arrayAdapter.notifyDataSetChanged();
    }
}
