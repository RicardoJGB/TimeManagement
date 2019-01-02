package com.mobileapps.timemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonGoToLogin, buttonGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        buttonGoToLogin=findViewById(R.id.btnGoToLogin);
        buttonGoToSignUp=findViewById(R.id.btnGoToSignUp);

        findViewById(R.id.btnGoToLogin).setOnClickListener(this);
        findViewById(R.id.btnGoToSignUp).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnGoToLogin:
                Intent intentGoToLogin = new Intent(this,LoginActivity.class);
                startActivity(intentGoToLogin);
                break;
            case R.id.btnGoToSignUp:
                Intent intentGoToSignUp = new Intent(this, SignUpActivity.class);
                startActivity(intentGoToSignUp);
                break;

        }

    }
}
