package com.example.kavach;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

public class SpeechRecognition extends AppCompatActivity {

    // UI components
    AutoCompleteTextView autoCompleteTextView;
    GifImageView voice;
    Button startButton;
    Button stopButton;

    // State variables
    boolean isWakeWordServiceRunning = false;
    String selectedTriggerWord;

    // SharedPreferences keys
    private static final String PREFS_NAME = "my_prefs";
    private static final String PREF_IS_WAKE_WORD_RUNNING = "is_wake_word_running";
    private static final String PREF_SELECTED_TRIGGER_WORD = "selected_trigger_word";


    @SuppressLint("MissingInflatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognition);

        Animation speechAnimation = AnimationUtils.loadAnimation(this, R.anim.speech_animation);

        // Hooks
        voice = findViewById(R.id.gifsr);
        voice.setAnimation(speechAnimation);

        // Initializing UI components
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        // Initializing the triggerWords array from resources
        String[] triggerWords = getResources().getStringArray(R.array.trigger_words);

        // Initializing the ArrayAdapter for the AutoCompleteTextView
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.trigger_word, triggerWords);
        autoCompleteTextView.setAdapter(adapterItems);

        // Restoring selected trigger word from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isWakeWordServiceRunning = sharedPreferences.getBoolean(PREF_IS_WAKE_WORD_RUNNING, false);
        selectedTriggerWord = sharedPreferences.getString(PREF_SELECTED_TRIGGER_WORD, null);

        // Setting selected trigger word and button states
        autoCompleteTextView.setText(selectedTriggerWord);
        startButton.setEnabled(!isWakeWordServiceRunning);
        stopButton.setEnabled(isWakeWordServiceRunning);

        // Setting item click listener for AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTriggerWord = adapterItems.getItem(position);
            }
        });

        // Setting click listeners for buttons
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTriggerWord != null) {
                    Log.d("234567","antra");
                    uploadKeyword();
                    setServiceEnabled(true);
                    startWakeWordService();
                    Log.d("234567","antra");
                } else {
                    showToast("Please select a trigger word before enabling.");
                }
            }
        });


        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWakeWordServiceRunning) {
                    stopWakeWordService();
                    // Clear selected trigger word
                    selectedTriggerWord = null;
                    autoCompleteTextView.setText(""); // Clear the text in AutoCompleteTextView
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Save the current state of isWakeWordServiceRunning to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_WAKE_WORD_RUNNING, isWakeWordServiceRunning);
        editor.putString(PREF_SELECTED_TRIGGER_WORD, selectedTriggerWord);
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // Start the wake word service
    private void startWakeWordService() {
        if (!isWakeWordServiceRunning) {

            // Start the wake word service
            Intent serviceIntent = new Intent(this, WakeWordService.class);
            startService(serviceIntent);

            // Disable start button and enable stop button
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            isWakeWordServiceRunning = true;
            Log.d("1234567890","vidit");
        }
    }


    // Save the selected trigger word to SharedPreferences
    private void uploadKeyword() {
        if (selectedTriggerWord != null) {
            // Save the selected trigger word to SharedPreferences as lowercase
            SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.TRIGGER_WORD_KEY, selectedTriggerWord.toLowerCase());
            editor.apply();
        }
    }


    // Stop the wake word service
    private void stopWakeWordService() {
        Intent serviceIntent = new Intent(this, WakeWordService.class);
        stopService(serviceIntent);

        // Enable start button and disable stop button
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        isWakeWordServiceRunning = false;
    }

    private void setServiceEnabled(boolean isEnabled) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.SERVICE_ENABLED_KEY, isEnabled);
        editor.apply();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
