package com.example.kavach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Switch;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import android.content.SharedPreferences;


public class VoiceRecognition extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    private static final String TRIGGER_WORD = "redhead"; // Default trigger word
    private Switch switchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        // Initialize the Switch widget
        switchStatus = findViewById(R.id.switch1);
        switchStatus.setChecked(false); // Default state is "deactivated"
        switchStatus.setText("Status: Deactivated"); // Default text

        Button btnSpeechRecognition = findViewById(R.id.btnSpeechRecognition);
        btnSpeechRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechRecognition();
                Toast.makeText(VoiceRecognition.this, "Work is in Prototype Stage. Still Developing.", Toast.LENGTH_SHORT).show();
            }
        });

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

                // Save the selected item to SharedPreferences
                SharedPreferences sP = getSharedPreferences("my_val", MODE_PRIVATE);
                SharedPreferences.Editor ed = sP.edit();
                ed.putString("name", selectedItem);
                ed.apply();

                // Read the value from SharedPreferences
                SharedPreferences sPRead = getSharedPreferences("my_val", MODE_PRIVATE);
                String editVal = sPRead.getString("name", "No value as of now");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case when nothing is selected (optional)
            }
        });
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            // Start the speech recognition activity
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            // Speech recognition not supported on this device
            Toast.makeText(this, "Speech recognition is not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            // Get the recognized speech results
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (result != null && result.size() > 0) {
                // Process the recognized speech result
                String recognizedText = result.get(0);

                // Convert recognized text to lowercase for case-insensitive comparison
                String lowerCaseText = recognizedText.toLowerCase();

                // Get the trigger word from SharedPreferences
                SharedPreferences sPRead = getSharedPreferences("my_val", MODE_PRIVATE);
                String triggerWord = sPRead.getString("name", "No value as of now");

                // Check if the recognized text contains the trigger word
                if (lowerCaseText.contains(triggerWord)) {
                    // Trigger word is recognized, set the Switch to "activated" state
                    switchStatus.setChecked(true);
                    switchStatus.setText("Status: Activated");

                    // Get user's current location (latitude and longitude)
                    double latitude = 12.345; // Replace with actual latitude
                    double longitude = 67.890; // Replace with actual longitude

                    // Call the handleLocationData() method from SmsHandler to send the SMS
//                    SmsHandler.handleLocationData(this, latitude, longitude);
                } else {
                    // Trigger word is not recognized, set the Switch to "deactivated" state
                    switchStatus.setChecked(false);
                    switchStatus.setText("Status: Deactivated");
                }
            }
        }
    }
}