package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity { //admin main page

    CardView busContactTrace, schoolContactTrace, markAttendance, floorMaps, studentAdmin, busSeatChart;
    TextView displayNameTextView;
    ImageView logoutImageView, profileImageView;
    FirebaseAuth fAuth;
    String admin_id, name;
    Query quer;
    Boolean submittedAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        busContactTrace = findViewById(R.id.busContactTrace);
        schoolContactTrace = findViewById(R.id.schoolContactTrace);
        markAttendance = findViewById(R.id.markAttendance);
        floorMaps = findViewById(R.id.floorMaps);
        studentAdmin = findViewById(R.id.studentAdmin);
        busSeatChart = findViewById(R.id.busSeatChart);
        logoutImageView = findViewById(R.id.logoutImageView);
        profileImageView = findViewById(R.id.profileImageView);
        fAuth = FirebaseAuth.getInstance();
        displayNameTextView = findViewById(R.id.nameDisplayTextView);
        admin_id = (String) getIntent().getSerializableExtra("admin_id");
        quer = FirebaseDatabase.getInstance().getReference().child("admin").orderByChild("admin_id").equalTo(admin_id);

        quer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    name = next.child("name").getValue().toString();
                }
                displayNameTextView.setText("Welcome back, " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        busContactTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BusContactTracer.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        schoolContactTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SchoolContactTracer.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, admin_mark_attendance.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        floorMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        studentAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentAdmin.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);            }
        });

        busSeatChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BusSeating.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);            }
        });
    }
}