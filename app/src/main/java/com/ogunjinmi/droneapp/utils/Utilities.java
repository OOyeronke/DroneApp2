package com.ogunjinmi.droneapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utilities {
    public static final String BASE_URL = "https://deepbuzz-project.azurewebsites.net/";

    public static boolean verifyInputCommand(String inputCommand) {
        String[] commands = Constants.commands;
        for (String command : commands) {
            if (command.equalsIgnoreCase(inputCommand)) {
                return true;
            }
        }

        return false;
    }

    public static Bitmap convertFromBase64(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
