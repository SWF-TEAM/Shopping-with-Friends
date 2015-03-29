package am.te.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
import am.te.myapplication.Service.PopulateFriendsTask;
import am.te.myapplication.Service.PopulateProductsTask;
import am.te.myapplication.Service.UserTask;
import am.te.myapplication.Util.AlertListingAdapter;

/**
 * list of friend's listings
 *
 * @author not Mitchell Manguno
 * @version 1.0
 * @since not 2015 March 01
 */
public class FriendListings extends ActionBarActivity {

    private AlertListingAdapter arrayAdapter;
    private List<Listing> friendListings = new ArrayList<>();
    private List<User> friends = new ArrayList<>();
    private boolean notify;

    public static Listing selectedFriendListing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_listings);
    }

    @Override
    public void onStart() {

        ListView lv = (ListView) findViewById(R.id.activity_friends_listings_listView);

        //local
        arrayAdapter = new AlertListingAdapter(this, friendListings);
        if (State.local && Agent.getLoggedIn() != null && Agent.getLoggedIn().hasItems()) {
            friendListings = RegistrationModel.getUsers().get(RegistrationModel.getUsers().indexOf(Agent.getLoggedIn())).getItemList();
        } else {
            /* get friends from database */
            UserTask mPopulateFriendsTask = new PopulateFriendsTask(friends);
            mPopulateFriendsTask.execute();
            try {
                mPopulateFriendsTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            /* Get friend listings from the database. */
            List<Listing> allListings = new ArrayList<>();
            for (User friend: friends) {
                String friendID = friend.getId();
                List<Listing> currFriendListings = new ArrayList<>();
                PopulateProductsTask mListingsTask = new PopulateProductsTask(currFriendListings, notify, arrayAdapter, this, friendID);
                mListingsTask.execute(); //should update arrayAdapter automatically with fetch of each friend's listing data
                try {
                    mListingsTask.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                allListings.addAll(currFriendListings);
            }
            System.out.println(">>>>>>>>size of allListings: " + allListings.size());
            friendListings.clear();
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
                if (!State.local) {
                    selectedFriendListing = friendListings.get(position);
                }
                startActivity(i);
            }
        });

        arrayAdapter.notifyDataSetChanged();
        super.onStart();

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu items for use in the action bar
//        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
////        MenuInflater inflater = getMenuInflater();
////        inflater.inflate(R.menu.menu_homepage, menu);
////        return super.onCreateOptionsMenu(menu);
//        return null;
//    }

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

    void openFriends() {
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void addDeal() {
        Intent intent = new Intent(this, AddDeal.class);
        startActivity(intent);
        arrayAdapter.notifyDataSetChanged();
    }

    void addProduct() {
        Intent intent = new Intent(this, AddListing.class);
        startActivity(intent);
        arrayAdapter.notifyDataSetChanged();
    }
}
