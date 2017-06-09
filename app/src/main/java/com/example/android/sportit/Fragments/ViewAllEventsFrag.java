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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.sportit.Adapter.EventAdapter;
import com.example.android.sportit.Activities.EventDetails;
import android.support.v7.widget.SearchView;
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
public class ViewAllEventsFrag extends Fragment {

    private FloatingActionButton fab;
    private ArrayList<Event> event;
    private ListView listView;
    private EventAdapter eventAdapter;
    private View rootView;
    private TextView empty;
    private View loadingIndicator;
    private ArrayList<String> eventId;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceEvents;
    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListener2;
    private DatabaseReference databaseReferenceUsers;

    public ViewAllEventsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_layout, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferenceEvents  = firebaseDatabase.getReference().child("events");
        databaseReferenceUsers = firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("eventsAttending");

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        empty = (TextView) rootView.findViewById(R.id.empty_view) ;

        event = new ArrayList<Event>();
        eventId = new ArrayList<String>();


//        event.add(0,new Event("Football Match","Monash Caulfield Ground","20-May-2017", "5:00PM"));
//        event.add(1,new Event("Badminton Match", "Oakleigh Ground", "10 May 2017", "10:30AM"));
//        event.add(2,new Event("Cricket Match","Monash Clayton","20-Jul-2017","6:00PM"));
//        event.add(3,new Event("Rugby Match", "RMIT Ground", "18 May 2017", "09:30AM"));

        eventAdapter = new EventAdapter(getActivity(), event);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);

        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(eventAdapter);


        valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventId.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Log.v("event id", "ids from users : "+postSnapshot.getKey());
                    eventId.add(postSnapshot.getKey());
                }

                databaseReferenceEvents.addValueEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventAdapter.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event e = postSnapshot.getValue(Event.class);
                    if (!e.getCreatedBy().contentEquals(firebaseAuth.getCurrentUser().getUid()) &&
                            !eventId.contains(postSnapshot.getKey()) && !e.getIsCancelled()) {
                        e.setEventID(postSnapshot.getKey());
                        event.add(e);
                        eventAdapter.notifyDataSetChanged();
                        loadingIndicator.setVisibility(View.GONE);
                        empty.setVisibility(View.GONE);
                    }
                }
                if (eventAdapter.isEmpty()){
                    loadingIndicator.setVisibility(View.GONE);
                    empty.setText("No Events Available");
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReferenceUsers.addValueEventListener(valueEventListener2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event e = event.get(position);
                Intent intent = new Intent(getContext(), EventDetails.class);
                intent.putExtra("EventID",e.getEventID());
                intent.putExtra("Caller Method","view all events");
                startActivity(intent);
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
        //super.onCreateOptionsMenu(menu, inflater);
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
        databaseReferenceEvents.removeEventListener(valueEventListener);
        databaseReferenceEvents.removeEventListener(valueEventListener2);
    }


}
