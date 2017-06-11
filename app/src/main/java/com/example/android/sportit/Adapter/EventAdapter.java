package com.example.android.sportit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;
import android.widget.Filter;

import java.util.ArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.R.attr.filter;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Sanya on 21/05/2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> filteredData;
    private ArrayList<Event> originalData;

    public static class ViewHolder {
        TextView vEventName;
        TextView vEventPlace;
        TextView vEventDate;
        private ImageView vImageView;
    }


    public EventAdapter(Context con, ArrayList<Event> events){
        super(con, 0, events);
        filteredData = events;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;

        // Check if the view has been created for the row. If not, inflate it
        if (convertView == null)
        {

            // Reference list item layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            // Setup ViewHolder and attach to view
            viewHolder = new ViewHolder();
            viewHolder.vEventName = (TextView) convertView.findViewById(R.id.event_name);
            viewHolder.vEventPlace = (TextView) convertView.findViewById(R.id.event_place);
            viewHolder.vEventDate = (TextView) convertView.findViewById(R.id.event_date);
            viewHolder.vImageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }
        else {
            // View has already been created, fetch our ViewHolder
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Event currentEvent = getItem(position);

        if (currentEvent.getIsCancelled()) {
            viewHolder.vEventName.setText(currentEvent.getEventName() + " - CANCELLED");
        }else{
            viewHolder.vEventName.setText(currentEvent.getEventName());
        }
        if (currentEvent.getPlace().indexOf('|') != -1) {
            viewHolder.vEventPlace.setText(currentEvent.getPlace().substring(0, currentEvent.getPlace().indexOf('|')));
        }else{
            viewHolder.vEventPlace.setText(currentEvent.getPlace());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date local = null;
        try {
            Date convertedDate = sdf.parse(currentEvent.getDateTime());
            String timeZoneID = Calendar.getInstance().getTimeZone().getID();
            local = new Date(convertedDate.getTime() + TimeZone.getTimeZone(timeZoneID).getOffset(convertedDate.getTime()));//local timezone date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("dd MMM yyyy, h:mm a");
        String dateTime = sdf.format(local);
        viewHolder.vEventDate.setText(dateTime);
        viewHolder.vImageView.setImageResource(currentEvent.getImageResourceId());

        return convertView;
    }

    //https://www.survivingwithandroid.com/2012/10/android-listview-custom-filter-and.html
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                final FilterResults results = new FilterResults();

                if (originalData == null) {
                    originalData = new ArrayList<>(filteredData);
                }

                if (prefix == null || prefix.length() == 0) {  //no search text
                    final ArrayList<Event> list = new ArrayList<>(originalData);

                    results.values = list;
                    results.count = list.size();
                } else {       //search text present
                    final String prefixString = prefix.toString().toLowerCase();

                    final ArrayList<Event> values = new ArrayList<>(originalData);

                    final ArrayList<Event> newValues = new ArrayList<>();

                    for (Event e : values){
                        //Filter the search on name and location of event
                        if (e.getEventName().toLowerCase().contains(prefixString) ||
                                e.getPlace().toLowerCase().contains(prefixString) ) {
                            newValues.add(e);
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }
//https://stackoverflow.com/questions/2718202/custom-filtering-in-android-using-arrayadapter

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<Event>) results.values;
                notifyDataSetChanged();
                clear();
                addAll(filteredData);
                notifyDataSetInvalidated();

            }
        };
    }




}
