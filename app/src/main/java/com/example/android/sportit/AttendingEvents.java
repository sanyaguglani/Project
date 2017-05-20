package com.example.android.sportit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.*;

import com.example.android.sportit.Models.Event;

import java.util.ArrayList;

import static com.example.android.sportit.R.id.listView;

/**
 * Created by Sanya on 21/05/2017.
 */

public class AttendingEvents extends AppCompatActivity  {

    private ListView attendingList;
    private ArrayList<Event> eventArrayList;  //String to Event
    private EventAdapter eventAdapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_attending_events);


        eventArrayList = new  ArrayList<Event>(); //String to Event
        eventArrayList.add(0, new Event());
        eventArrayList.add(1, new Event());

        eventAdapter = new EventAdapter(this, eventArrayList);

        attendingList = (ListView) findViewById(R.id.listView);
        attendingList.setAdapter(eventAdapter);
    }
}
