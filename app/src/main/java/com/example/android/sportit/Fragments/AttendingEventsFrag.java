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
import com.example.android.sportit.MainActivity;
import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendingEventsFrag extends Fragment {

    private ListView attendingList;
    private ArrayList<Event> eventArrayList;  //String to Event
    private EventAdapter eventAdapter;
    private FloatingActionButton fab;
    private View rootView;


    public AttendingEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_layout, container, false);

         fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        eventArrayList = new  ArrayList<Event>(); //String to Event
        eventArrayList.add(0, new Event("Football Match","Monash Caulfield Ground","20-May-2017", "5:00pm"));
        eventArrayList.add(1, new Event("Cricket Match","Monash Clayton","20-Jul-2017","6:00pm"));

        eventAdapter = new EventAdapter(getActivity(), eventArrayList);

        attendingList = (ListView) rootView.findViewById(R.id.list);
        attendingList.setAdapter(eventAdapter);

        attendingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), EventDetails.class);
                intent.putExtra("Caller Method","event details attending");
                startActivity(intent);
            }
        });



        return rootView;
    }

}
