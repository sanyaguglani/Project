package com.example.android.sportit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.sportit.Models.Event;

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
        TextView eventName;
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
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventListName);
            convertView.setTag(viewHolder);
        }
        else {
            // View has already been created, fetch our ViewHolder
            viewHolder= (ViewHolder) convertView.getTag();
        }
        // Assign values to the TextViews using the Monster object
        viewHolder.eventName.setText(eventList.get(position).getEventName());
        return convertView;
    }



}
