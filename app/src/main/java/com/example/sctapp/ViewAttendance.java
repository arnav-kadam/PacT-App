package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ViewAttendance extends AppCompatActivity {

    CalendarView calendarView;
    TextView schoolTextView, busTextView;
    String date, student_id;
    Boolean school, bus;
    FirebaseDatabase fire;
    Query quer;
    FirebaseAuth fAuth;
    ImageView profileImageView, homeImageView, logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        calendarView = findViewById(R.id.calendarView);
        schoolTextView = findViewById(R.id.schoolTextView);
        busTextView = findViewById(R.id.busTextView);
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        student_id = (String) getIntent().getSerializableExtra("student_id");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if((i1+1) < 10){
                    date = i2 + "_" + "0" + (i1 + 1) + "_" + i;
                }
                else{
                    date = i2 + "_" + (i1 + 1) + "_" + i;
                }
                quer = FirebaseDatabase.getInstance().getReference().child("student_attendance").orderByChild("identifier").equalTo(student_id + "_" + date);
                quer.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            school = (Boolean) next.child("school").getValue();
                            bus = (Boolean) next.child("bus").getValue();
                            if(school == false){
                                schoolTextView.setText("No");
                            }
                            else{
                                schoolTextView.setText("Yes");
                            }
                            if(bus == false){
                                busTextView.setText("No");
                            }
                            else{
                                busTextView.setText("Yes");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ViewAttendance.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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
                Intent intent = new Intent(ViewAttendance.this, StudentProfile.class);
                intent.putExtra("id", student_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAttendance.this, StudentMainActivity.class);
                intent.putExtra("id", student_id);
                startActivity(intent);
            }
        });

    }
}