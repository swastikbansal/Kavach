package com.example.kavach;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kavach.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationHandler.LocationListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final int PERMISSION_REQUEST_CODE = 1;

    private CameraHandler cameraHandler;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "App is Still Work in Progress.", Toast.LENGTH_SHORT).show();

        dailogBoxEmptyContacts();

        cameraHandler = new CameraHandler(this,this);

        //Navigation Layout Code
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_manual, R.id.nav_tutorial, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        // ---------------------------------- Permissions ------------------------------------
        // Define the list of permissions to be requested
        String[] permissions = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        // Checking if all permissions are granted
        if (checkPermissions(permissions)) {
            assert true;
        }
        else {
            // Some permissions are not granted, request them
            requestPermissions(permissions);
        }
    }

    //Checking for permissions
    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //Requesting Permissions
    private void requestPermissions(String[] permissions) {
        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Explained the need of our permission
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("Please grant all the permission so that the app can run properly")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[] {permission}, PERMISSION_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                        .create().show();
            } else {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }

    //Function for computing Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;

            // Check if all permissions are granted
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
               Toast.makeText(this, "Permission Denied. App won't work properly.", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    //------------------------------------- Function For Opening Activities -------------------------------------------

    //Emergency Contacts Activity
    public void EmergencyContactsActivity(View view){
        //Checking for contact permission
        Intent contactIntent = new Intent(this,EmergencyContacts.class);
        startActivity(contactIntent);
    }

    //Voice Recognition Activity
    public void VoiceRecogntionActivity(View view){
        Intent contactIntent = new Intent(this,VoiceRecognition.class);
        startActivity(contactIntent);
    }

    //Lock Screen Activity
    public void LockScreenActivity(View view){
        Intent contactIntent = new Intent(this,LockScreen.class);
        startActivity(contactIntent);
    }

    //This Function will be executed when the button is pressed to send the SOS to selected emergency contacts
    public void SOS(View view){
        dailogBoxEmptyContacts();
        LocationHandler locationHandler = new LocationHandler(this, this);
        Toast.makeText(this, "SOS", Toast.LENGTH_SHORT).show();
        // Request current location when SOS button is pressed
        locationHandler.getCurrentLocation();

        cameraHandler.capturePhoto(MainActivity.this);
    }


    //Function for popping up Dialog box if Emergency COntacts are not added
    public void dailogBoxEmptyContacts(){
        //If user has no contacts added
        ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(MainActivity.this);
        List<ContactInfo> allContacts = dbHelper.getAllContacts();

        if (allContacts.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Emergency Contacts")
                    .setMessage("Please add Emergency Contacts before sending an SOS")
                    .setPositiveButton("ok", (dialog, which) -> {
                        Intent contactIntent = new Intent(MainActivity.this,EmergencyContacts.class);
                        startActivity(contactIntent);
                    })
                    .create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onLocationChanged(double latitude, double longitude) {

    }

    // Implementing LocationListener interface method to get the current location
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

    }

}