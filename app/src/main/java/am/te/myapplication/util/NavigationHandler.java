package am.te.myapplication.util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import am.te.myapplication.R;
import am.te.myapplication.presenter.AddListing;
import am.te.myapplication.presenter.FriendList;
import am.te.myapplication.presenter.FriendListings;
import am.te.myapplication.presenter.Homepage;
import am.te.myapplication.presenter.SearchFriends;

/**
 * This class is to help navigation throughout the application.
 * It parses menu items, and launches activities.
 *
 * @author Veronica LeBlanc
 * @version 1.0
 * @since 2015 April 9
 */
public class NavigationHandler {
    Activity activity;
    BaseAdapter adapter;

    public NavigationHandler(Activity activity, BaseAdapter adapter){
        this.activity = activity;
        this.adapter = adapter;
    }

    public Boolean openMenuItem(MenuItem item) {
        //Handle presses on the action bar
        //Opens the friends menu if the user presses the 'friends' button
        //see http://developer.android.com/guide/topics/ui/actionbar.html#Adding
        switch (item.getItemId()) {
            case R.id.friend_menu:
                launchActivity(FriendList.class,adapter);
                return true;
            case R.id.add_listing:
                launchActivity(AddListing.class,adapter);
                return true;
            case R.id.search_friend:
                launchActivity(SearchFriends.class,adapter);
                return true;
            case R.id.friends_listings:
                launchActivity(FriendListings.class,adapter);
                return true;
            case R.id.homepage:
                launchActivity(Homepage.class,adapter);
                return true;
            default:
                return false;
        }
    }

    public void launchActivity(Class activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }

    public void launchActivity(Class activityClass, BaseAdapter adapter) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivityForResult(intent, 1);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
