package com.example.kavach;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

//This class will be Used for sending SMS to all the emergency contacts
public class SmsHandler {


    //Function to send SMS
    public static void sendSMS(Context context, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If Permission is Given
            if (ActivityCompat.checkSelfPermission((Activity) context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                //Extracting Contracts from Local Database
                ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(context);
                List<ContactInfo> allContacts = dbHelper.getAllContacts();

                //If user has selected no emergency Contacts
                if (allContacts.isEmpty()) {
                    Toast.makeText(context, "No Emeregency Contacts Selected", Toast.LENGTH_SHORT).show();
                } else {
                    //Loop for sending SMS to all the emergency contacts
                    for (ContactInfo contact : allContacts) {

                        // Try Exception Block for sending SMS
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
                            Toast.makeText(context, "Emergency SMS sent!", Toast.LENGTH_SHORT).show();
                        }

                        // Handle SMS sending failure
                        catch (Exception ex) {
                            Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    }
                }
            }
        //If permission is not given
        else{
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 1);
            }

        }

    }
}
