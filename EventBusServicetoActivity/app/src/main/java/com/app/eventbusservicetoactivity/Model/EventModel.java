package com.app.eventbusservicetoactivity.Model;

public class EventModel {
    String eventid;

    public EventModel() {
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventmessage() {
        return eventmessage;
    }

    public void setEventmessage(String eventmessage) {
        this.eventmessage = eventmessage;
    }

    String eventname;

    public EventModel(String eventid, String eventname, String eventmessage) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.eventmessage = eventmessage;
    }

    String eventmessage;
}
