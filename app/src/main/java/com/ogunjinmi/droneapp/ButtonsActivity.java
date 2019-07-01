package com.ogunjinmi.droneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ButtonsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        Button Btn5 = (Button) findViewById(R.id.Speech);
        Btn5.setOnClickListener(this);
        Button Btn6 = (Button) findViewById(R.id.Teleoperation);
        Btn6.setOnClickListener(this);
        Button Btn4 = (Button) findViewById(R.id.Main_Menu);
        Btn4.setOnClickListener(this);

           }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Teleoperation: {
                Intent intent = new Intent(this, TeleOperationActivity.class);
                startActivity(intent);
            }

            break;
            case R.id.Speech: {
                Intent intent = new Intent(this, SpeechActivity.class);
                startActivity(intent);
            }

            break;
            case R.id.Main_Menu: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            break;
        }
    }
}
