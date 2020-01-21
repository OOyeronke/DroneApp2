package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class StartResponse {

    @SerializedName("start")
    private String start;

    public StartResponse(String start) {
        this.start = start;

    }

//Retrieve the data using setter/getter methods//

    public String getText() {
        return start;
    }

    public void setText(String start) {
        this.start = start;
    }
}

