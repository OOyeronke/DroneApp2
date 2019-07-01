package com.ogunjinmi.droneapp;

public class Utilities {
    public static boolean verifyInputCommand(String inputCommand) {
        String[] commands = Constants.commands;
        for (String command : commands) {
            if (command.equalsIgnoreCase(inputCommand)) {
                return true;
            }
        }

        return false;
    }
}
