package com.example.kavach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LockScreen extends AppCompatActivity {
// Under Development

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
    }

    public void PIN(View view){
        Intent PinIntent = new Intent(this,LockScreen_PIN.class);
        startActivity(PinIntent);
    }
}