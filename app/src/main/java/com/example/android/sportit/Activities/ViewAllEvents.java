package com.example.android.sportit.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.android.sportit.Fragments.ViewAllEventsFrag;
import com.example.android.sportit.R;
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
