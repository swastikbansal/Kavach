package com.example.kavach;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.RunnableFuture;

public class Login extends AppCompatActivity {
    //This File handle the Intro Screen Animation of our application

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView name,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
                }
        }, 1500 );

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView2);
        name = findViewById(R.id.textView10);
        slogan = findViewById(R.id.textView9);

        image.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

    }
}
