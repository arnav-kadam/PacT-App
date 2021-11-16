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

public class AdminProfile extends AppCompatActivity {

    Button backHomeButton;
    TextView adminTextView, nameTextView, emailTextView, adminIDTextView, phoneNumberTextView, adminTypeTextView;
    DatabaseReference reff;
    DatabaseReference users;
    String admin_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        backHomeButton = findViewById(R.id.backHomeButton);
        adminTextView = findViewById(R.id.adminTextView);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        adminIDTextView = findViewById(R.id.adminIDTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        adminTypeTextView = findViewById(R.id.adminTypeTextView);
        admin_id = (String) getIntent().getSerializableExtra("admin_id");

        reff = FirebaseDatabase.getInstance().getReference().child("admin").child("admin_"+admin_id);
        users = FirebaseDatabase.getInstance().getReference().child("users").child(admin_id);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminTextView.setText("Profile: " + snapshot.child("name").getValue().toString());
                nameTextView.setText(snapshot.child("name").getValue().toString());
                emailTextView.setText(snapshot.child("email").getValue().toString());
                adminIDTextView.setText(snapshot.child("admin_id").getValue().toString());
                if(snapshot.child("admin_type").getValue().toString().equals("a")){
                    adminTypeTextView.setText("Administrator");
                }
                phoneNumberTextView.setText(snapshot.child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminProfile.this, "Error" + error, Toast.LENGTH_LONG).show();
            }
        });

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProfile.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });
    }


}