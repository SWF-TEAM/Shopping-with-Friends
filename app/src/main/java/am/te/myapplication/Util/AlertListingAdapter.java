package am.te.myapplication.Util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import am.te.myapplication.Model.Deal;
import am.te.myapplication.Model.Listing;
import am.te.myapplication.R;

/**
 * A custom list adapter to allow certain row elements to have the template
 * outlined in R/drawable/alerted_row.xml.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 23
 */
public class AlertListingAdapter extends BaseAdapter {

    private static int[] colors = new int[] { 0x99CC00, 0xFFFFFF };
    LayoutInflater inflater;
    List<Listing> items;
    int rowLayout;
    int alertLayout;
    int normalLayout;

    public AlertListingAdapter(Activity context, List<Listing> items,
                             int rowLayout, int alertLayout, int normalLayout) {
        super();
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.rowLayout = rowLayout;
        this.alertLayout = alertLayout;
        this.normalLayout = normalLayout;
    }

    public AlertListingAdapter(Activity context, List<Listing> items, int rowLayout) {
        this(context, items, rowLayout, R.drawable.alerted_row,
                                        R.drawable.normal_row);
    }

    public AlertListingAdapter(Activity context, List<Listing> items) {
        this(context, items, R.layout.card_contents, R.drawable.alerted_row,
                                                     R.drawable.normal_row);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        Listing item = items.get(position);
        return Long.valueOf(item.hashCode());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // See http://stackoverflow.com/questions/19911272/
        // changing-the-background-color-of-a-listview-row-conditionally

        View v = convertView;

        ListingHolder holder = new ListingHolder();

        Listing item = items.get(position);

        if (v == null) {
            v = inflater.inflate(R.layout.card_contents, null);
        }

        holder.nameView = (TextView) v.findViewById(R.id.name);
        holder.priceView = (TextView) v.findViewById(R.id.price);

        v.setTag(holder);

        //If this item contains new, unseen deals, color the row.
//        if (!(item.hasBeenSeen())) {
//            v.setBackgroundColor(colors[0]); //Green
//        } else {
//            v.setBackgroundColor(colors[1]); //White (default)
//        }


        holder.nameView.setText(item.getName());
        String price = item.formatPrice(item.getDesiredPrice());

        holder.priceView.setText(price);

        return v;
    }

    private class ListingHolder {
        public TextView nameView;
        public TextView priceView;
    }

}
