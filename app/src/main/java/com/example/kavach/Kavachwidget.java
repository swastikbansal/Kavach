package com.example.kavach;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.content.Intent;
import android.widget.Toast;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.location.Location;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.kavach.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class Kavachwidget extends AppWidgetProvider {

    private ImageButton SOSButton;
    private Button EmergencyContactsButton;
    private Button VoiceRecognitionButton;
    private Button LockScreenButton;
    private static final int PERMISSION_REQUEST_CODE = 1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.kavachwidget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.kavachwidget);

            // Set up click intent for the widget's SOS button
            Intent intent = new Intent(context, Kavachwidget.class);
            intent.setAction("SOS_CLICKED");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,  PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.widgetSOSButton, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if ("SOS_CLICKED".equals(intent.getAction())) {
            // Trigger the SOS functionality here
            sendEmergencySOS(context);
        }
    }

    private void sendEmergencySOS(Context context) {
        LocationHandler locationHandler = new LocationHandler(context, new LocationHandler.LocationListener() {
            @Override
            public void onLocationChanged(double latitude, double longitude) {
                // Location update callback, you can use it if needed
            }

            @Override
            public void onLocationChanged(android.location.Location location) {
                // Location update callback, you can use it if needed
            }
        });

        // Request current location when SOS button is pressed
        locationHandler.getCurrentLocation();

        Toast.makeText(context, "Emergency SOS triggered!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, handle any necessary cleanup here
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

    }
}
