package com.example.android.sportit.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.sportit.Adapter.EventAdapter;
import com.example.android.sportit.Activities.EventDetails;
import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;
import com.google.firebase.auth.FirebaseAuth;
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
    private TextView empty;
    private View loadingIndicator;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;



    public MyEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_layout, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();  //Instantiate firebase database
        firebaseAuth = FirebaseAuth.getInstance();

        event = new ArrayList<Event>();
        eventAdapter = new EventAdapter(getActivity(), event);

        empty = (TextView) rootView.findViewById(R.id.empty_view);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(eventAdapter);

        fab= (FloatingActionButton) rootView.findViewById(R.id.fab);

       databaseReference = firebaseDatabase.getReference().child("events");

        valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {      //IF created events exists
                eventAdapter.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event e = postSnapshot.getValue(Event.class);
                    if (!e.getIsCancelled()) {
                        e.setEventID(postSnapshot.getKey());
                        event.add(e);
                        eventAdapter.notifyDataSetChanged();
                        loadingIndicator.setVisibility(View.GONE);
                        empty.setVisibility(View.GONE);
                    }
                }
                if (eventAdapter.isEmpty()){        //No events in created event list
                    loadingIndicator.setVisibility(View.GONE);
                    empty.setText("No Events Available. Create an Event");
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };

        databaseReference.orderByChild("createdBy").equalTo(firebaseAuth.getCurrentUser().getUid()).
                addValueEventListener(valueEventListener);

        fab.setOnClickListener(new View.OnClickListener() {         //Add new Event on fab button
            @Override
            public void onClick(View v) { //For creating new events
                Intent intent = new Intent(getContext(),EventDetails.class);
                intent.putExtra("Caller Method","event add");
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {     //View Event Details
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event e = event.get(position);
                Intent intent = new Intent(getContext(), EventDetails.class);
                intent.putExtra("EventID",e.getEventID());
                intent.putExtra("Caller Method","event details");
                startActivity(intent);  // View Event Details for selected event
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                eventAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventAdapter.clear();
        databaseReference.removeEventListener(valueEventListener);
    }


}
