package com.example.kavach;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreen extends AppCompatActivity {
    // Under Development

    ImageButton imageButton;
    TextView textView;

    private LocationHandler locationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        imageButton = findViewById(R.id.UnlockButton);

        textView = findViewById(R.id.editTextNumberPassword);

        //This part will request the focus of Keypad for inputting pin
        textView.requestFocus();

        InputMethodManager imm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        }
        assert imm != null;
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);

        locationHandler = new LocationHandler(this, new LocationHandler.LocationListener() {
            @Override
            public void onLocationChanged(double latitude, double longitude) {

            }

            @Override
            public void onLocationChanged(Location location) {
            }
        });

    }

    //Function for unlocking Screen
    public void unlock(View view){
        String currentPassword = textView.getText().toString();

        String unlcokPIN = "1234";
        String SOSPin = "2222";


        if (currentPassword.equals(unlcokPIN)){
            Toast.makeText(this, "Phone Unlocked", Toast.LENGTH_SHORT).show();
            Log.d("SOS", "Phone unlocked");
        }
        else if (currentPassword.equals(SOSPin)) {
            Toast.makeText(this, "Phone unlocked And SOS Sent.", Toast.LENGTH_SHORT).show();
            Log.d("SOS", "Phone unlocked and SOS Sent.");
            locationHandler.getCurrentLocation();
        }

    }

    }

