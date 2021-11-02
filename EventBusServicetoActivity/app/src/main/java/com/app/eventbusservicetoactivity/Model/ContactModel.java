package com.app.eventbusservicetoactivity.Model;

public class ContactModel {
    String id;
    String name;

    public ContactModel(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    String message;

    public ContactModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
