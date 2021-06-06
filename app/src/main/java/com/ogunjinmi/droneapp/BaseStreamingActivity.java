package com.ogunjinmi.droneapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
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
import com.ogunjinmi.droneapp.utils.Utilities;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener;
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage;
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ogunjinmi.droneapp.utils.Constants.COMMAND_HUB_URL;
import static com.ogunjinmi.droneapp.utils.Constants.DEVICE_HUB_URL;

public abstract class BaseStreamingActivity extends AppCompatActivity {

    protected Bitmap bitmap;
    protected ImageView imageView;
    HubConnection commandConnection;
    HubConnection connection;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState, @Nullable @org.jetbrains.annotations.Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        startSignalR();
        startSignalRForCommands();
    }


    public void makeRequest(DroneRequest droneRequest) {
        MessageService messageService = ServiceBuilder.getRetrofitInstance().create(MessageService.class);
        Call<LandResponse> land = messageService.sendCommand(droneRequest.getCommand());

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

    private void startSignalR() {
        connection = new WebSocketHubConnectionP2(
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

    public void stopConnection(){
        connection.disconnect();
        commandConnection.disconnect();
    }

    private void startSignalRForCommands() {
        commandConnection = new WebSocketHubConnectionP2(
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
