package am.te.myapplication.Service;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
import am.te.myapplication.Util.AlertListingAdapter;


/**
 * Populates an array with all of a user's friends.
 *
 * @author Mike Adkison
 * @version 1.0
 * @since 2015 March 23
 */
public class PopulateFriendsListingsTask extends UserTask {

    private AlertListingAdapter arrayAdapter; //ArrayAdapter to update
    private Activity caller;
    private boolean notifier;

    public PopulateFriendsListingsTask(AlertListingAdapter arrayAdapter, Activity caller) {
        this.arrayAdapter = arrayAdapter;
        this.caller = caller;
    }

    /**
     * Populates deals ArrayList with info from database.
     *
     * @param params the Void... that async tasks need to show progress
     * @return true if population was successful
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        /*Get list of friends to iterate through */
        List<User> friends = new ArrayList<User>();
        PopulateFriendsTask mPopulateFriendsTask = new PopulateFriendsTask(friends);
        mPopulateFriendsTask.execute();
        try {
            mPopulateFriendsTask.get(4000, TimeUnit.MILLISECONDS); //wait til friends are gotten
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        /*Now iterate through list of friends and get their listings */
        for (User friend : friends) {
            List<Listing> currFriendListings = new ArrayList<>();
            PopulateProductsTask mListingsTask = new PopulateProductsTask(currFriendListings, notifier, arrayAdapter, caller, "hey");
            mListingsTask.execute(); //should update arrayAdapter automatically with fetch of each friend's listing data
        }
        return true;
    }
}