package com.example.kavach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.content.ActivityNotFoundException;
import android.speech.RecognizerIntent;
import android.widget.Switch;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import android.content.SharedPreferences;
import android.content.Intent;


import pl.droidsonroids.gif.GifImageView;

public class VoiceRecognition extends AppCompatActivity {

    //Hooks
    GifImageView voice;

    //Animations
    Animation speechAnimation;

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    private static final String TRIGGER_WORD_KEY = "trigger_word";
    private static final String DEFAULT_TRIGGER_WORD = "shield";
    private SwitchCompat switchStatus;
    private boolean isSpeechRecognitionEnabled = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        speechAnimation = AnimationUtils.loadAnimation(this, R.anim.speech_animation);

        // Hooks
        voice = findViewById(R.id.gifsr);
        voice.setAnimation(speechAnimation);

        // Initialize the Switch widget
        switchStatus = findViewById(R.id.switch1);
        switchStatus.setChecked(false); // Default state is "disabled"
        switchStatus.setText("Disabled    "); // Default text

        switchStatus.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSpeechRecognitionEnabled = isChecked;
                switchStatus.setText(isChecked ? "Enabled    " : "Disabled    ");
            }
        });

        Button btnSpeechRecognition = findViewById(R.id.btnSpeechRecognition);
        btnSpeechRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSpeechRecognitionEnabled) {
                    startSpeechRecognition();
                    Toast.makeText(VoiceRecognition.this, "Work is in Prototype Stage. Still Developing.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VoiceRecognition.this, "Speech recognition is disabled. Enable the switch to use it.", Toast.LENGTH_SHORT).show();
                }
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

                // Save the selected item to SharedPreferences as lowercase
                SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TRIGGER_WORD_KEY, selectedItem.toLowerCase());
                editor.apply();
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

                // Get the trigger word from SharedPreferences with default value
                SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
                String triggerWord = sharedPreferences.getString(TRIGGER_WORD_KEY, DEFAULT_TRIGGER_WORD).toLowerCase();

                // Check if the recognized text contains the trigger word
                if (!triggerWord.isEmpty() && recognizedText.toLowerCase().contains(triggerWord)) {

                    // Call the sendSMS() method from SmsHandler to send the SMS
                    SmsHandler.sendSMS(this, "Hi, the sender has selected you as their Emergency contact. In time of future crisis, an SOS help message will be sent to you.");
                    // Trigger word is recognized, indicating that using toast
                    Toast.makeText(this, "Recognized Successful", Toast.LENGTH_SHORT).show();


                } else {
                    // Trigger word is not recognized
                    Toast.makeText(this, "Unable to Recognize. Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}



