package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class SchoolContactTracer2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseAuth fAuth;
    TextView textView;
    ListView listView;
    Query quer;
    DatabaseReference reff;
    ArrayList<student> arrayList;
    ArrayAdapter arrayAdapter;
    student displayStudent, labels, originalStudent;
    ImageView profileImageView, homeImageView, logoutImageView;
    Spinner spinner;
    Calendar calendar;
    String text, date, admin_id, textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_contact_tracer2);

        final student newStudent = (student)getIntent().getSerializableExtra("student");
        textViewName = newStudent.getName();
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);
        fAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("students");
        arrayList = new ArrayList<student>();
        calendar = Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());
        admin_id = (String) getIntent().getSerializableExtra("admin_id");
        originalStudent = (student) getIntent().getSerializableExtra("student");

        textView.setText("Select a class number from the dropdown menu to view students in the same class as " + textViewName + ".");
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
                Intent intent = new Intent(SchoolContactTracer2.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchoolContactTracer2.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        arrayList.clear();
        text = adapterView.getItemAtPosition(i).toString();
        String number = String.valueOf(text.charAt(6));
        quer = FirebaseDatabase.getInstance().getReference().child("students").orderByChild("class_" + number).equalTo(originalStudent.returnRoomNumber(number));
        quer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                if (iterator.hasNext() == false) {
                    Log.i("data", "no data");
                }
                labels = new student("Name", "Grade", "Student id");
                arrayList.add(labels);
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String name = next.child("name").getValue().toString();
                    String grade = next.child("grade").getValue().toString();
                    String student_id = next.child("student_id").getValue().toString();
                    displayStudent = new student(name, grade, student_id);
                    arrayList.add(displayStudent);
                }
                studentListAdapter2 adapter = new studentListAdapter2(SchoolContactTracer2.this, R.layout.adapter_view_layout2, arrayList);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}