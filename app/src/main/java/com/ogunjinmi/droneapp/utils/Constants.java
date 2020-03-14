package com.ogunjinmi.droneapp.utils;

public class Constants {

    public static String[] commands = {
      "takeoff",
      "land",
      "go right",
      "go left",
      "go up",
      "go down",
      "start",
      "stop"
    };

    public static final String DRONE_ID = "drone";
    public static final String VIDEO = "video";
    public static final String IMAGES = "camera";


    public static final String START_COMMAND = "start";
    public static final String STOP_COMMAND = "stop";
    public static final String LAND_COMMAND = "land";
    public static final String UP_COMMAND = "up";
    public static final String DOWN_COMMAND = "down";
    public static final String LEFT_COMMAND = "left";
    public static final String RIGHT_COMMAND = "right";
    public static final String START_STREAMING_COMMAND = "start_streaming";
    public static final String STOP_STREAMING_COMMAND = "stop_streaming";
    public static final String REVIEW_STREAMS_COMMAND = "review_streams";

    public static final String COMMAND_HUB_URL = "https://deepbuzz-project.azurewebsites.net/commandHub";
    public static final String DEVICE_HUB_URL = "https://deepbuzz-project.azurewebsites.net/deviceHub";



}
