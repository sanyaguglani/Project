package com.example.android.sportit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.android.sportit.Fragments.ViewAllEventsFrag;
import com.example.android.sportit.Models.Event;

import java.util.ArrayList;

/**
 * Created by Sanya on 18/05/2017.
 */

public class ViewAllEvents extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ViewAllEventsFrag())
                .commit();
    }
}
