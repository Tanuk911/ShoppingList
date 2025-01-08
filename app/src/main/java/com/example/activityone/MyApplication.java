package com.example.activityone;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    public ArrayList<String> dates = new ArrayList<>(); // ******** Global Dates ********
    public ArrayList<String> locations = new ArrayList<>(); // ******** Global Locations ********
    public Map<Integer, ArrayList<String>> itemCollections = new HashMap<>(); // ******** Global Item Collections ********
}
