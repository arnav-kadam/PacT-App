package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class admin_mark_attendance extends AppCompatActivity {

    ImageView logoutImageView, homeImageView, profileImageView;
    FirebaseAuth fAuth;
    TextView dateTextView, busTextView, schoolTextView;
    EditText idEditText;
    Button submitAttendanceButton, findStudentButton;
    RadioGroup busRadioGroup, schoolRadioGroup;
    String displayDate, student_id, inputDate, identifier, admin_id, student_name;
    DatabaseReference reff, name;
    Calendar calendar;
    int busRadioID, schoolRadioID;
    RadioButton busRadioButton, schoolRadioButton;
    Boolean bus, school;
    SimpleDateFormat formatter;
    studentAttendance attendance;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mark_attendance);

        busTextView = findViewById(R.id.busTextView);
        schoolTextView = findViewById(R.id.schoolTextView);
        calendar = Calendar.getInstance();
        fAuth = FirebaseAuth.getInstance();
        idEditText = findViewById(R.id.idEditText);
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        submitAttendanceButton = findViewById(R.id.submitAttendanceButton);
        findStudentButton = findViewById(R.id.findStudentButton);
        dateTextView = findViewById(R.id.dateTextView);
        busRadioGroup = findViewById(R.id.busRadioButton);
        schoolRadioGroup = findViewById(R.id.schoolRadioGroup);
        displayDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        student_id = (String) getIntent().getSerializableExtra("student_id");
        formatter = new SimpleDateFormat("dd_MM_yyyy");
        inputDate = formatter.format(calendar.getTime());
        admin_id = (String) getIntent().getSerializableExtra("admin_id");
        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.INVISIBLE);

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
                Intent intent = new Intent(admin_mark_attendance.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_mark_attendance.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        findStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student_id = idEditText.getText().toString();
                if(TextUtils.isEmpty(student_id)){
                    idEditText.setError("Please enter a valid student id");
                    return;
                }
                name = FirebaseDatabase.getInstance().getReference("students").child("student"+student_id);
                name.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        student_name = snapshot.child("name").getValue().toString();
                        String[] firstName = student_name.split(" ");
                        String first_name = firstName[0];
                        busTextView.setText("Is " + first_name + " taking the bus today?");
                        schoolTextView.setText("Is " + first_name + " going to school today?");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                cardView.setVisibility(View.VISIBLE);

            }
        });

        submitAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busRadioID = busRadioGroup.getCheckedRadioButtonId();

                if(busRadioID == -1){
                    Toast.makeText(admin_mark_attendance.this, "Please select yes or no.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(admin_mark_attendance.this, "Please select yes or no.", Toast.LENGTH_SHORT).show();
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

                Intent intent = new Intent(admin_mark_attendance.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);

            }
        });
    }
}