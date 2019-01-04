package com.mobileapps.timemanagement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FusedLocationProviderClient client;

    EditText editTextShiftStart, editTextShiftEnd;
    Button buttonClockIn, buttonClockOut;
    Spinner spinnerIn, spinnerOut;

    DatabaseReference databaseTimes, databaseTimes2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        databaseTimes = FirebaseDatabase.getInstance().getReference("Times In");
        databaseTimes2 = FirebaseDatabase.getInstance().getReference("Times Out");

        editTextShiftStart = findViewById(R.id.etShiftStart);
        editTextShiftEnd = findViewById(R.id.etShiftEnd);
        buttonClockIn = findViewById(R.id.btnClockIn);
        buttonClockOut = findViewById(R.id.btnClockOut);
        spinnerIn = findViewById(R.id.spinIn);
        spinnerOut = findViewById(R.id.spinOut);

        findViewById(R.id.btnClockIn).setOnClickListener(this);
        findViewById(R.id.btnClockOut).setOnClickListener(this);
    }

    private void recordTimeIn() {
        String shiftStart = editTextShiftStart.getText().toString();
        String dayOfWeekClockIn = spinnerIn.getSelectedItem().toString();

        if (!shiftStart.isEmpty()) {

            String databaseID = databaseTimes.push().getKey();

            ClockInInfo clockInInfo = new ClockInInfo(databaseID, shiftStart, dayOfWeekClockIn);
            databaseTimes.child(databaseID).setValue(clockInInfo);

            Toast.makeText(getApplicationContext(), "Time added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid time", Toast.LENGTH_LONG).show();

        }

    }

    private void recordTimeOut() {

        String shiftEnd = editTextShiftEnd.getText().toString();
        String dayOfWeekClockOut = spinnerOut.getSelectedItem().toString();

        if (!shiftEnd.isEmpty()) {

            String databaseID2 = databaseTimes2.push().getKey();

            ClockOutInfo clockOutInfo = new ClockOutInfo(databaseID2, shiftEnd, dayOfWeekClockOut);
            databaseTimes2.child(databaseID2).setValue(clockOutInfo);

            Toast.makeText(getApplicationContext(), "Time added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid time", Toast.LENGTH_LONG).show();
        }

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    private void uglyLocation(){
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(UserProfileActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){
                    TextView tvLocation = findViewById(R.id.tvLocation);
                    tvLocation.setText(location.toString().trim());
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClockIn:
                recordTimeIn();
                uglyLocation();

            break;
            case R.id.btnClockOut:
                recordTimeOut();
                uglyLocation();
                break;
        }


    }
}
