package com.example.sctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity {

    TextView takeToRegiserTextView;
    EditText emailEditText, passwordEditText;
    Button loginButton;
    ProgressBar loginProgressBar;
    FirebaseAuth fAuth;
    Query quer;
    DatabaseReference reff;
    String homePage, student_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        takeToRegiserTextView = findViewById(R.id.takeToRegisterTextView);
        fAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailEditText.setError("Please enter a valid email");
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length() < 6){
                    passwordEditText.setError("Please enter a valid password of at least 6 characters");
                    return;
                }
                loginProgressBar.setVisibility(View.VISIBLE);
                Log.i("email", email);
                Log.i("password", password);
                quer = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(email);

                quer.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            homePage = next.child("user_type").getValue().toString();
                            student_id = next.child("id").getValue().toString();
                        }
                        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful() && homePage.equals("a")){
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("admin_id", student_id);
                                    startActivity(intent);
                                }
                                else if(task.isSuccessful() && homePage.equals("s")){
                                    Intent intent = new Intent(Login.this, StudentMainActivity.class);
                                    intent.putExtra("id", student_id);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Login.this, "Error. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "There was an error " + error, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        takeToRegiserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
}