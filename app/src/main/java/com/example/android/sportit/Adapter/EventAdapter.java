package com.example.android.sportit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;

import java.util.ArrayList;

/**
 * Created by Sanya on 21/05/2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    private Context eventContext;
    private ArrayList<Event> eventList;


    public EventAdapter(Context con, ArrayList<Event> events){
        super(con, 0, events);
        eventContext = con;
        eventList = events;
    }

    public static class ViewHolder {
        TextView vEventName;
        TextView vEventPlace;
        TextView vEventDate;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
    // Check if the view has been created for the row. If not, inflate it
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) eventContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Reference list item layout
            convertView = inflater.inflate(R.layout.list_item, null);

            // Setup ViewHolder and attach to view
            viewHolder = new ViewHolder();
            viewHolder.vEventName = (TextView) convertView.findViewById(R.id.event_name);
            viewHolder.vEventPlace = (TextView) convertView.findViewById(R.id.event_place);
            viewHolder.vEventDate = (TextView) convertView.findViewById(R.id.event_date);
            convertView.setTag(viewHolder);
        }
        else {
            // View has already been created, fetch our ViewHolder
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Event currentEvent = getItem(position);

//        // Assign values to the TextViews using the Monster object
//        viewHolder.vEventName.setText(eventList.get(position).getEventName());
//        viewHolder.vEventPlace.setText(eventList.get(position).getPlace());
//        viewHolder.vEvenDate.setText(eventList.get(position).getDate().concat(" ").concat(eventList.get(position).getTime()));

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
        String dateTime = currentEvent.getDate() + ", " + currentEvent.getTime();
        viewHolder.vEventDate.setText(dateTime);

        return convertView;
    }



}
