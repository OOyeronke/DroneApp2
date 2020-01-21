package com.ogunjinmi.droneapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogunjinmi.droneapp.model.HoverResponse;
import com.ogunjinmi.droneapp.model.LandResponse;
import com.ogunjinmi.droneapp.model.ReviewResponse;
import com.ogunjinmi.droneapp.model.StartResponse;
import com.ogunjinmi.droneapp.model.Stop2Response;
import com.ogunjinmi.droneapp.model.StopResponse;
import com.ogunjinmi.droneapp.model.TextResponse;
import com.ogunjinmi.droneapp.services.MessageService;
import com.ogunjinmi.droneapp.services.ServiceBuilder;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ogunjinmi.droneapp.Utilities.verifyInputCommand;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_CODE_SPEECH_INPUT = 12;
    LinearLayout TeleOp, TeleOp1, TeleOp2;
    Button teleoperationButton, datastreamingButton, mainMenuButton, stopButton, landButton, takeOffButton, startButton, stop2Button, reviewButton;
    ImageButton imageButtonUp, imageButtonDown, imageButtonLeft, imageButtonRight;
    String inputCommand = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MessageService textService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<TextResponse> text = textService.getText();


        text.enqueue(new Callback<TextResponse>() {
            @Override
            public void onResponse(Call<TextResponse> text, Response<TextResponse> response) {
                //if(response.isSuccessful()){

                //}
                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<TextResponse> text, Throwable t) {
                ((Button) findViewById(R.id.DataStreamingBtn)).setText("Request Failed");

            }
        });

        MessageService LandService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<LandResponse> land = LandService.getLand();


        land.enqueue(new Callback<LandResponse>() {
            @Override
            public void onResponse(Call<LandResponse> land, Response<LandResponse> response) {

                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<LandResponse> land, Throwable t) {
                ((Button) findViewById(R.id.LandBtn)).setText("Request Failed");

            }
        });
        MessageService HoverService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<HoverResponse> hover = HoverService.getHover();


        hover.enqueue(new Callback<HoverResponse>() {
            @Override
            public void onResponse(Call<HoverResponse> hover, Response<HoverResponse> response) {

                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<HoverResponse> call, Throwable t) {
                ((Button) findViewById(R.id.TakeOffBtn)).setText("Request Failed");

            }
        });


        MessageService StopService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<StopResponse> stop = StopService.getStop();


        stop.enqueue(new Callback<StopResponse>() {
            @Override
            public void onResponse(Call<StopResponse> stop, Response<StopResponse> response) {
                //if(response.isSuccessful()){

                //}
                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<StopResponse> call, Throwable t) {
                ((TextView) findViewById(R.id.StopBtn)).setText("Request Failed");

            }
        });

        MessageService Stop2Service = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<Stop2Response> stop2 = Stop2Service.getStop2();


        stop2.enqueue(new Callback<Stop2Response>() {
            @Override
            public void onResponse(Call<Stop2Response> stop2, Response<Stop2Response> response) {
                //if(response.isSuccessful()){

                //}
                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<Stop2Response> call, Throwable t) {
                ((TextView) findViewById(R.id.StopStreamingBtn)).setText("Request Failed");

            }
        });

        MessageService StartService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<StartResponse> start = StartService.getStart();


        start.enqueue(new Callback<StartResponse>() {
            @Override
            public void onResponse(Call<StartResponse> start, Response<StartResponse> response) {
                //if(response.isSuccessful()){

                //}
                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<StartResponse> call, Throwable t) {
                ((TextView) findViewById(R.id.StartStreamingBtn)).setText("Request Failed");

            }
        });

        MessageService ReviewService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<ReviewResponse> review = ReviewService.getReview();


        review.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> review, Response<ReviewResponse> response) {
                //if(response.isSuccessful()){

                //}
                Log.e("main", "Request Sent Successfully");
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                ((TextView) findViewById(R.id.ReviewBtn)).setText("Request Failed");

            }
        });


        TeleOp = findViewById(R.id.TeleOperation);
        TeleOp1 = findViewById(R.id.TeleButton1);
        TeleOp2 = findViewById(R.id.TeleButton2);
        teleoperationButton = findViewById(R.id.TeleOperationBtn);
        teleoperationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleOp.setVisibility(View.VISIBLE);
                TeleOp1.setVisibility(View.VISIBLE);
                TeleOp2.setVisibility(View.VISIBLE);
                imageButtonDown.setVisibility(View.VISIBLE);
                datastreamingButton.setText(getString(R.string.text));
                datastreamingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Handle button functionality when text is "Text"
                        openTextInputLayout();

                    }
                });
              //  sensorButton.setText(getString(R.string.speech));
                //sensorButton.setOnClickListener(new View.OnClickListener() {

                    //  @Override
                   // public void onClick(View v) {
                        //TODO: Handle button functionality when text is "Speech"
                  // promptSpeechInput();

      //             }
        //        });

            }
        });


        datastreamingButton= findViewById(R.id.DataStreamingBtn);
        datastreamingButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startButton.setVisibility(View.VISIBLE);
            startButton.setClickable(false);
            stop2Button.setVisibility(View.VISIBLE);
            reviewButton.setVisibility(View.VISIBLE);
        }
              });

        mainMenuButton = findViewById(R.id.MainMenuBtn);
        mainMenuButton.setOnClickListener(this);
        imageButtonDown = findViewById(R.id.DownBtn);
        imageButtonDown.setOnClickListener(this);
        imageButtonLeft  = findViewById(R.id.LeftBtn);
        imageButtonLeft.setOnClickListener(this);
        imageButtonRight = findViewById(R.id.RightBtn);
        imageButtonRight.setOnClickListener(this);
        imageButtonUp= findViewById(R.id.UpBtn);
        imageButtonUp.setOnClickListener(this);
        stopButton= findViewById(R.id.StopBtn);
        stopButton.setOnClickListener(this);
        landButton= findViewById(R.id.LandBtn);
        landButton.setOnClickListener(this);
        takeOffButton= findViewById(R.id.TakeOffBtn);
        takeOffButton.setOnClickListener(this);
        startButton = findViewById(R.id.StartStreamingBtn);
        startButton.setOnClickListener(this);
        startButton.setClickable(true);
        stop2Button = findViewById(R.id.StopStreamingBtn);
        stop2Button.setOnClickListener(this);
        reviewButton = findViewById(R.id.ReviewBtn);
        reviewButton.setOnClickListener(this);
        TeleOp.setVisibility(View.GONE);
        TeleOp1.setVisibility(View.GONE);
        TeleOp2.setVisibility(View.GONE);
        imageButtonDown.setVisibility(View.GONE);
        stopButton.setVisibility(View.GONE);
        landButton.setVisibility(View.GONE);
        takeOffButton.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        stop2Button.setVisibility(View.GONE);
        reviewButton.setVisibility(View.GONE);
            }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DownBtn: {

            }
            break;
            case R.id.TeleOperationBtn: {
                //TODO show button controls for controlling the drone

                //startActivity(new Intent(MainActivity.this, TeleOperationActivity.class));
            }

            break;
            case R.id.DataStreamingBtn: {
                startActivity(new Intent(MainActivity.this, WaypointActivity.class));
            }

            break;
            case R.id.MainMenuBtn: {
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
                else {
                    Toast.makeText(MainActivity.this, "Command invalid.", Toast.LENGTH_LONG).show();
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
                if(verifyInputCommand) {
                    Toast.makeText(this, "Command is valid.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this,  "Command invalid.", Toast.LENGTH_LONG).show();
                    }

            }
        }

    }
}

