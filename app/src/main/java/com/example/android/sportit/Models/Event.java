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

    private String eventName;

    private String place;

    private String dateTime;

    private String eventID;

    private String eventType;

    private String createdBy;

    private int playersRequired;

    private int playersAttending;

    private boolean isCancelled;

    private int imageResourceId;

    public Event()
    {
        //default constructor
    }


    public Event(String eventName, String eventType, String place, String dateTime, String createdBy, int playersRequired, int imageResourceId){
        this.eventName = eventName;
        this.eventType = eventType;
        this.place = place;
        this.dateTime = dateTime;
        this.createdBy = createdBy;
        this.playersRequired = playersRequired;
        this.imageResourceId = imageResourceId;
        this.playersAttending = 0;
        this.isCancelled = false;
    };

    //Validate user input
    public String validate(String eventName, String eventType, String eventDate, String eventPlace, String eventTime, int playersRequired)
    {

        String result;

        if((eventName != null) && (eventType != null) && (eventDate !=null) && (eventPlace !=null) && (eventTime !=null) ){
            result = "Success: ";
        }
        else
        {
            result= "Error: \n";
            if (eventName == null)
                result= result+ "Please enter a valid event name \n";
            if (eventType == null)
                result= result+ "Please select a valid event category \n";
            if (eventDate == null)
                result= result+ "Please select a valid event date \n";
            if (eventPlace == null)
                result= result+ "Please select a valid event place \n";
            if (eventTime == null)
                result= result+ "Please select a valid event time \n";
            if (playersRequired <= 0)
                result= result+ "Please enter a valid number of players required \n";
        }
        return  result;
    }

    //getter and setter for all attributes
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setPlayersRequired(int playersRequired) {
        this.playersRequired = playersRequired;
    }

    public void setPlayersAttending(int playersAttending) {
        this.playersAttending = playersAttending;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventType (String eventType) {
        this.eventType = eventType;
    }

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

    public String getEventType() { return eventType; }

    public int getPlayersRequired(){
        return playersRequired;
    }

    public int getPlayersAttending(){
        return playersAttending;
    }

    public boolean getIsCancelled(){ return isCancelled; }

    public int getImageResourceId() {
        return imageResourceId;
    }



}
