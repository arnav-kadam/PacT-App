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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class StudentMainActivity extends AppCompatActivity {

    CardView markAttendance, viewAttendance;
    ImageView profileImageView, logoutImageView;
    String student_id, name;
    TextView nameDisplayTextView;
    FirebaseAuth fAuth;
    Query quer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        markAttendance = findViewById(R.id.markAttendance);
        viewAttendance = findViewById(R.id.viewAttendance);
        profileImageView = findViewById(R.id.profileImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        fAuth = FirebaseAuth.getInstance();
        nameDisplayTextView = findViewById(R.id.nameDisplayTextView);
        student_id = (String) getIntent().getSerializableExtra("id");

        quer = FirebaseDatabase.getInstance().getReference().child("students").orderByChild("student_id").equalTo(student_id);

        quer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    name = next.child("name").getValue().toString();
                }
                nameDisplayTextView.setText("Welcome back, " + name + " !");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMainActivity.this, StudentProfile.class);
                intent.putExtra("id", student_id);
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

        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMainActivity.this, StudentMarkAttendance.class);
                intent.putExtra("student_id", student_id);
                startActivity(intent);
            }
        });

        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentMainActivity.this, ViewAttendance.class);
                intent.putExtra("student_id", student_id);
                startActivity(intent);
            }
        });
    }
}

