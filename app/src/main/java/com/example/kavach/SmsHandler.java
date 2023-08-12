package com.example.kavach;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class SmsHandler {

    public static void sendSMS(Context context, String message, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(context);
                List<ContactInfo> allContacts = dbHelper.getAllContacts();

                if (allContacts.isEmpty()) {
                    Toast.makeText(context, "No Emergency Contacts Selected", Toast.LENGTH_SHORT).show();
                } else {
                    for (ContactInfo contact : allContacts) {
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
                            Toast.makeText(context, "Emergency SMS sent!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 1);
            }
        }
    }
}

