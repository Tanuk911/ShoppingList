package com.example.activityone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    EditText edtxtLocation;
    Button btnAdd, btnView;

    ArrayList<String> dates;
    ArrayList<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.main_datePicker);
        edtxtLocation = findViewById(R.id.main_edTxtLocation);
        btnAdd = findViewById(R.id.main_btnAdd);
        btnView = findViewById(R.id.main_btnView);

        // ******** Access global variables ********
        MyApplication app = (MyApplication) getApplicationContext();
        dates = app.dates;
        locations = app.locations;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReports();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addToList() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month is zero-based, so add 1
        int year = datePicker.getYear();

        String date = String.format("%02d/%02d/%04d", day, month, year);
        String locationInput = edtxtLocation.getText().toString();

        dates.add(date); // ******** Update global dates ********
        locations.add(locationInput); // ******** Update global locations ********

        edtxtLocation.setText("");

        Toast.makeText(this, date + ", " + locationInput, Toast.LENGTH_SHORT).show();
    }

    public void openReports() {
        Intent intent = new Intent(MainActivity.this, ListsActivity.class);
        startActivity(intent); // ******** No need to pass dates and locations explicitly ********
    }
}
