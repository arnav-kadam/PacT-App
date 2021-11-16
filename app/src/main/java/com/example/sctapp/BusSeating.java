package com.example.sctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;

public class BusSeating extends AppCompatActivity {

    FirebaseAuth fAuth;
    ImageView profileImageView, homeImageView, logoutImageView;
    String admin_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seating);

        fAuth = FirebaseAuth.getInstance();
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        admin_id = (String) getIntent().getSerializableExtra("admin_id");


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
                Intent intent = new Intent(BusSeating.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusSeating.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });
    }
}