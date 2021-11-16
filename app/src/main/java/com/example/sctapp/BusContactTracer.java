package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static java.util.Arrays.asList;

public class BusContactTracer extends AppCompatActivity {

    CardView contactTraceCardView;
    Button logoutButton, backButton, submitButton, contactTraceButton;
    EditText idEditText;
    ListView listView;
    FirebaseAuth fAuth;
    TextView messageTextView;
    String admin_id;
    DatabaseReference reff;
    ArrayList<String> studentInfo = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    student newStudent;
    Query quer;
    ValueEventListener listener;
    ImageView profileImageView, homeImageView, logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_contact_tracer);

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
                Intent intent = new Intent(BusContactTracer.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusContactTracer.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        idEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.INVISIBLE);
            }
        });

        contactTraceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BusContactTracer.this, BusContactTracer2.class);
                intent.putExtra("student", newStudent);
                intent.putExtra("admin_id", admin_id);
                //quer.removeEventListener(listener);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),BusContactTracer2.class));
            }
        });

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

                //listener = new ValueEventListener() {
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
                            String student_id = idEditText.getText().toString();
                            newStudent = new student(name, grade, bus_number, seat_number, student_id, false);
                            studentInfo.add("Name: " + name);
                            studentInfo.add("Grade: " + grade);
                            studentInfo.add("Bus number: " + bus_number);
                            studentInfo.add("Seat number: " + seat_number);

                        arrayAdapter = new ArrayAdapter<String>(BusContactTracer.this, android.R.layout.simple_list_item_1, studentInfo);
                        listView.setAdapter(arrayAdapter);
                        listView.setVisibility(View.VISIBLE);
                    }

                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BusContactTracer.this, "Error. " + error, Toast.LENGTH_LONG);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

            });
            contactTraceCardView.setVisibility(View.VISIBLE);
        }

    });
}}