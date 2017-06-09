package com.example.android.sportit.Models;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Random;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by Sanya on 1/05/2017.
 */

public class Event {

    public Event()
    {}

//    public Event(String eventName, String place, String date, String time){   // Dummy
//        this.eventName = eventName;
//        this.place = place;
//        this.date = date;
//        this.time = time;
//    }


    public Event(String eventName, String place, String dateTime, String createdBy, int playersRequired){
        this.eventName = eventName;
        this.place = place;
        this.dateTime = dateTime;
        this.createdBy = createdBy;
        this.playersRequired = playersRequired;
        this.playersAttending = 0;
        this.isCancelled = false;
    };


    private String eventName;

    private String place;

    private String dateTime;

    private String eventID;

    private String createdBy;

    private int playersRequired;

    private int playersAttending;

    private boolean isCancelled;


    //getter setter for all attributes?


    public String getEventName(){
        return eventName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDateTime() { return dateTime; }

    public String getPlace() {
        return place;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public int getPlayersRequired(){
        return playersRequired;
    }

    public int getPlayersAttending(){
        return playersAttending;
    }

    public boolean getIsCancelled(){ return isCancelled; }
}
