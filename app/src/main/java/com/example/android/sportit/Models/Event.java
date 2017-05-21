package com.example.android.sportit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Random;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by Sanya on 1/05/2017.
 */

public class Event implements Parcelable {

    //Event Class attributes
    private String eventName;
    private String eventSport;
    private String eventDate;
    private String eventLocation;
    private String eventTime;               // Event Time is string
    private int eventPlayers;
    private String eventInfo;
    private String createdBy;


    public Event(String eventName, String place, String date, String time){
        this.eventName = eventName;
        this.eventLocation = place;
        this.eventDate = date;
        this.eventTime = time;
    };


    public Event(String eventName, String eventSport, String eventDate, String eventLocation,
                 String eventTime, int eventPlayers, String eventInfo) //Parameterised Constructor
    {
        this.eventName = eventName;
        this.eventSport = eventSport;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventPlayers = eventPlayers;
        this.eventInfo = eventInfo;
    }

    protected Event(Parcel in)    //Constructor accepting Parcel
    {
        eventName = in.readString();
        eventSport = in.readString();
        // eventDate = in.readDate(); Date Read

        eventLocation = in.readString();
        eventTime = in.readString();
        eventPlayers = in.readInt();
        eventInfo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    //Writing class variables to a parcel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventSport);
        // dest.writeDate(eventDate);           Date Write
        dest.writeString(eventLocation);
        dest.writeString(eventTime);
        dest.writeInt(eventPlayers);
        dest.writeString(eventInfo);
    }

    //Creator Constant
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getEventName() {    //Accessor For Event Name
        return eventName;
    }

    public void setEventName(String eventName) {       //Mutator For Event Name
        this.eventName = eventName;
    }

    public String getEventSport() {             //Accessor For Sport Name
        return eventSport;
    }

    public void setEventSport(String eventSport) {          //Mutator For Sport Name
        this.eventSport = eventSport;
    }

    public String getEventDate() {            //Accessor For Event Date
        return eventDate;
    }

    public void setEventDate(String eventDate) {      //Mutator For Event Date
        this.eventDate = eventDate;
    }

    public String getEventLocation() {      //Accessor For Event Location
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {        //Mutator For Event Location
        this.eventLocation = eventLocation;
    }

    public String getEventTime() {      //Accessor For Event Time
        return eventTime;
    }

    public void setEventTime(String eventTime) {        //Mutator For Event Time
        this.eventTime = eventTime;
    }

    public int getEventPlayers() {          //Accessor For Number of players
        return eventPlayers;
    }

    public void setEventPlayers(int eventPlayers) {     //Mutator For Number of Players
        this.eventPlayers = eventPlayers;
    }

    public String getEventInfo() {      //Accessor For Event Info
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {        //Mutator For Event Info
        this.eventInfo = eventInfo;
    }

    public String getCreatedBy() {      //Accessor For Event Info
        return createdBy;
    }

    public void setCreatedBy(String creator) {        //Mutator For Event Info
        this.createdBy = creator;
    }

}
