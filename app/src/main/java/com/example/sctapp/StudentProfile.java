package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {

    Button backHomeButton;
    TextView studentTextView, nameTextView, gradeTextView, emailTextView, busNumberTextView, seatNumberTextView;
    DatabaseReference reff;
    DatabaseReference users;
    String student_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        backHomeButton = findViewById(R.id.backHomeButton);
        studentTextView = findViewById(R.id.studentTextView);
        nameTextView = findViewById(R.id.nameTextView);
        gradeTextView = findViewById(R.id.gradeTextView);
        emailTextView = findViewById(R.id.emailTextView);
        busNumberTextView = findViewById(R.id.busNumberTextView);
        seatNumberTextView = findViewById(R.id.seatNumberTextView);
        student_id = (String) getIntent().getSerializableExtra("id");

        reff = FirebaseDatabase.getInstance().getReference().child("students").child("student"+student_id);
        users = FirebaseDatabase.getInstance().getReference().child("users").child(student_id);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTextView.setText("Profile: " + snapshot.child("name").getValue().toString());
                studentTextView.setText(snapshot.child("name").getValue().toString());
                gradeTextView.setText(snapshot.child("name").getValue().toString());
                busNumberTextView.setText(snapshot.child("bus_number").getValue().toString());
                seatNumberTextView.setText(snapshot.child("seat_number").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfile.this, "Error" + error, Toast.LENGTH_LONG).show();
            }
        });

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emailTextView.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfile.this, "Error" + error, Toast.LENGTH_LONG).show();
            }
        });


        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentProfile.this, StudentMainActivity.class);
                intent.putExtra("id", student_id);
                startActivity(intent);
            }
        });
    }
}