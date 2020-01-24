package com.team.afeto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team.afeto.R;

public class SplashActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentUser != null){
                    startActivity(new Intent(getBaseContext(), Dashboard_area_logada.class));
                    finishAffinity();
                }else {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        }, 1500);
    }
}
