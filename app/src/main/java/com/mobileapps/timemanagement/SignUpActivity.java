package com.mobileapps.timemanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail,etPassword;
    ProgressBar progressBar;
    TextView textViewBackToLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail=findViewById(R.id.etUserEmailRegistry);
        etPassword=findViewById(R.id.etUserPasswordRegistry);
        progressBar=findViewById(R.id.progressBar);
        textViewBackToLogin=findViewById(R.id.tvBackToLogin);

        mAuth=FirebaseAuth.getInstance();

        findViewById(R.id.btnSignUp).setOnClickListener(this);
        findViewById(R.id.tvBackToLogin).setOnClickListener(this);
    }

    private void registerUser(){
        String newEmail= etEmail.getText().toString();
        String newPassword=etPassword.getText().toString();

        if (newEmail.isEmpty()){
            etEmail.setError("Both fields are required. Please input an e-mail");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
            etEmail.setError("E-mail in the wrong format. Please enter valid e-mail");
            etEmail.requestFocus();
            return;
        }

        if (newPassword.isEmpty()){
            etPassword.setError("Both fields are required. Please input a password");
            etPassword.requestFocus();
            return;
        }

        if (newPassword.length()<6){
            etPassword.setError("Password must have at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(newEmail,newPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Registration Successful!",Toast.LENGTH_LONG).show();
                }else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                registerUser();
                break;

            case R.id.tvBackToLogin:
                Intent intentBackToLogin = new Intent(this,LoginActivity.class);
                startActivity(intentBackToLogin);
                break;

        }

    }
}
