package com.example.kavach;


import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

//This class will be Used for sending SMS to all the emergency contacts
public class SmsHandler {

    //Function to send SMS
    public static void sendSMS(Context context, String message){

        //Extracting Contracts from Local Database
        ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(context);
        List<ContactInfo> allContacts = dbHelper.getAllContacts();

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
