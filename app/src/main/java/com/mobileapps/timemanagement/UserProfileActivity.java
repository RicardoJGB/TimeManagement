package com.mobileapps.timemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextShiftStart,editTextShiftEnd;
    Button buttonClockIn, buttonClockOut;
    Spinner spinnerIn,spinnerOut;

    DatabaseReference databaseTimes,databaseTimes2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        databaseTimes=FirebaseDatabase.getInstance().getReference("Times In");
        databaseTimes2=FirebaseDatabase.getInstance().getReference("Times Out");

        editTextShiftStart=findViewById(R.id.etShiftStart);
        editTextShiftEnd=findViewById(R.id.etShiftEnd);
        buttonClockIn=findViewById(R.id.btnClockIn);
        buttonClockOut=findViewById(R.id.btnClockOut);
        spinnerIn=findViewById(R.id.spinIn);
        spinnerOut=findViewById(R.id.spinOut);

        findViewById(R.id.btnClockIn).setOnClickListener(this);
        findViewById(R.id.btnClockOut).setOnClickListener(this);
    }

    private void recordTimeIn(){
        String shiftStart=editTextShiftStart.getText().toString();
        String dayOfWeekClockIn=spinnerIn.getSelectedItem().toString();

        if(!shiftStart.isEmpty()){

           String databaseID= databaseTimes.push().getKey();

           ClockInInfo clockInInfo = new ClockInInfo(databaseID,shiftStart,dayOfWeekClockIn);
           databaseTimes.child(databaseID).setValue(clockInInfo);

           Toast.makeText(getApplicationContext(),"Time added",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(),"Please enter a valid time",Toast.LENGTH_LONG).show();

        }

    }

    private void recordTimeOut(){

        String shiftEnd=editTextShiftEnd.getText().toString();
        String dayOfWeekClockOut=spinnerOut.getSelectedItem().toString();

        if (!shiftEnd.isEmpty()){

            String databaseID2= databaseTimes2.push().getKey();

            ClockOutInfo clockOutInfo = new ClockOutInfo(databaseID2,shiftEnd,dayOfWeekClockOut);
            databaseTimes2.child(databaseID2).setValue(clockOutInfo);

            Toast.makeText(getApplicationContext(),"Time added",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(),"Please enter a valid time",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnClockIn:
            recordTimeIn();
            break;
            case R.id.btnClockOut:
                recordTimeOut();
                break;
        }


    }
}
