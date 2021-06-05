package com.example.suraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Handler handler = new Handler();
        if(currentUser!=null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(in);
                    SplashScreen.this.finish();
                }
            }, 2500);
        }
        else
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(in);
                    SplashScreen.this.finish();
                }
            }, 2500);
        }

    }


}
