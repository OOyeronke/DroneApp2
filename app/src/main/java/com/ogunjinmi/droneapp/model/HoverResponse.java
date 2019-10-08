package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class HoverResponse {

    @SerializedName("hover")
        private String hover;

        public HoverResponse(String hover) {
            this.hover = hover;

        }

//Retrieve the data using setter/getter methods//

        public String getText() {
            return hover;
        }

        public void setText(String hover) {
            this.hover = hover;
        }
}
