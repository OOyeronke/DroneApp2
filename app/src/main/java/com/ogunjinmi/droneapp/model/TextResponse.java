package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

    public class TextResponse {

        @SerializedName("text")
    private String text;

    public TextResponse(String text) {
        this.text = text;

    }

//Retrieve the data using setter/getter methods//

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
