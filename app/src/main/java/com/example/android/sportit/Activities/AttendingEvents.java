package com.example.android.sportit.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.android.sportit.Fragments.AttendingEventsFrag;
import com.example.android.sportit.R;


/**
 * Created by Sanya on 21/05/2017.
 */

public class AttendingEvents extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AttendingEventsFrag())
                .commit();

    }
}
