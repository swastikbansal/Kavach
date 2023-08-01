package com.example.kavach;


import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


public class SmsHandler {
    public static void sendSMS(Context context, String userLocation){


    // Method to send SMS (made static)
        try {
          //  String phoneNumber = getSavedEmergencyContactNumber(); // Retrieve the saved emergency contact number
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("7024084997", null, "Emergency SMS LOCATION : https://www.google.com/maps?q="+userLocation, null, null);
            Toast.makeText(context, "Emergency SMS sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            // Handle SMS sending failure
            Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public static void handleLocationData(Context context, double latitude, double longitude) {
        String userLocation =  latitude + "," + longitude;

        // Call the sendSMS() method to send the location data via SMS
        sendSMS(context, userLocation);
    }

}
