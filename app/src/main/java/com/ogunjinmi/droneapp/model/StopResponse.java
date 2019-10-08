package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class StopResponse {

    @SerializedName("stop")
    private String stop;

    public StopResponse(String stop) {
        this.stop = stop;

    }

//Retrieve the data using setter/getter methods//

    public String getText() {
        return stop;
    }

    public void setText(String stop) {
        this.stop = stop;
    }
}

