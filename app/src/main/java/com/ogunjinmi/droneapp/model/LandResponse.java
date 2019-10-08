package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class LandResponse {

    @SerializedName("land")
    private String land;

    public LandResponse(String land) {
        this.land = land;

    }

//Retrieve the data using setter/getter methods//

    public String getText() {
        return land;
    }

    public void setText(String land) {
        this.land= land;
    }
}

