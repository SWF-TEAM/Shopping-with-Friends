package am.te.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import am.te.myapplication.R;
import am.te.myapplication.model.Listing;
import am.te.myapplication.model.User;

/**
 * A custom list adapter to allow certain row elements to have the template
 * outlined in R/drawable/alerted_row.xml.
 *
 * @author Veronica LeBlanc
 * @version 1.0
 * @since 2015 April 8
 */
public class ListFriendsAdapter extends BaseAdapter {

    //private static int[] colors = new int[] { 0x99CC00, 0xFFFFFF };
    private final LayoutInflater inflater;
    private final List<User> friends;

    public ListFriendsAdapter(Activity context, List<User> friends) {
        super();
        this.friends = friends;
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        User friend = friends.get(position);
        return (long) friend.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // See http://stackoverflow.com/questions/19911272/
        // changing-the-background-color-of-a-listview-row-conditionally

        View v = convertView;

        ListingHolder holder = new ListingHolder();

        User friend = friends.get(position);

        if (v == null) {
            v = inflater.inflate(R.layout.card_contents_friends, null);
        }

        holder.nameView = (TextView) v.findViewById(R.id.friend_name);
        holder.emailView = (TextView) v.findViewById(R.id.friend_email);

        v.setTag(holder);

        holder.nameView.setText(friend.getName());
        //holder.nameView.setText(friend.getUsername());
        holder.emailView.setText(friend.getEmail());

        return v;
    }

    private class ListingHolder {
        public TextView nameView;
        public TextView emailView;
    }

}
