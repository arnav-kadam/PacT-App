package com.example.sctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputInfo extends AppCompatActivity {

    Button addStudent;
    EditText nameEditText, gradeEditText, busNumberEditText, seatNumberEditText, idEditText, class1, class2, class3, class4, class5, class6, class7, class8;
    FirebaseAuth fAuth;
    DatabaseReference reff;
    String admin_id;
    ImageView logoutImageView, homeImageView, profileImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        nameEditText = findViewById(R.id.nameEditText);
        gradeEditText = findViewById(R.id.gradeEditText);
        busNumberEditText = findViewById(R.id.busNumberEditText);
        seatNumberEditText = findViewById(R.id.seatNumberEditText);
        idEditText = findViewById(R.id.idEditText);
        addStudent = findViewById(R.id.submitButton);
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        class1 = findViewById(R.id.class1);
        class2 = findViewById(R.id.class2);
        class3 = findViewById(R.id.class3);
        class4 = findViewById(R.id.class4);
        class5 = findViewById(R.id.class5);
        class6 = findViewById(R.id.class6);
        class7 = findViewById(R.id.class7);
        class8 = findViewById(R.id.class8);
        fAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("students");
        admin_id = (String) getIntent().getSerializableExtra("admin_id");

        addStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(InputInfo.this, "User created successfully!", Toast.LENGTH_LONG);
                String name = nameEditText.getText().toString();
                String grade = gradeEditText.getText().toString();
                String bus_number = busNumberEditText.getText().toString();
                String seat_number = seatNumberEditText.getText().toString();
                String student_id = idEditText.getText().toString();
                String class_1 = class1.getText().toString();
                String class_2 = class2.getText().toString();
                String class_3 = class3.getText().toString();
                String class_4 = class4.getText().toString();
                String class_5 = class5.getText().toString();
                String class_6 = class6.getText().toString();
                String class_7 = class7.getText().toString();
                String class_8 = class8.getText().toString();

                student helper = new student(name, grade, bus_number, seat_number, student_id, false, class_1, class_2, class_3, class_4, class_5, class_6, class_7, class_8);

                reff.child("student"+student_id).setValue(helper);
                Toast.makeText(InputInfo.this, "New student created!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
                Intent intent = new Intent(InputInfo.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputInfo.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

    }
}