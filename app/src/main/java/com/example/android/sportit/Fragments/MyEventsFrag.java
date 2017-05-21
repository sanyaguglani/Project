package com.example.android.sportit.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.sportit.Adapter.EventAdapter;
import com.example.android.sportit.EventDetails;
import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;


    public MyEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_layout, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();


        fab= (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.show();

        event = new ArrayList<Event>();

//        event.add(0, new Event("Football Match", "Monash Caulfield Ground", "20-May-2017", "5:00PM"));
//        event.add(1, new Event("Badminton Match", "Oakleigh Ground", "10 May 2017", "10:30AM"));

        eventAdapter = new EventAdapter(getActivity(), event);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(eventAdapter);

        databaseReference = firebaseDatabase.getReference().child("events");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventAdapter.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.v("In Loop", "In Loop");
                    Event e = postSnapshot.getValue(Event.class);
                    e.setEventID(postSnapshot.getKey());
                    event.add(e);
                    eventAdapter.notifyDataSetChanged();
                    //loadingIndicator.setVisibility(View.GONE);
                  //  emptyStateTextView.setVisibility(View.GONE);
                }
                if (eventAdapter.isEmpty()){
                    Log.v("List Empty", "List Empty found");
//                    loadingIndicator.setVisibility(View.GONE);
//                    emptyStateTextView.setText("No Events Available. Create an Event");
//                    emptyStateTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //System.out.println("The read failed: " + databaseError.getCode());
                Log.v("In onCancelled", "In onCancelled");
            }
        };

        databaseReference.orderByChild("createdBy").equalTo("RMBIva5WdIZyE7zcTbcQ8SPAGlZ2").addValueEventListener(valueEventListener);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventAdapter.clear();
        databaseReference.removeEventListener(valueEventListener);
    }


}
