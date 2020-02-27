package com.ogunjinmi.droneapp.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class DroneRequest {
    private String item;
    private String command;

    public DroneRequest(String item, String command) {
        this.item = item;
        this.command = command;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
