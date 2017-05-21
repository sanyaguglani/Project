package com.example.android.sportit.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.sportit.Adapter.EventAdapter;
import com.example.android.sportit.EventDetails;
import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventsFrag extends Fragment {

    private ArrayList<Event> event;
    private EventAdapter eventAdapter;
    private ListView listView;
    private FloatingActionButton fab;
    private View rootView;

    public MyEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_layout, container, false);

        fab= (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.show();

        event = new ArrayList<Event>();

        event.add(0, new Event("Football Match", "Monash Caulfield Ground", "20-May-2017", "5:00PM"));
        event.add(1, new Event("Badminton Match", "Oakleigh Ground", "10 May 2017", "10:30AM"));

        eventAdapter = new EventAdapter(getActivity(), event);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(eventAdapter);

        fab.setOnClickListener(new View.OnClickListener() {         //Add new Event on fab button
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EventDetails.class);
                intent.putExtra("Caller Method","event add");
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {     //View Event Details
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), EventDetails.class);
                intent.putExtra("Caller Method","event details");
                startActivity(intent);
            }
        });

        return rootView;
    }

}
