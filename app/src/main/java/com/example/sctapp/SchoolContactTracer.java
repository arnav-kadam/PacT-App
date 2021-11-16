package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SchoolContactTracer extends AppCompatActivity {

    CardView contactTraceCardView;
    Button logoutButton, backButton, submitButton, contactTraceButton;
    EditText idEditText;
    ListView listView;
    FirebaseAuth fAuth;
    TextView messageTextView;
    DatabaseReference reff;
    ArrayList<String> studentInfo = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    Query quer;
    student newStudent;
    String admin_id;
    ImageView profileImageView, homeImageView, logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_contact_tracer);

        contactTraceCardView = findViewById(R.id.contactTraceCardView);
        logoutButton = findViewById(R.id.logoutButton);
        backButton = findViewById(R.id.backButton);
        idEditText = findViewById(R.id.idEditText);
        submitButton = findViewById(R.id.submitButton);
        listView = findViewById(R.id.listView);
        contactTraceButton = findViewById(R.id.contactTraceButton);
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        messageTextView = findViewById(R.id.messageTextView);
        admin_id = (String) getIntent().getSerializableExtra("admin_id");
        fAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("students");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentInfo.clear();
                String id = idEditText.getText().toString();
                if(TextUtils.isEmpty(id)){
                    idEditText.setError("Please enter a valid student id");
                    return;
                }
                reff = FirebaseDatabase.getInstance().getReference("students");
                quer = FirebaseDatabase.getInstance().getReference().child("students").orderByChild("student_id").equalTo(id);

                quer.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            String name = next.child("name").getValue().toString();
                            String grade = next.child("grade").getValue().toString();
                            String bus_number = next.child("bus_number").getValue().toString();
                            String seat_number = next.child("seat_number").getValue().toString();
                            String class1 = next.child("class_1").getValue().toString();
                            String class2 = next.child("class_2").getValue().toString();
                            String class3 = next.child("class_3").getValue().toString();
                            String class4 = next.child("class_4").getValue().toString();
                            String class5 = next.child("class_5").getValue().toString();
                            String class6 = next.child("class_6").getValue().toString();
                            String class7 = next.child("class_7").getValue().toString();
                            String class8 = next.child("class_8").getValue().toString();
                            String student_id = idEditText.getText().toString();
                            newStudent = new student(name, grade, bus_number, seat_number, student_id, false, class1, class2, class3, class4, class5, class6, class7, class8);
                            studentInfo.add("Name: " + name);
                            studentInfo.add("Grade: " + grade);
                            studentInfo.add("Bus number: " + bus_number);
                            studentInfo.add("Seat number: " + seat_number);
                            studentInfo.add("Class 1 room number: " + class1);
                            studentInfo.add("Class 2 room number: " + class2);
                            studentInfo.add("Class 3 room number: " + class3);
                            studentInfo.add("Class 4 room number: " + class4);
                            studentInfo.add("Class 5 room number: " + class5);
                            studentInfo.add("Class 6 room number: " + class6);
                            studentInfo.add("Class 7 room number: " + class7);
                            studentInfo.add("Class 8 room number: " + class8);

                            arrayAdapter = new ArrayAdapter<String>(SchoolContactTracer.this, android.R.layout.simple_list_item_1, studentInfo);
                            listView.setAdapter(arrayAdapter);
                            listView.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SchoolContactTracer.this, "Error. " + error, Toast.LENGTH_LONG);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

                });
                contactTraceCardView.setVisibility(View.VISIBLE);
            }

        });

        contactTraceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolContactTracer.this, SchoolContactTracer2.class);
                intent.putExtra("student", newStudent);
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
                Intent intent = new Intent(SchoolContactTracer.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolContactTracer.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

    }
}