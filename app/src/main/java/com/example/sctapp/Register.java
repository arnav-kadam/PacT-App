package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Register extends AppCompatActivity {

    EditText emailEditText, passwordEditText, idEditText;
    Button registerButton;
    FirebaseAuth fAuth;
    String student_id, email, type, name, admin_id;
    ProgressBar progressBar;
    TextView takeToLogin;
    RadioButton radioButton;
    RadioGroup radioGroup;
    Query quer;
    student newStudent;
    User newUser;
    admin Admin;
    int radioID;
    boolean student, admin;
    String id;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        takeToLogin = findViewById(R.id.takeToLoginTextView);
        idEditText = findViewById(R.id.idEditText);
        radioGroup = findViewById(R.id.radioGroup);
        id = (String) getIntent().getSerializableExtra("id");


        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            email = fAuth.getCurrentUser().getEmail();
            quer = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(email);
            quer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        type = next.child("user_type").getValue().toString();
                        student_id = next.child("id").getValue().toString();
                    }
                    if(type.equals("s")){
                        Intent intent = new Intent(Register.this, StudentMainActivity.class);
                        intent.putExtra("id", student_id);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.putExtra("admin_id", student_id);
                        startActivity(intent);
                    }
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();
                final String id = idEditText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailEditText.setError("Please enter a valid email");
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length() < 6){
                    passwordEditText.setError("Please enter a valid password of at least 6 characters");
                    return;
                }
                if(TextUtils.isEmpty(id)){
                    passwordEditText.setError("Please enter a valid id");
                    return;
                }
                radioID = radioGroup.getCheckedRadioButtonId();

                if (radioID == -1){
                    Toast.makeText(Register.this, "Please select student or admin.", Toast.LENGTH_LONG).show();
                }
                else {
                    radioButton = findViewById(radioID);

                    if(radioButton.getText().toString().equals("Student")){
                        student = true;
                        admin = false;
                    }
                    if(radioButton.getText().toString().equals("Admin")){
                        admin = true;
                        student = false;
                    }
                }
                progressBar.setVisibility(View.VISIBLE);

                if(student == true){
                    quer = FirebaseDatabase.getInstance().getReference().child("students").orderByChild("student_id").equalTo(id);

                    quer.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                            Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                            newStudent = null;
                            while (iterator.hasNext()) {
                                DataSnapshot next = (DataSnapshot) iterator.next();
                                name = next.child("name").getValue().toString();
                                String grade = next.child("grade").getValue().toString();
                                String bus_number = next.child("bus_number").getValue().toString();
                                String seat_number = next.child("seat_number").getValue().toString();
                                student_id = next.child("student_id").getValue().toString();
                                newStudent = new student(name, grade, bus_number, seat_number, student_id, false);
                            }//end of while
                            if(newStudent != null) {
                                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "Account created", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(Register.this, StudentMainActivity.class);
                                            intent.putExtra("id", student_id);
                                            intent.putExtra("name", name);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                newUser = new User(email, id, "s");
                                reff = FirebaseDatabase.getInstance().getReference().child("users");
                                reff.child(id).setValue(newUser);
                            }
                            else {
                                Toast.makeText(Register.this, "You are not authenticated to make an account.", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Register.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                        }
                    }); //end of quer

                }//end of if statement
                if(admin == true){
                    Query quer1 = FirebaseDatabase.getInstance().getReference().child("admin").orderByChild("admin_id").equalTo(id);

                    quer1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                            Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                            Admin = null;
                            while (iterator.hasNext()) {
                                DataSnapshot next = (DataSnapshot) iterator.next();
                                admin_id = next.child("admin_id").getValue().toString();
                                String admin_type = next.child("admin_type").getValue().toString();
                                String email = next.child("email").getValue().toString();
                                String name = next.child("name").getValue().toString();
                                Admin = new admin(admin_id, admin_type, email, name);
                            }//end of while
                            if(Admin != null) {
                                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "Account created", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(Register.this, MainActivity.class);
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        } else {
                                            Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                newUser = new User(email, id, "a" );
                                reff = FirebaseDatabase.getInstance().getReference().child("users");
                                reff.child(id).setValue(newUser);

                            }//end of if new student != null
                            else {
                                Toast.makeText(Register.this, "You are not authenticated to make an account.", Toast.LENGTH_LONG).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Register.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                        }
                    }); //end of quer
                }
            }
        });

        takeToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                //finish();
            }
        });
    }
}