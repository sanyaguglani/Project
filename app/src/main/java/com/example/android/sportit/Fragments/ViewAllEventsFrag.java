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
public class ViewAllEventsFrag extends Fragment {

    private FloatingActionButton fab;
    private ArrayList<Event> event;
    private ListView listView;
    private EventAdapter eventAdapter;
    private View rootView;

    public ViewAllEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       rootView = inflater.inflate(R.layout.list_layout, container, false);


        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        event = new ArrayList<Event>();

        event.add(0,new Event("Football Match","Monash Caulfield Ground","20-May-2017", "5:00PM"));
        event.add(1,new Event("Badminton Match", "Oakleigh Ground", "10 May 2017", "10:30AM"));
        event.add(2,new Event("Cricket Match","Monash Clayton","20-Jul-2017","6:00PM"));
        event.add(3,new Event("Rugby Match", "RMIT Ground", "18 May 2017", "09:30AM"));

        eventAdapter = new EventAdapter(getActivity(), event);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(eventAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetails.class);
                intent.putExtra("Caller Method","view all events");
                startActivity(intent);
            }
        });

        return rootView;
    }

}
