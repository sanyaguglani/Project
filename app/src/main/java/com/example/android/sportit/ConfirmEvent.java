package com.example.android.sportit;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.sportit.Models.Event;

import static android.R.id.edit;

/**
 * Created by Sanya on 3/05/2017.
 */

public class ConfirmEvent extends AppCompatActivity {

    // Initialise UI elements
    EditText editName;
    EditText editSport;
    EditText editDate;          // EditText or DatePicker?
    EditText editLoc;
    EditText editTime;
    EditText players;
    EditText info;
    Button share;
    Button edit;
    Button delete;

    Event created_event = new Event("Football Match","Monash Caulfield Ground","20-May-2017", "5:00pm");

    Intent ievent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_event);

        editName = (EditText) findViewById(R.id.editName);
        editSport = (EditText) findViewById(R.id.editSport);
        editDate = (EditText) findViewById(R.id.editDate);
        editLoc = (EditText) findViewById(R.id.editLoc);
        editTime = (EditText) findViewById(R.id.editName);
        players = (EditText) findViewById(R.id.editPlayerNo);
        info = (EditText) findViewById(R.id.editOther);
        share = (Button) findViewById(R.id.shareButton);
        edit = (Button) findViewById(R.id.editButton);
        delete = (Button) findViewById(R.id.deleteButton);

        ievent = new Intent(this, EditEvent.class);

        //ievent = getIntent();
        created_event = getIntent().getExtras().getParcelable("event");

        editName.setText(created_event.getEventName());
        editSport.setText(created_event.getEventSport());
        editDate.setText(created_event.getEventDate().toString());
        editLoc.setText(created_event.getEventLocation());
        editTime.setText(created_event.getEventTime());
        players.setText(created_event.getEventPlayers());
        info.setText(created_event.getEventInfo());

        share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // code for sharing applications
            }
        });

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // for editing event
                ievent.putExtra("event", created_event);
                //startActvity

                setContentView(R.layout.edit_event);
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // code for deleting event
            }
        });
    }
    }
