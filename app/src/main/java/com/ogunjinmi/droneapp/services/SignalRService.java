package com.ogunjinmi.droneapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.microsoft.signalr.Action;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.OnClosedCallback;
import com.ogunjinmi.droneapp.Utilities;
import com.ogunjinmi.droneapp.model.DroneRequest;


public class SignalRService extends Service {
    private HubConnection mHubConnection;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients

    public SignalRService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {
        mHubConnection.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }

    /**
     * method for clients (activities)
     */
    public void sendMessage(String message) {
        String SERVER_METHOD_SEND = "Command";
        mHubConnection.send(SERVER_METHOD_SEND, message);
    }

    private void startSignalR() {

        String serverUrl = Utilities.BASE_URL;

         mHubConnection = HubConnectionBuilder.create(serverUrl)

                .build();

        mHubConnection.onClosed(exception -> {

        });

        mHubConnection.on("Command", new Action() {
            @Override
            public void invoke() {
                System.out.println("New Message: Command");
            }
        });

        mHubConnection.start();
        mHubConnection.invoke(DroneRequest.class, "SendCommand", "");

        String HELLO_MSG = "Hello from Android!";
        sendMessage(HELLO_MSG);
    }
}