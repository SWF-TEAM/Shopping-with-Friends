package am.te.myapplication.Service;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.Model.User;
import am.te.myapplication.Util.AlertListingAdapter;


/**
 * Created by Mike Adkison on 3/23/15.
 */
public class PopulateFriendsListingsTask extends UserTask {

    AlertListingAdapter arrayAdapter; //arrayadapter to update
    List<Listing> friendsListings; //will be updated
    Activity caller;
    public PopulateFriendsListingsTask(AlertListingAdapter arrayAdapter, List<Listing> friendsListings, Activity caller) {
        this.arrayAdapter = arrayAdapter;
        this.friendsListings = friendsListings;
        this.caller = caller;
    }
    /**
     * populates deals ArrayList with info from database
     *
     * @param params
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

        /*now iterate thru list of friends and get their listings*/
        List<Listing> allListings = new ArrayList<>();
        for (User friend: friends) {
            String friendID = friend.getId();
            List<Listing> currFriendListings = new ArrayList<>();
            PopulateProductsTask mListingsTask = new PopulateProductsTask(currFriendListings, arrayAdapter, caller, "hey");
            mListingsTask.execute(); //should update arrayAdapter automatically with fetch of each friend's listing data
        }
        return true;
    }


   /* @Override
    protected void onCancelled() {
        mPopulateDealsTask = null;
    }*/
}