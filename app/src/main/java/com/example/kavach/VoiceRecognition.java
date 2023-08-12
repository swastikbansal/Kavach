package com.example.kavach;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class VoiceRecognition extends AppCompatActivity {

    //Hooks
    GifImageView voice;

    //Animations
    Animation speechAnimation;

    private Button startButton;
    private Button stopButton;
    private boolean isWakeWordServiceRunning = false;

    String triggerWordKey = Constants.TRIGGER_WORD_KEY;
    String defaultTriggerWord = Constants.DEFAULT_TRIGGER_WORD;

    private LocationHandler locationHandler;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("2345", "qwerty");

        setContentView(R.layout.activity_voice_recognition);

        speechAnimation = AnimationUtils.loadAnimation(this, R.anim.speech_animation);

        // Hooks
        voice = findViewById(R.id.gifsr);
        voice.setAnimation(speechAnimation);


        Spinner spinnerDropdown = findViewById(R.id.spinnerDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDropdown.setAdapter(adapter);

        spinnerDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();

                // Save the selected item to SharedPreferences as lowercase
                SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.TRIGGER_WORD_KEY, selectedItem.toLowerCase());
                editor.apply();

                // Stop the current wake word service if running
                if (isWakeWordServiceRunning) {
                    stopWakeWordService();
                    startWakeWordService();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case when nothing is selected (if needed)
            }
        });


        // Initialize buttons
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        // Set click listeners for buttons
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWakeWordServiceRunning) {
                    startWakeWordService();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWakeWordServiceRunning) {
                    stopWakeWordService();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void startWakeWordService() {
        if (!isWakeWordServiceRunning) {

            // Start the wake word service
            Intent serviceIntent = new Intent(this, WakeWordService.class);
            startService(serviceIntent);

            // Disable start button and enable stop button
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            isWakeWordServiceRunning = true;
        }
    }

    private void stopWakeWordService() {
        Intent serviceIntent = new Intent(this, WakeWordService.class);
        stopService(serviceIntent);

        // Enable start button and disable stop button
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        isWakeWordServiceRunning = false;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}



