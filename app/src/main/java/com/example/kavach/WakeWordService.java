package com.example.kavach;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import ai.picovoice.porcupine.Porcupine;
import ai.picovoice.porcupine.PorcupineException;
import ai.picovoice.porcupine.PorcupineManager;
import ai.picovoice.porcupine.PorcupineManagerCallback;



public class WakeWordService extends Service {

    private PorcupineManager porcupineManager;
    private static final int NOTIFICATION_ID = 123;

    String triggerWordKey = Constants.TRIGGER_WORD_KEY;
    String defaultTriggerWord = Constants.DEFAULT_TRIGGER_WORD;

    private LocationHandler locationHandler;

    private CameraHandler cameraHandler;


    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(2, createNotification());

        locationHandler = new LocationHandler(this, new LocationHandler.LocationListener() {
            @Override
            public void onLocationChanged(double latitude, double longitude) {

            }

            @Override
            public void onLocationChanged(Location location) {
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupWakeWordDetection();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopWakeWordDetection();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "WakeWordChannel";
            String channelName = "Wake Word Detection";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return new NotificationCompat.Builder(this, "WakeWordChannel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
    }

    private void setupWakeWordDetection() {
        Context context = this;

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String selectedWakeWord = sharedPreferences.getString(Constants.TRIGGER_WORD_KEY, Constants.DEFAULT_TRIGGER_WORD);

        Porcupine.BuiltInKeyword[] keywords = new Porcupine.BuiltInKeyword[]{
                Porcupine.BuiltInKeyword.valueOf(selectedWakeWord.toUpperCase())
        };

        try {
            porcupineManager = new PorcupineManager.Builder()
                    .setAccessKey("nnlklAGraQk1b9da+VcyK+r/q9bWUFU3mf5vq75rcsBWACqYwiIPHQ==")
                    .setKeywords(keywords)
                    .build(context, wakeWordCallback);

            porcupineManager.start();
        } catch (PorcupineException e) {
            e.printStackTrace(); // Handle the exception as needed
        }

    }


    private void stopWakeWordDetection() {
        if (porcupineManager != null) {
            try {
                porcupineManager.stop();
                porcupineManager.delete();
            } catch (PorcupineException e) {
                e.printStackTrace();
            }
        }
    }

    private PorcupineManagerCallback wakeWordCallback = new PorcupineManagerCallback() {
        @Override
        public void invoke(int keywordIndex) {
            SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
            String selectedWakeWord = sharedPreferences.getString(Constants.TRIGGER_WORD_KEY, Constants.DEFAULT_TRIGGER_WORD);


            if (keywordIndex == 0) {
                showToast("Wake word detected: " + selectedWakeWord);
                locationHandler.getCurrentLocation();
                Log.d("SOS","Msg sent through voice.");

            } else {
                showToast("Detected wake word does not match selected wake word");
            }


        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}