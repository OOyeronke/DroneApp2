package com.ogunjinmi.droneapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class SplashedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splashed);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //load the xml file for the starting loading screen
        setContentView(R.layout.activity_splashed);

        Log.d("MainActivity:", "onCreate: created activity_main.xml UI succesfully.");

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(SplashedActivity.this, MainActivity.class));
                finish();

                Log.d("MainActivity:", "onCreate: waiting 5 seconds for MainActivity... loading PrimaryActivity.class");
            }
        }, 500 );
    }
}
