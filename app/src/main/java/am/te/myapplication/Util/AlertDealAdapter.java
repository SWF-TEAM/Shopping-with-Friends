package am.te.myapplication.Util;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import am.te.myapplication.Model.Deal;
import am.te.myapplication.R;

/**
 * A custom list adapter to allow certain row elements to have the template
 * outlined in R/drawable/alerted_row.xml.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 23
 */
public class AlertDealAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Deal> items;

    public AlertDealAdapter(Activity context, List<Deal> items) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
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
        Deal item = items.get(position);
        return (long) item.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // See http://stackoverflow.com/questions/19911272/
        // changing-the-background-color-of-a-listview-row-conditionally

        View v = convertView;

        ListingHolder holder = new ListingHolder();

        Deal item = items.get(position);

        if (v == null) {
            v = inflater.inflate(R.layout.card_contents, null);
        }

        holder.nameView = (TextView) v.findViewById(R.id.name);
        holder.priceView = (TextView) v.findViewById(R.id.price);

        v.setTag(holder);

        //If this item contains new, unseen deals, color the row.
        //if (item.hasThresholdDeal() && !(item.hasBeenSeen())) {
        //    v.setBackgroundResource(R.drawable.alerted_row);
        //} else {
        //    v.setBackgroundResource(R.drawable.normal_row);
        //}


        holder.nameView.setText(item.getName());
        holder.priceView.setText(String.valueOf(item.getDesiredPrice()));

        return v;
    }

    private class ListingHolder {
        public TextView nameView;
        public TextView priceView;
    }

}
