package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class StudentAdmin extends AppCompatActivity {

    EditText idEditText, nameEditText, gradeEditText, busNumberEditText, seatNumberEditText, class1, class2, class3, class4, class5, class6, class7, class8;
    Button findStudentButton, updateStudent, addStudentButton;
    Query quer;
    CardView cardView;
    ImageView logoutImageView, homeImageView, profileImageView;
    String admin_id, id, name, grade, bus_number, seat_number, class_1, class_2, class_3, class_4, class_5, class_6, class_7, class_8;
    FirebaseDatabase fire;
    DatabaseReference reff;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admin);

        idEditText = findViewById(R.id.idEditText);
        nameEditText = findViewById(R.id.nameEditText);
        gradeEditText = findViewById(R.id.gradeEditText);
        busNumberEditText = findViewById(R.id.busNumberEditText);
        seatNumberEditText = findViewById(R.id.seatNumberEditText);
        findStudentButton = findViewById(R.id.findStudentButton);
        updateStudent = findViewById(R.id.updateBusInfo);
        addStudentButton = findViewById(R.id.addStudentButton);
        logoutImageView = findViewById(R.id.logoutImageView);
        homeImageView = findViewById(R.id.homeImageView);
        profileImageView = findViewById(R.id.profileImageView);
        fAuth = FirebaseAuth.getInstance();
        cardView = findViewById(R.id.cardView);
        class1 = findViewById(R.id.class1);
        class2 = findViewById(R.id.class2);
        class3 = findViewById(R.id.class3);
        class4 = findViewById(R.id.class4);
        class5 = findViewById(R.id.class5);
        class6 = findViewById(R.id.class6);
        class7 = findViewById(R.id.class7);
        class8 = findViewById(R.id.class8);
        admin_id = (String) getIntent().getSerializableExtra("admin_id");

        findStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = idEditText.getText().toString();
                quer = FirebaseDatabase.getInstance().getReference().child("students").orderByChild("student_id").equalTo(id);

                quer.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            name = next.child("name").getValue().toString();
                            nameEditText.setText(name);
                            grade = next.child("grade").getValue().toString();
                            gradeEditText.setText(grade);
                            bus_number = next.child("bus_number").getValue().toString();
                            busNumberEditText.setText(bus_number);
                            seat_number = next.child("seat_number").getValue().toString();
                            seatNumberEditText.setText(seat_number);
                            class_1 = next.child("class_1").getValue().toString();
                            class1.setText(class_1);
                            class_2 = next.child("class_2").getValue().toString();
                            class2.setText(class_2);
                            class_3 = next.child("class_3").getValue().toString();
                            class3.setText(class_3);
                            class_4 = next.child("class_4").getValue().toString();
                            class4.setText(class_4);
                            class_5 = next.child("class_5").getValue().toString();
                            class5.setText(class_5);
                            class_6 = next.child("class_6").getValue().toString();
                            class6.setText(class_6);
                            class_7 = next.child("class_7").getValue().toString();
                            class7.setText(class_7);
                            class_8 = next.child("class_8").getValue().toString();
                            class8.setText(class_8);
                        }
                        cardView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        updateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditText.getText().toString();
                grade = gradeEditText.getText().toString();
                bus_number = busNumberEditText.getText().toString();
                seat_number = seatNumberEditText.getText().toString();
                class_1 = class1.getText().toString();
                class_2 = class2.getText().toString();
                class_3 = class3.getText().toString();
                class_4 = class4.getText().toString();
                class_5 = class5.getText().toString();
                class_6 = class6.getText().toString();
                class_7 = class7.getText().toString();
                class_8 = class8.getText().toString();
                reff = FirebaseDatabase.getInstance().getReference("students");
                reff.child("student"+id).child("name").setValue(name);
                reff.child("student"+id).child("grade").setValue(grade);
                reff.child("student"+id).child("bus_number").setValue(bus_number);
                reff.child("student"+id).child("seat_number").setValue(seat_number);
                reff.child("student"+id).child("class_1").setValue(class_1);
                reff.child("student"+id).child("class_2").setValue(class_2);
                reff.child("student"+id).child("class_3").setValue(class_3);
                reff.child("student"+id).child("class_4").setValue(class_4);
                reff.child("student"+id).child("class_5").setValue(class_5);
                reff.child("student"+id).child("class_6").setValue(class_6);
                reff.child("student"+id).child("class_7").setValue(class_7);
                reff.child("student"+id).child("class_8").setValue(class_8);
                Toast.makeText(StudentAdmin.this, "Information updated successfully.", Toast.LENGTH_LONG).show();

            }
        });

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(StudentAdmin.this, AdminProfile.class);
                Intent intent = new Intent(StudentAdmin.this, InputInfo.class);
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
                Intent intent = new Intent(StudentAdmin.this, AdminProfile.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentAdmin.this, MainActivity.class);
                intent.putExtra("admin_id", admin_id);
                startActivity(intent);
            }
        });


    }
}