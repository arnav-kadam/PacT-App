package com.example.sctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentMarkAttendance extends AppCompatActivity {

    ImageView logoutImageView, homeImageView, profileImageView;
    FirebaseAuth fAuth;
    TextView dateTextView;
    Button submitButton;
    RadioGroup busRadioGroup, schoolRadioGroup;
    String displayDate, student_id, inputDate, identifier;
    Calendar calendar;
    int busRadioID, schoolRadioID;
    RadioButton busRadioButton, schoolRadioButton;
    Boolean bus, school;
    SimpleDateFormat formatter;
    studentAttendance attendance;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mark_attendance);

        calendar = Calendar.getInstance();
        fAuth = FirebaseAuth.getInstance();
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        submitButton = findViewById(R.id.submitButton);
        dateTextView = findViewById(R.id.dateTextView);
        busRadioGroup = findViewById(R.id.busRadioButton);
        schoolRadioGroup = findViewById(R.id.schoolRadioGroup);
        displayDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        student_id = (String) getIntent().getSerializableExtra("student_id");
        formatter = new SimpleDateFormat("dd_MM_yyyy");
        inputDate = formatter.format(calendar.getTime());

        Log.i("Date", inputDate);
        dateTextView.setText(displayDate);

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMarkAttendance.this, StudentProfile.class);
                intent.putExtra("id", student_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMarkAttendance.this, StudentMainActivity.class);
                intent.putExtra("id", student_id);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busRadioID = busRadioGroup.getCheckedRadioButtonId();

                if(busRadioID == -1){
                    Toast.makeText(StudentMarkAttendance.this, "Please select yes or no.", Toast.LENGTH_SHORT).show();
                }
                else{
                    busRadioButton = findViewById(busRadioID);
                    if(busRadioButton.getText().toString().equals("Yes")){
                        bus = true;
                    }
                    else{
                        bus = false;
                    }
                }
                schoolRadioID = schoolRadioGroup.getCheckedRadioButtonId();

                if(schoolRadioID == -1){
                    Toast.makeText(StudentMarkAttendance.this, "Please select yes or no.", Toast.LENGTH_SHORT).show();
                }
                else{
                    schoolRadioButton = findViewById(schoolRadioID);
                    if(schoolRadioButton.getText().toString().equals("Yes")){
                        school = true;
                    }
                    else{
                        school = false;
                    }
                }
                identifier = student_id + "_" + inputDate;
                attendance = new studentAttendance(inputDate, student_id, school, bus, identifier);
                reff = FirebaseDatabase.getInstance().getReference("student_attendance");
                String key = identifier;
                reff.child(key).setValue(attendance);

                Intent intent = new Intent(StudentMarkAttendance.this, StudentMainActivity.class);
                intent.putExtra("id", student_id);
                startActivity(intent);

            }
        });
    }
}