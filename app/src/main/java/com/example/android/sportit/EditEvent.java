package com.example.android.sportit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.sportit.Models.Event;

/**
 * Created by Sanya on 5/05/2017.
 */

public class EditEvent extends AppCompatActivity {

    // Initialise UI elements
    EditText editName;
    EditText editSport;
    EditText editDate;          // EditText or DatePicker?
    EditText editLoc;
    EditText editTime;
    EditText players;
    EditText info;
    Button save;
    Button cancel;
    Button delete;


    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);
        editName = (EditText) findViewById(R.id.editName);
        editSport = (EditText) findViewById(R.id.editSport);
        editDate = (EditText) findViewById(R.id.editDate);
        editLoc = (EditText) findViewById(R.id.editLoc);
        editTime = (EditText) findViewById(R.id.editTime);
        players = (EditText) findViewById(R.id.editPlayerNo);
        info = (EditText) findViewById(R.id.editOther);
        save = (Button) findViewById(R.id.saveButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        delete = (Button) findViewById(R.id.deleteButton);

        event = getIntent().getExtras().getParcelable("event");

        editName.setText(event.getEventName());
        editSport.setText(event.getEventSport());
        // editDate.setText(event.setEventDate());
        editLoc.setText(event.getEventLocation());
        editTime.setText(event.getEventTime());
        players.setText(event.getEventPlayers());
        info.setText(event.getEventInfo());


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // code for updating event changes
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // code for deleting event
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // code for canceling the changes
            }
        });

    }
}
