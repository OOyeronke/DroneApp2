package com.ogunjinmi.droneapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.ogunjinmi.droneapp.utils.Utilities;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection;
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener;
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage;
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2;

import static com.ogunjinmi.droneapp.utils.Constants.DEVICE_HUB_URL;

public class StreamingActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private ImageView imageView;
    private Button startStreaming, stopStreaming;

    private static String TAG = "StreamingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);

        final HubConnection connection =
                new WebSocketHubConnectionP2(
                        DEVICE_HUB_URL,
                        "Bearer your_token");

        imageView = findViewById(R.id.imageView);
        startStreaming = findViewById(R.id.StartStreamingBtn);
        stopStreaming = findViewById(R.id.StopStreamingBtn);

        connection.addListener(new HubConnectionListener() {
            @Override
            public void onConnected() {
                Log.e(TAG, "onConnected");
                runOnUiThread(()->{
                    startStreaming.setEnabled(true);
                });
            }

            @Override
            public void onDisconnected() {
                Log.e(TAG, "onDisconnected ");
                runOnUiThread(()->{
                    startStreaming.setEnabled(false);
                });
            }

            @Override
            public void onMessage(HubMessage message) {
                Log.e(TAG, "onMessage message is "+message.getTarget());
                handleStream(message);
            }

            @Override
            public void onError(Exception exception) {
                Log.e(TAG, "onError message is "+exception.getMessage());
            }
        });
        connection.subscribeToEvent("ImageStream", this::handleStream);
        connection.connect();
    }

    private void handleStream(HubMessage message) {
        JsonElement[] messageArguments = message.getArguments();
        JsonElement messageArgument = messageArguments[0];

        String imageString = messageArgument.getAsString();

        runOnUiThread(() -> {
            bitmap = Utilities.convertFromBase64(imageString);
            Log.e("Main1", "image1 is "+bitmap);
            imageView.setImageBitmap(bitmap);
        });

    }
}
