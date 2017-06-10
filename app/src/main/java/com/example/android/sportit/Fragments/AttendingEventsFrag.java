package com.example.android.sportit.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
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
public class AttendingEventsFrag extends Fragment {

    private ListView attendingList;
    private ArrayList<Event> event;
    private ArrayList<String> eventId;
    private EventAdapter eventAdapter;
    private FloatingActionButton fab;
    private TextView empty;
    private View rootView;
    private View loadingIndicator;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference eventsReference;
    private FirebaseAuth firebaseAuth;
    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListener2;

    public AttendingEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_layout, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        empty = (TextView) rootView.findViewById(R.id.empty_view);

        firebaseDatabase = FirebaseDatabase.getInstance();  //Instantiate Firebase Instance
        firebaseAuth = FirebaseAuth.getInstance();
        event = new  ArrayList<Event>();

        eventAdapter = new EventAdapter(getActivity(), event);

        loadingIndicator = rootView.findViewById(R.id.loading_indicator);

        attendingList = (ListView) rootView.findViewById(R.id.list);
        attendingList.setEmptyView(empty);
        attendingList.setAdapter(eventAdapter);

        databaseReference = firebaseDatabase.getReference().child("users").
                child(firebaseAuth.getCurrentUser().getUid()).child("eventsAttending");

        eventsReference = firebaseDatabase.getReference().child("events");


        valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventId.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Log.v("event id", "ids from users : "+postSnapshot.getKey());
                    eventId.add(postSnapshot.getKey());
                }

                eventsReference.addValueEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventAdapter.clear();
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        eventsReference.child(postSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.v("inner value", "inner value : " + dataSnapshot.getKey());
                                Event e = dataSnapshot.getValue(Event.class);
                                e.setEventID(dataSnapshot.getKey());
                                event.add(e);
                                eventAdapter.notifyDataSetChanged();
                                loadingIndicator.setVisibility(View.GONE);
                                empty.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                else{
                    loadingIndicator.setVisibility(View.GONE);
                    empty.setText("No Events joined");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        databaseReference.addValueEventListener(valueEventListener);
        attendingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event e = event.get(position);
                Intent intent = new Intent(getContext(), EventDetails.class);
                intent.putExtra("EventID",e.getEventID());
                intent.putExtra("Caller Method","event details attending");
                startActivity(intent);  //Details for the selected event
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
        inflater.inflate(R.menu.search_menu,menu);      //Search menu options for the list
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
        eventsReference.removeEventListener(valueEventListener2);

    }


}





