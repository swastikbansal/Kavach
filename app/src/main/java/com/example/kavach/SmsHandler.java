package com.example.kavach;


import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class SmsHandler {
    public static void sendSMS(Context context, String userLocation){

        ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(context);
        List<ContactInfo> allContacts = dbHelper.getAllContacts();

        for (ContactInfo contact : allContacts) {

            // Method to send SMS
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(contact.getNumber(), null, "Emergency SMS LOCATION : https://www.google.com/maps?q="+userLocation, null, null);
                Toast.makeText(context, "Emergency SMS sent!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex) {
                // Handle SMS sending failure
                Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        }

        }




    public static void handleLocationData(Context context, double latitude, double longitude) {
        String userLocation =  latitude + "," + longitude;

        // Call the sendSMS() method to send the location data via SMS
        sendSMS(context, userLocation);
    }

}
