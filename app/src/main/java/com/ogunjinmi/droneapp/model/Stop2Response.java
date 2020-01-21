package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class Stop2Response {
   @SerializedName("stop2")
    private String stop2;

    public Stop2Response(String stop2) {
        this.stop2 = stop2;

    }

//Retrieve the data using setter/getter methods//

    public String getText() {
        return stop2;
    }

    public void setText(String stop2) {
        this.stop2 = stop2;
    }
}

