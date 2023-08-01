package com.example.kavach;


import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class SmsHandler{
    public void sendSms(String phoneNumber, String message) {
        // Check if the SMS permission is granted
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsHandler.
            // If permission is granted, send the SMS
            //SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            //Toast.makeText(activity, locationMessage, Toast.LENGTH_SHORT).show();

        } else {
            // Request SMS permission
            Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }




    String Location = "";

}