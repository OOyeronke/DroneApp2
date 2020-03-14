package com.ogunjinmi.droneapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.ogunjinmi.droneapp.model.Command;
import com.ogunjinmi.droneapp.model.DroneRequest;
import com.ogunjinmi.droneapp.model.ImageMessage;
import com.ogunjinmi.droneapp.model.LandResponse;
import com.ogunjinmi.droneapp.model.Location;
import com.ogunjinmi.droneapp.services.MessageService;
import com.ogunjinmi.droneapp.services.ServiceBuilder;
import com.ogunjinmi.droneapp.utils.Constants;
import com.ogunjinmi.droneapp.utils.Utilities;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener;
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage;
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ogunjinmi.droneapp.utils.Constants.COMMAND_HUB_URL;
import static com.ogunjinmi.droneapp.utils.Constants.DEVICE_HUB_URL;
import static com.ogunjinmi.droneapp.utils.Utilities.verifyInputCommand;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_CODE_SPEECH_INPUT = 12;
    LinearLayout TeleOp, TeleOp1, TeleOp2;
    Button teleoperationButton, datastreamingButton, mainMenuButton, stopButton, landButton, takeOffButton, startStreamingButton, stopStreamingButton, reviewStreamsButton;
    ImageButton imageButtonUp, imageButtonDown, imageButtonLeft, imageButtonRight;
    String inputCommand = "";
    private Bitmap bitmap;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSignalR();
        startSignalRForCommands();


        imageView = findViewById(R.id.imageView);

        TeleOp = findViewById(R.id.TeleOperation);
        TeleOp1 = findViewById(R.id.TeleButton1);
        TeleOp2 = findViewById(R.id.TeleButton2);
        teleoperationButton = findViewById(R.id.TeleOperationBtn);
        teleoperationButton.setOnClickListener(v -> {
            TeleOp.setVisibility(View.VISIBLE);
            TeleOp1.setVisibility(View.VISIBLE);
            TeleOp2.setVisibility(View.VISIBLE);
            imageButtonDown.setVisibility(View.VISIBLE);
            takeOffButton.setVisibility(View.VISIBLE);
            landButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
            startStreamingButton.setVisibility(View.GONE);
            stopStreamingButton.setVisibility(View.GONE);
            reviewStreamsButton.setVisibility(View.GONE);
            datastreamingButton.setText(getString(R.string.text));
            datastreamingButton.setOnClickListener(v1 -> {
                //TODO: Handle button functionality when text is "Text"
                openTextInputLayout();

            });
            //  sensorButton.setText(getString(R.string.speech));
            //sensorButton.setOnClickListener(new View.OnClickListener() {

            //  @Override
            // public void onClick(View v) {
            //TODO: Handle button functionality when text is "Speech"
            // promptSpeechInput();

            //             }
            //        });

        });


        datastreamingButton = findViewById(R.id.DataStreamingBtn);
        datastreamingButton.setOnClickListener(v -> {
            startStreamingButton.setVisibility(View.VISIBLE);
            startStreamingButton.setClickable(false);
            stopStreamingButton.setVisibility(View.VISIBLE);
            reviewStreamsButton.setVisibility(View.VISIBLE);
            TeleOp.setVisibility(View.GONE);
            TeleOp1.setVisibility(View.GONE);
            TeleOp2.setVisibility(View.GONE);
            imageButtonDown.setVisibility(View.GONE);
            takeOffButton.setVisibility(View.GONE);
            landButton.setVisibility(View.GONE);
            stopButton.setVisibility(View.GONE);
        });

        mainMenuButton = findViewById(R.id.MainMenuBtn);
        mainMenuButton.setOnClickListener(this);
        imageButtonDown = findViewById(R.id.DownBtn);
        imageButtonDown.setOnClickListener(this);
        imageButtonLeft = findViewById(R.id.LeftBtn);
        imageButtonLeft.setOnClickListener(this);
        imageButtonRight = findViewById(R.id.RightBtn);
        imageButtonRight.setOnClickListener(this);
        imageButtonUp = findViewById(R.id.UpBtn);
        imageButtonUp.setOnClickListener(this);
        stopButton = findViewById(R.id.StopBtn);
        stopButton.setOnClickListener(this);
        landButton = findViewById(R.id.LandBtn);
        landButton.setOnClickListener(this);
        takeOffButton = findViewById(R.id.TakeOffBtn);
        takeOffButton.setOnClickListener(this);
        startStreamingButton = findViewById(R.id.StartStreamingBtn);
        startStreamingButton.setOnClickListener(this);
        startStreamingButton.setClickable(true);
        stopStreamingButton = findViewById(R.id.StopStreamingBtn);
        stopStreamingButton.setOnClickListener(this);
        reviewStreamsButton = findViewById(R.id.ReviewBtn);
        reviewStreamsButton.setOnClickListener(this);
        TeleOp.setVisibility(View.GONE);
        TeleOp1.setVisibility(View.GONE);
        TeleOp2.setVisibility(View.GONE);
        imageButtonDown.setVisibility(View.GONE);
        stopButton.setVisibility(View.GONE);
        landButton.setVisibility(View.GONE);
        takeOffButton.setVisibility(View.GONE);
        startStreamingButton.setVisibility(View.GONE);
        stopStreamingButton.setVisibility(View.GONE);
        reviewStreamsButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.TeleOperationBtn: {
                //TODO show button controls for controlling the drone

                //startActivity(new Intent(MainActivity.this, TeleOperationActivity.class));
            }

            break;
            case R.id.LandBtn:
                doLand();
                break;

            case R.id.StopBtn:
                doStop();
                break;

            case R.id.TakeOffBtn:
                doStart();
                break;

            case R.id.UpBtn:
                doUp();
                break;

            case R.id.DownBtn:
                doDown();
                break;

            case R.id.LeftBtn:
                doLeft();
                break;

            case R.id.RightBtn:
                doRight();
                break;

            case R.id.StartStreamingBtn:
                doStartStreaming();
                break;

            case R.id.StopStreamingBtn:
                doStopStreaming();
                break;

            case R.id.ReviewBtn:
                doReviewStreams();
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

    private void doLand() {
        DroneRequest landDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.LAND_COMMAND);

        makeRequest(landDroneRequest);
    }

    public void makeRequest(DroneRequest droneRequest) {
        MessageService LandService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<LandResponse> land = LandService.getLand();

        String successMessage = getString(R.string.success_text_message,
                droneRequest.getCommand(), droneRequest.getItem());
        String failedMessage = getString(R.string.failure_text_message,
                droneRequest.getCommand(), droneRequest.getItem());

        land.enqueue(new Callback<LandResponse>() {
            @Override
            public void onResponse(Call<LandResponse> land, Response<LandResponse> response) {

                Log.e("main", "Request Sent Successfully");
                Snackbar.make(findViewById(R.id.videoControlsFrame),
                        successMessage,
                        Snackbar.LENGTH_LONG
                ).show();
            }

            @Override
            public void onFailure(Call<LandResponse> land, Throwable t) {
                Snackbar.make(findViewById(R.id.videoControlsFrame),
                        failedMessage,
                        Snackbar.LENGTH_LONG
                ).show();

            }
        });
    }

    private void doStop() {
        DroneRequest stopDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.STOP_COMMAND);

        makeRequest(stopDroneRequest);
        //sendCommand(stopDroneRequest.toString());
    }


    private void doStart() {
        DroneRequest startDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.START_COMMAND);

        makeRequest(startDroneRequest);
        //sendCommand(startDroneRequest.toString());
    }

    private void doUp() {
        DroneRequest upDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.UP_COMMAND);

        makeRequest(upDroneRequest);
        //sendCommand(upDroneRequest.toString());
    }

    private void doDown() {
        DroneRequest downDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.DOWN_COMMAND);

        makeRequest(downDroneRequest);
        //sendCommand(downDroneRequest.toString());
    }

    private void doLeft() {
        DroneRequest leftDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.LEFT_COMMAND);

        makeRequest(leftDroneRequest);
        //sendCommand(leftDroneRequest.toString());
    }

    private void doRight() {
        DroneRequest rightDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.RIGHT_COMMAND);

        makeRequest(rightDroneRequest);
        //sendCommand(rightDroneRequest.toString());
    }

    private void doStartStreaming() {
        DroneRequest startStreamingDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.START_COMMAND);

        makeRequest(startStreamingDroneRequest);
        //sendCommand(startStreamingDroneRequest.toString());
    }

    private void doStopStreaming() {
        DroneRequest stopStreamingDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.STOP_COMMAND);

        makeRequest(stopStreamingDroneRequest);
        //sendCommand(stopStreamingDroneRequest.toString());
    }

    private void doReviewStreams() {
        DroneRequest reviewStreamsDroneRequest = new DroneRequest(Constants.DRONE_ID, Constants.REVIEW_STREAMS_COMMAND);

        makeRequest(reviewStreamsDroneRequest);
        //sendCommand(reviewStreamsDroneRequest.toString());
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
        builder.setPositiveButton("OK", (dialog, which) -> {
            inputCommand = input.getText().toString();
            boolean verifyInputCommand = verifyInputCommand(inputCommand);
            if (verifyInputCommand) {
                Toast.makeText(MainActivity.this, "Command is valid.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Command invalid.", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

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
                if (verifyInputCommand) {
                    Toast.makeText(this, "Command is valid.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Command invalid.", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void startSignalR() {
        final HubConnection connection =
                new WebSocketHubConnectionP2(
                        DEVICE_HUB_URL,
                        "Bearer your_token");

        connection.addListener(new HubConnectionListener() {
            @Override
            public void onConnected() {
                Log.e("SPlash", "onConnected");
            }

            @Override
            public void onDisconnected() {
                Log.e("SPlash", "onDisconnected ");
            }

            @Override
            public void onMessage(HubMessage message) {
                Log.e("SPlash", "onMessage message is "+message.getTarget());
            }

            @Override
            public void onError(Exception exception) {
                Log.e("SPlash", "onError message is "+exception.getMessage());
            }
        });
        connection.subscribeToEvent("ImageStream", this::handleStream);
        connection.subscribeToEvent("ImageMessage", this::handleImages);
        connection.subscribeToEvent("LocationMessage", this::handleLocations);
        connection.connect();
    }

    private void startSignalRForCommands() {

        final HubConnection commandConnection =
                new WebSocketHubConnectionP2(
                        COMMAND_HUB_URL,
                        "Bearer your_token");

        commandConnection.addListener(new HubConnectionListener() {
            @Override
            public void onConnected() {
                Log.e("SPlash", "commandConnection onConnected");
            }

            @Override
            public void onDisconnected() {
                Log.e("SPlash", "onDisconnected ");
            }

            @Override
            public void onMessage(HubMessage message) {
                Log.e("SPlash", "onMessage message is "+message.getInvocationId());
                Log.e("SPlash", "onMessage message is "+message.toString());
                JsonElement[] messageArguments = message.getArguments();
                Log.e("SPlash", "onMessage message is "+ messageArguments[0]);

            }

            @Override
            public void onError(Exception exception) {
                Log.e("SPlash", "onError message is "+exception.getMessage());
            }
        });


        commandConnection.subscribeToEvent("SendCommand", this::handleCommand);

        commandConnection.connect();
    }

    private void handleCommand(HubMessage message) {
        JsonElement[] messageArguments = message.getArguments();
        JsonElement messageArgument = messageArguments[0];
        Gson gson = new GsonBuilder().create();
        Command command = gson.fromJson(messageArgument, Command.class);

        Log.e("SPlash", "subscribeToEvent command is "+ command.getCommand());
    }

    private void handleStream(HubMessage message) {
        JsonElement[] messageArguments = message.getArguments();
        JsonElement messageArgument = messageArguments[0];

        String imageString = messageArgument.getAsString();

        runOnUiThread(() -> {
            bitmap = Utilities.convertFromBase64(imageString);
            Log.e("Main", "image is "+bitmap);
            imageView.setImageBitmap(bitmap);
        });
    }

    private void handleImages(HubMessage message) {
        JsonElement[] messageArguments = message.getArguments();
        JsonElement messageArgument = messageArguments[0];

        Gson gson = new GsonBuilder().create();
        ImageMessage imageMessage = gson.fromJson(messageArgument, ImageMessage.class);
    }

    private void handleLocations(HubMessage hubMessage) {
        JsonElement[] messageArguments = hubMessage.getArguments();
        JsonElement messageArgument = messageArguments[0];
        Gson gson = new GsonBuilder().create();
        Location location = gson.fromJson(messageArgument, Location.class);
    }
}

