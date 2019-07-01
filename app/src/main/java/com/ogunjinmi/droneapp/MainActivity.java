package com.ogunjinmi.droneapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.ogunjinmi.droneapp.Utilities.verifyInputCommand;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_CODE_SPEECH_INPUT = 12;
    LinearLayout TeleOp, TeleOp1, TeleOp2;
    Button teleoperationButton, waypointButton, sensorButton, mainMenuButton;
    ImageButton imageButtonUp, imageButtonDown, imageButtonLeft, imageButtonRight;
    String inputCommand = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TeleOp = findViewById(R.id.TeleOperation);
        TeleOp1 = findViewById(R.id.TeleButton1);
        TeleOp2 = findViewById(R.id.TeleButton2);
        teleoperationButton = findViewById(R.id.Teleoperation);
        teleoperationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleOp.setVisibility(View.VISIBLE);
                TeleOp1.setVisibility(View.VISIBLE);
                TeleOp2.setVisibility(View.VISIBLE);
                imageButtonDown.setVisibility(View.VISIBLE);
                waypointButton.setText(getString(R.string.text));
                waypointButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Handle button functionality when text is "Text"
                        openTextInputLayout();
                    }
                });
                sensorButton.setText(getString(R.string.speech));
                sensorButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TODO: Handle button functionality when text is "Speech"
                        promptSpeechInput();
                    }
                });
            }
        });
        waypointButton = findViewById(R.id.Waypoint);
        waypointButton.setOnClickListener(this);
        sensorButton = findViewById(R.id.SensorData);
        sensorButton.setOnClickListener(this);
        mainMenuButton = findViewById(R.id.Main_Menu);
        mainMenuButton.setOnClickListener(this);
        imageButtonDown = findViewById(R.id.imageButtonDown);
        imageButtonDown.setOnClickListener(this);
        imageButtonLeft  = findViewById(R.id.imageButtonLeft);
        imageButtonLeft.setOnClickListener(this);
        imageButtonRight = findViewById(R.id.imageButtonRight);
        imageButtonRight.setOnClickListener(this);
        imageButtonUp= findViewById(R.id.imageButtonUp);
        imageButtonUp.setOnClickListener(this);
        TeleOp.setVisibility(View.GONE);
        TeleOp1.setVisibility(View.GONE);
        TeleOp2.setVisibility(View.GONE);
        imageButtonDown.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonDown: {

            }
            break;
            case R.id.Teleoperation: {
                //TODO show button controls for controlling the drone

                //startActivity(new Intent(MainActivity.this, TeleOperationActivity.class));
            }

            break;
            case R.id.Waypoint: {
                startActivity(new Intent(MainActivity.this, WaypointActivity.class));
            }
            break;
            case R.id.SensorData: {
                startActivity(new Intent(MainActivity.this, Sensor_DataActivity.class));
            }
            break;
            case R.id.Main_Menu: {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
            break;
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void openTextInputLayout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a Command for the drone");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputCommand = input.getText().toString();
                boolean verifyInputCommand = verifyInputCommand(inputCommand);
                if(verifyInputCommand){
                    Toast.makeText(MainActivity.this, "Command is valid.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                inputCommand = result.get(0);
                Log.e("MainActivity", "result is " + inputCommand);
                boolean verifyInputCommand = verifyInputCommand(inputCommand);
                if(verifyInputCommand){
                    Toast.makeText(this, "Command is valid.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}

