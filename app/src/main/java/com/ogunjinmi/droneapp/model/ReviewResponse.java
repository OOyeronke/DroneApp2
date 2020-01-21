package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class ReviewResponse {

    @SerializedName("hover")
    private String review;

    public ReviewResponse(String review) {
        this.review= review;

    }

//Retrieve the data using setter/getter methods//

    public String getText() {
        return review;
    }

    public void setText(String review) {
        this.review = review;
    }
}

