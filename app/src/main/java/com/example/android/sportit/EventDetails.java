package com.example.android.sportit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.sportit.R;

/**
 * Created by Sanya on 19/05/2017.
 */

public class EventDetails extends AppCompatActivity {

    private EditText eventNameEditText;
    private EditText eventDateEditText;
    private EditText eventTimeEditText;
    private EditText eventPlaceEditText;
    private EditText playersRequiredEditText;
    private EditText playersAttendingEditText;
    private String previousActivity;
    private String eventID;
    private Button button;      //For Join/Withdraw/Save
    private Button share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_layout);

        eventNameEditText = (EditText) findViewById(R.id.edit_event_name);
        eventDateEditText = (EditText) findViewById(R.id.edit_event_date);
        eventPlaceEditText = (EditText) findViewById(R.id.edit_event_place);
        eventTimeEditText = (EditText) findViewById(R.id.edit_event_time);
        playersAttendingEditText = (EditText) findViewById(R.id.edit_players_attending);
        playersRequiredEditText = (EditText) findViewById(R.id.edit_players_required);
        playersAttendingEditText.setEnabled(false);


        button = (Button) findViewById(R.id.button1);
        share = (Button) findViewById(R.id.button2);


        Intent intent = getIntent();
        previousActivity = intent.getStringExtra("Caller Method");

        if (previousActivity.contentEquals("event add")){
            setTitle("Create Event");
            button.setText("Create Event");
            share.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.GONE);
            findViewById(R.id.label_playersAttending).setVisibility(View.GONE);
        }
        else if (previousActivity.contentEquals("event details")){          //Created Event Details View
            setTitle("Event Created");
            button.setVisibility(View.GONE);
            share.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.label_playersAttending).setVisibility(View.VISIBLE);
        }
        else if  (previousActivity.contentEquals("event details attending")){   //Attending Event Details View
            setTitle("Event Information");
            button.setText("Withdraw");
            share.setVisibility(View.VISIBLE);                                  //Slots Remaining to be added
            button.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.label_playersAttending).setVisibility(View.VISIBLE);
        }
        else if (previousActivity.contentEquals("view all events")){            //All events Details View
            setTitle("Event Information");
            button.setText("Join Event");
            button.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            playersAttendingEditText.setVisibility(View.VISIBLE);
            findViewById(R.id.label_playersAttending).setVisibility(View.VISIBLE);
        }

    }


    }
