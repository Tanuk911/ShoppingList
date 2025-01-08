package com.example.activityone;

public class Item {
    String date;
    String location;

    public Item(String date, String location){
        this.date = date;
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
