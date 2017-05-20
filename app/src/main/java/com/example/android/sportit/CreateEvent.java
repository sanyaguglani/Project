package com.example.android.sportit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.sportit.Models.Event;

import java.util.Locale;

/**
 * Created by Sanya on 1/05/2017.
 */

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    // Initialise UI elements
    EditText editName;
    /* Spinner --> Edit Text    ?
    EditText editSport;     */
    EditText editDate;          // EditText or DatePicker?
    EditText editLoc;
    EditText editTime;
    EditText players;
    EditText info;
    Button createEvent;

    Event event = new Event();

    EditText dateEdit;
    Spinner spinner;
    Calendar myCalendar;

   Intent ievent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        //Initialise UI elements
        editName = (EditText) findViewById(R.id.editName);
        // Spinner Sport Name and DatePicker Date  ?
        editDate = (EditText) findViewById(R.id.editDate);
        editLoc = (EditText) findViewById(R.id.editLoc) ;
        editTime = (EditText) findViewById(R.id.editTime);
        players = (EditText) findViewById(R.id.editPlayerNo);
        info = (EditText) findViewById(R.id.editOther);
        createEvent = (Button) findViewById(R.id.createEventButton);


        spinner = (Spinner) findViewById(R.id.sports_spinner);
        myCalendar= Calendar.getInstance();
        dateEdit = (EditText) findViewById(R.id.editDate);

        ievent = new Intent(this,ConfirmEvent.class);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sports_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        createEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                event.setEventName(editName.getText().toString());
                // event.setEventSport();  ??
                // event.setEventDate(editDate.getText().toString());  DATE??
                event.setEventLocation(editLoc.getText().toString());
                event.setEventTime(editTime.getText().toString());
                event.setEventPlayers(Integer.parseInt(players.getText().toString()));
                event.setEventInfo(info.getText().toString());

                ievent.putExtra("event", event);

                startActivity(ievent);
            }
        });


    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    }
