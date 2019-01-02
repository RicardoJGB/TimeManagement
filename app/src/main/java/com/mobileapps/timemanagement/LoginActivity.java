package com.mobileapps.timemanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewSignUp;
    EditText etEmail,etPassword;
    ProgressBar progressBar;
    Button buttonLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        etEmail=findViewById(R.id.etUserEmail);
        etPassword=findViewById(R.id.etUserPassword);
        textViewSignUp=findViewById(R.id.tvSignUp);
        buttonLogin=findViewById(R.id.btnLogin);
        progressBar=findViewById(R.id.progressBar);
        findViewById(R.id.tvSignUp).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    private void userLogin(){
        String userEmail= etEmail.getText().toString();
        String userPassword=etPassword.getText().toString();

        if (userEmail.isEmpty()){
            etEmail.setError("Both fields are required. Please input an e-mail");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            etEmail.setError("E-mail in the wrong format. Please enter valid e-mail");
            etEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()){
            etPassword.setError("Both fields are required. Please input a password");
            etPassword.requestFocus();
            return;
        }

        if (userPassword.length()<6){
            etPassword.setError("Password must have at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()){
                    Intent intentGoToUserProfile = new Intent(getApplicationContext(),UserProfileActivity.class);
                    //Clears activities on top of the stack
                    intentGoToUserProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentGoToUserProfile);

                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tvSignUp:
                Intent intentGoToSignUp = new Intent(this,SignUpActivity.class);
                startActivity(intentGoToSignUp);
                break;

            case R.id.btnLogin:
                userLogin();
                break;
        }

    }
}