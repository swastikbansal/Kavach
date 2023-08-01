package com.example.kavach;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsHandler {

    private static final int PERMISSION_REQUEST_SMS = 101;
    private static final int PERMISSION_REQUEST_LOCATION = 102;

    private Activity activity;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public SmsHandler(Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // You can get the current location here and send the SMS with the location details.
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String locationMessage = "Latitude: " + latitude + ", Longitude: " + longitude;
                Toast.makeText(activity, locationMessage, Toast.LENGTH_SHORT).show();
                Log.d("SMS", locationMessage);
                //sendSms("9424851954", locationMessage); // Replace "1234567890" with the desired phone number.
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    public void sendSms(String phoneNumber, String message) {
        // Check if the SMS permission is granted
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted, send the SMS
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        } else {
            // Request SMS permission
            Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdates() {
        try {
            // Request location updates with desired criteria
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // Stop receiving location updates when the activity is destroyed
    public void stopLocationUpdates() {
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}

