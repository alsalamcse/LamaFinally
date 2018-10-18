package com.sholi.lama.lamafinally;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSave;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataHandler();

            }
        });


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LogInActivity.class));
            finish();
            return;
        } else {
            String userName = user.getDisplayName();
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
            }
        }
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //4.
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Toast.makeText(SignUpActivity.this, "user is signed in.", Toast.LENGTH_SHORT).show();

                } else {
                    //user signed out
                    Toast.makeText(SignUpActivity.this, "user signed out.", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }

    private void dataHandler() {
        {
            boolean isok = true;

            String email= etEmail.getText().toString();
            String password=etPassword.getText().toString();
            String fname=etFirstName.getText().toString();
            String lname=etLastName.getText().toString();
            String phone=etPhone.getText().toString();
            if (etEmail.length() < 4 || email.indexOf('@') < 0 || email.indexOf('.')<0)
            {
                etEmail.setError("Wrong Email");
                isok = false;
            }
            if (password.length() < 3) {
                etPassword.setError("Have to be at least 8 char");
                isok = false;
            }
            if (isok){
                createAcount(email, password);
            }



        }

    }

    private void createAcount(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                    //updateUserProfile(task.getResult().getUser());
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }

            }
        }