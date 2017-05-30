package com.example.android.sportit.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.sportit.Models.Event;
import com.example.android.sportit.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Sanya on 19/05/2017.
 */

public class EventDetails extends AppCompatActivity {

    // User input Controls
    private EditText eventNameEditText;
    private EditText eventDateEditText;
    private EditText eventTimeEditText;
    private EditText playersRequiredEditText;
    private EditText playersAttendingEditText;
    private Button button1;      //For Join/Withdraw/Save Button
    private Button button2;      // For share button
    private Button placePickerButton;   // To select location

    private String previousActivity;        // For picking previous activity name from intent

    private String eventID;
    private Event e;

    private String location;
    private Double lat;
    private Double lon;
    private String[] loc;
    int PLACE_PICKER_REQUEST = 1;

    private ValueEventListener valueEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_layout);      //Initialise the UI layout file

        firebaseDatabase = FirebaseDatabase.getInstance();     //Instantiate the Firebase reference
        databaseReference = firebaseDatabase.getReference();

        //Instantiate UI elements
        eventNameEditText = (EditText) findViewById(R.id.edit_event_name);
        eventDateEditText = (EditText) findViewById(R.id.edit_event_date);
        eventTimeEditText = (EditText) findViewById(R.id.edit_event_time);
        playersAttendingEditText = (EditText) findViewById(R.id.edit_players_attending);
        playersRequiredEditText = (EditText) findViewById(R.id.edit_players_required);
        placePickerButton = (Button) findViewById(R.id.place_picker);
        playersAttendingEditText.setEnabled(false);

        button1 = (Button) findViewById(R.id.button1); //UI Button for Join/Withdraw/Save control
        button2 = (Button) findViewById(R.id.button2);  //Share Button

        Intent intent = getIntent();        //Intent from calling activities
        previousActivity = intent.getStringExtra("Caller Method");  //extracting calling activity name from intent
        eventID = intent.getStringExtra("EventID");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    e = dataSnapshot.getValue(Event.class);     //?? Calling Event attribute methods for each event object
                    eventNameEditText.setText(e.getEventName());      //Setting values to output screen
                    eventDateEditText.setText(e.getDate());
                    eventTimeEditText.setText(e.getTime());
                    playersRequiredEditText.setText("" + e.getPlayersRequired());   // ?? int to string?
                    location = e.getPlace();    //Get the location
                    loc = location.split("[|]");    // Split location name, latitude and longitude
                    placePickerButton.setText(loc[0]);
                    lat = Double.parseDouble(loc[1]);
                    lon = Double.parseDouble(loc[2]);
                    playersAttendingEditText.setText("" + e.getPlayersAttending()); // ?? int to string?
                    if (e.getPlayersRequired() == e.getPlayersAttending() &&
                            previousActivity.contentEquals("view all events")) {
                        button1.setVisibility(View.GONE);  //No option to Join the event if number of players = number of players attending
                    }
                    if (e.getIsCancelled() &&
                            previousActivity.contentEquals("event details attending")){
                        button2.setVisibility(View.GONE); //Share disabled if the joined event is cancelled
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Error","Database Error");
            }
        };


        if (eventID != null){       //Displaying event details if one event id exists
            databaseReference.child("events").child(eventID).addValueEventListener(valueEventListener);
        }


        if (previousActivity.contentEquals("event add")) {        //Adding new event on myEvents page
            setTitle("Create Event");
            button1.setText("Create Event");
            button2.setVisibility(View.GONE);       // Disable button to share event
            button1.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.GONE);     //Disable players attending field
            findViewById(R.id.label_playersAttending).setVisibility(View.GONE);
            enableEditing();
            invalidateOptionsMenu();
        }


        else if (previousActivity.contentEquals("event details")){    //Event Details View for User Created Events
            setTitle("Event Created");
            button1.setVisibility(View.GONE);       //Disable button to withdraw/join/save
            button2.setVisibility(View.VISIBLE);    //Share Button
            playersAttendingEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.label_playersAttending).setVisibility(View.VISIBLE);
            disableEditing();
            invalidateOptionsMenu();
        }


        else if  (previousActivity.contentEquals("event details attending")){   //Attending Event Details View
            setTitle("Event Information");
            button1.setText("Withdraw");
            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.label_playersAttending).setVisibility(View.VISIBLE);
            disableEditing();
            invalidateOptionsMenu();
        }

        else if (previousActivity.contentEquals("view all events")){  //Event Details View from list of all events
            setTitle("Event Information");
            button1.setText("Join Event");
            button2.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.label_playersAttending).setVisibility(View.VISIBLE);
            disableEditing();       //Validate edit text enabled/disabled
            invalidateOptionsMenu();
        }

        else if (previousActivity.contentEquals("edit event")){    //Edit Event Details
            setTitle("Edit Event");
            button1.setText("Save Changes");
            button2.setVisibility(View.GONE);
            button1.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.GONE);
            findViewById(R.id.label_playersAttending).setVisibility(View.GONE);
            enableEditing();
            invalidateOptionsMenu();    //update menu options for every intent
        }

        button1.setOnClickListener(new View.OnClickListener() {         //OnClick for  Join/Withdraw/Save button
            @Override
            public void onClick(View v) {     //On Click action for Join/Withdraw/Save Button
                if ((previousActivity.contentEquals("view all events"))){   // Join event button
                    if (e.getPlayersRequired() > e.getPlayersAttending()) {    //Verify the slots remaining
                        joinEvent(e.getPlayersAttending());
                    }
                }
                else if (previousActivity.contentEquals("event add") ||
                        previousActivity.contentEquals("edit event")){      //Save event details button
                    saveEventData();
                }
                else if (previousActivity.contentEquals("event details attending")){    //Withdraw event
                    leaveEvent(e.getPlayersAttending());
                }
                e = null;    //****
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {         //Share button
            @Override
            public void onClick(View v) {   //Share Event Button
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");  //Fro email apps
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");     //****
                sendIntent.setType("text/plain");
                //startActivity(sendIntent);
                startActivity(Intent.createChooser(sendIntent,"Select App"));
            }
        });

        placePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //View/Pick location for the event
                if (previousActivity.contentEquals("event add") ||
                        previousActivity.contentEquals("edit event")) {      // to pick a place from maps
                    startPlacePickerActivity();
                }
                else{                           //to view location on map
                    startMapsActivity();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Called when control returns from maps activity to app
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK) {      //location selected by user
                Place place = PlacePicker.getPlace(data, this);     //***
                String placeName = place.getName().toString();
                LatLng latLng = place.getLatLng();
                Double l1 = latLng.latitude;
                Double l2 = latLng.longitude;
                location = placeName + "|" + l1.toString() + "|" + l2.toString();
                placePickerButton.setText(placeName);   //location name
            }
        }
    }

    private void startPlacePickerActivity(){        //Pick a location for event
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e1) {
            e1.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e1) {
            e1.printStackTrace();
        }
    }

    private void startMapsActivity(){           // View location on map
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(%s)", lat,lon,lat,lon,loc[0]);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {         //?? Called once
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {        //??? Buttons to show or delete menu options --> invalidate optionMenu

        super.onPrepareOptionsMenu(menu);
        if (previousActivity.contentEquals("event add")) {
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_edit).setVisible(false);
        }
        else if (previousActivity.contentEquals("event details")){
            menu.findItem(R.id.action_save).setVisible(false);
        }
        else if (previousActivity.contentEquals("edit event")){
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_edit).setVisible(false);
        }
        else{
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_edit).setVisible(false);
            menu.findItem(R.id.action_save).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {       //??Functionality for each menu button
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                saveEventData();
                finish();
                return true;
            case R.id.action_edit:
                startEditEvent();
                return true;
            case R.id.action_delete:
                deleteEvent();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEventData(){
        //String userID = firebaseAuth.getCurrentUser().getUid();
        String userID = "6ENX5lnih5eGGqvX6rBhhElUkbs2";
        if (!userID.isEmpty()) {
            String eventName = eventNameEditText.getText().toString().trim();
            String eventDate = eventDateEditText.getText().toString().trim();
            String eventPlace = location;
            String eventTime = eventTimeEditText.getText().toString().trim();
            int playersRequired = Integer.parseInt(playersRequiredEditText.getText().toString().trim());

            if ((previousActivity.contentEquals("event add"))) {
                Event event = new Event(eventName, eventPlace, eventDate, eventTime, userID, playersRequired);
                databaseReference.child("events").push().setValue(event);
            }
            else if ((previousActivity.contentEquals("edit event"))){
                Log.v("in edit","in edit " + location);

                Map<String,Object> update = new HashMap<>();
                update.put("events/"+eventID+"/date",eventDate);
                update.put("events/"+eventID+"/eventName",eventName);
                update.put("events/"+eventID+"/place",location);
                update.put("events/"+eventID+"/time",eventTime);
                databaseReference.updateChildren(update);       //DB update

            }
        }
        else{
            Log.v("Event entry", "user id not present");
        }
    }

    private void joinEvent(int players){

        Map<String,Object> update = new HashMap<>();
        update.put("events/"+eventID+"/usersAttending/"+"RMBIva5WdIZyE7zcTbcQ8SPAGlZ2",true);
        update.put("events/"+eventID+"/playersAttending",players+1);
        update.put("users/"+"RMBIva5WdIZyE7zcTbcQ8SPAGlZ2"+"/eventsAttending/"+eventID,true);

        databaseReference.updateChildren(update);   //DB update
    }

    private void leaveEvent(int players){
        Map<String,Object> update = new HashMap<>();
        update.put("events/"+eventID+"/playersAttending",players-1);
        databaseReference.updateChildren(update);
        databaseReference.child("events").child(eventID).child("usersAttending").child("RMBIva5WdIZyE7zcTbcQ8SPAGlZ2").removeValue();
        databaseReference.child("users").child("RMBIva5WdIZyE7zcTbcQ8SPAGlZ2").child("eventsAttending").child(eventID).removeValue();
    }

    private void deleteEvent(){
        Map<String,Object> update = new HashMap<>();
        update.put("events/"+eventID+"/isCancelled",true);
        databaseReference.updateChildren(update);
        Log.v("players ", "players attending value " + e.getPlayersAttending());
        if (e.getPlayersAttending() == 0){      //Only delete if number of user joined = 0
            databaseReference.child("events").child(eventID).removeValue();
        }
    }

    private void startEditEvent(){
        Intent intent = new Intent(this, EventDetails.class);
        Log.v("event id", "event id : "+ eventID);
        intent.putExtra("EventID", eventID);
        intent.putExtra("Caller Method","edit event");
        startActivity(intent);
    }


    private void enableEditing(){
        eventNameEditText.setEnabled(true);
        eventDateEditText.setEnabled(true);
        eventTimeEditText.setEnabled(true);
        playersRequiredEditText.setEnabled(true);
    }

    private void disableEditing(){
        eventNameEditText.setEnabled(false);
        eventDateEditText.setEnabled(false);
        eventTimeEditText.setEnabled(false);
        playersRequiredEditText.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }
}




