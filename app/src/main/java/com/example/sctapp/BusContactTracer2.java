package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class BusContactTracer2 extends AppCompatActivity {

    FirebaseAuth fAuth;
    TextView textView, textView2, textView3;
    ListView listView;
    DatabaseReference reff;
    ArrayList<student> arrayList;
    ArrayAdapter arrayAdapter;
    student displayStudent, labels;
    ImageView profileImageView, homeImageView, logoutImageView;
    static ArrayList<ArrayList<Integer>> busConfig;
    String date, admin_id, name;
    Calendar calendar;

    public void initializeBusConfig() {
        busConfig = new ArrayList<ArrayList<Integer>>(10);

        ArrayList<Integer> first = new ArrayList<Integer>(Arrays.asList(2, 3, 4));
        ArrayList<Integer> second = new ArrayList<Integer>(Arrays.asList(1, 3, 4));
        ArrayList<Integer> third = new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5, 6));
        ArrayList<Integer> fourth = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 5, 6));
        ArrayList<Integer> fifth = new ArrayList<Integer>(Arrays.asList(3, 4, 6, 7, 8));
        ArrayList<Integer> sixth = new ArrayList<Integer>(Arrays.asList(3, 4, 5, 7, 8));
        ArrayList<Integer> seventh = new ArrayList<Integer>(Arrays.asList(5, 6, 8, 9, 10));
        ArrayList<Integer> eigth = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 9, 10));
        ArrayList<Integer> ninth = new ArrayList<Integer>(Arrays.asList(7, 8, 10));
        ArrayList<Integer> tenth = new ArrayList<Integer>(Arrays.asList(7, 8, 9));

        busConfig.add(first);
        busConfig.add(second);
        busConfig.add(third);
        busConfig.add(fourth);
        busConfig.add(fifth);
        busConfig.add(sixth);
        busConfig.add(seventh);
        busConfig.add(eigth);
        busConfig.add(ninth);
        busConfig.add(tenth);
    }

    public boolean checkStudentSeat(int originalSeatNumber, int checkSeatNumber) {

        int index = originalSeatNumber - 1;
        ArrayList<Integer> contactSeat = busConfig.get(index);
        for(int i = 0; i < contactSeat.size(); i ++) {
            if(checkSeatNumber == contactSeat.get(i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_contact_tracer2);
        final student newStudent = (student)getIntent().getSerializableExtra("student");
        calendar = Calendar.getInstance();
        profileImageView = findViewById(R.id.profileImageView);
        homeImageView = findViewById(R.id.homeImageView);
        logoutImageView = findViewById(R.id.logoutImageView);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        listView = findViewById(R.id.listView);
        fAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("students");
        arrayList = new ArrayList<student>();
        date = DateFormat.getDateInstance().format(calendar.getTime());
        admin_id = (String) getIntent().getSerializableExtra("admin_id");
        initializeBusConfig();
        name = newStudent.getName();
        textView.setText("Contact Tracing results for " + name);
        textView2.setText("The students listed below are on the same bus as " + name + ".");
        textView3.setText("The highlighted students have assigned seats that are in a close proximity to " + name + ".");
        textView.setVisibility(View.VISIBLE);
        // final int studentBusNumber = Integer.parseInt(newStudent.getBus_number());
        Query quer = FirebaseDatabase.getInstance().getReference().child("students").orderByChild("bus_number").equalTo(newStudent.getBus_number());

        quer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("called", "called");
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                //Log.i("children", snapshotIterator.);
                if (iterator.hasNext() == false) {
                    Log.i("data", "no data");
                }
                labels = new student("Name", "Grade", "Bus Number", "Seat Number", "Student ID", false);
                arrayList.add(labels);
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String name = next.child("name").getValue().toString();
                    String grade = next.child("grade").getValue().toString();
                    String bus_number = next.child("bus_number").getValue().toString();
                    String seat_number = next.child("seat_number").getValue().toString();
                    String student_id = next.child("student_id").getValue().toString();
                    Boolean highlight = checkStudentSeat(Integer.parseInt(newStudent.getSeat_number()), Integer.parseInt(seat_number));
                    displayStudent = new student(name, grade, bus_number, seat_number, student_id, highlight);
                    arrayList.add(displayStudent);
                }
                studentListAdapter adapter = new studentListAdapter(BusContactTracer2.this, R.layout.adapter_view_layout, arrayList);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BusContactTracer2.this, "Error. " + error, Toast.LENGTH_LONG);
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
                Intent intent = new Intent(BusContactTracer2.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusContactTracer2.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });
    }
}