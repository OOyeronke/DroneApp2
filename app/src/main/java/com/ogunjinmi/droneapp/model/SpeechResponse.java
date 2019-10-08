package com.ogunjinmi.droneapp.model;

import com.google.gson.annotations.SerializedName;

public class SpeechResponse {

        @SerializedName("speech")
        private String speech;

        public SpeechResponse(String speech) {
            this.speech = speech;

        }

//Retrieve the data using setter/getter methods//

        public String getText() {
            return speech;
        }

        public void setText(String speech) {
            this.speech = speech;
        }
}
