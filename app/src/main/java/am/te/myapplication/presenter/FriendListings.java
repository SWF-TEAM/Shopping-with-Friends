package am.te.myapplication.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import am.te.myapplication.R;
import am.te.myapplication.model.Listing;
import am.te.myapplication.model.User;
import am.te.myapplication.service.PopulateFriendsTask;
import am.te.myapplication.service.PopulateListingsTask;
import am.te.myapplication.service.UserTask;
import am.te.myapplication.util.AlertListingAdapter;
import am.te.myapplication.util.NavigationHandler;

/**
 * list of friend's listings
 *
 * @author not Mitchell Manguno
 * @version 1.0
 * @since not 2015 March 01
 */
public class FriendListings extends ActionBarActivity {

    private AlertListingAdapter arrayAdapter;
    private final List<Listing> friendListings = new ArrayList<>();
    private final List<User> friends = new ArrayList<>();
    private static final Logger log = Logger.getLogger("FriendListings");
    private NavigationHandler nav;

    public static Listing selectedFriendListing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_listings);
        nav = new NavigationHandler(this,arrayAdapter);
    }

    @Override
    public void onStart() {

        ListView lv = (ListView) findViewById(
                                       R.id.activity_friends_listings_listView);

        lv.setEmptyView(findViewById(R.id.empty_friend_listings));

        //local
        arrayAdapter = new AlertListingAdapter(this, friendListings);

        /* get friends from database */
        UserTask mPopulateFriendsTask = new PopulateFriendsTask(friends);
        mPopulateFriendsTask.execute();

        try {
            mPopulateFriendsTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(friends);

        /* Get friends' listings from the database. */
        List<Listing> allListings = new ArrayList<>();
        for (User friend: friends) {
            String friendID = friend.getId();
            List<Listing> currFriendListings = new ArrayList<>();
            UserTask mListingsTask = new PopulateListingsTask(
                                                             currFriendListings,
                                                                   arrayAdapter,
                                                                           this,
                                                                      friendID);

            // Should update arrayAdapter automatically with fetch of each
            // friend's listing data
            mListingsTask.execute();
            try {
                mListingsTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            allListings.addAll(currFriendListings);
        }
        friendListings.clear();
        friendListings.addAll(allListings);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Pass user clicked on to new Friend Details Page
                Intent i = new Intent(getApplicationContext(),
                                      FriendListingDetails.class);
                selectedFriendListing = friendListings.get(position);
                startActivity(i);
            }
        });

        arrayAdapter.notifyDataSetChanged();
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_listings, menu);
        return true;
    }

    @Override
    public void onResume() {
        arrayAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar
        // Opens the friends menu if the user presses the 'friends' button
        // see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        return nav.openMenuItem(item);
    }


    static Listing getSelectedFriendListing() {
        return selectedFriendListing;
    }
}
