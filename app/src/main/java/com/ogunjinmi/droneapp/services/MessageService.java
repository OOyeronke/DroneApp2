package com.ogunjinmi.droneapp.services;

import com.ogunjinmi.droneapp.model.DroneRequest;
import com.ogunjinmi.droneapp.model.GoLeftResponse;
import com.ogunjinmi.droneapp.model.GoRightResponse;
import com.ogunjinmi.droneapp.model.HoverResponse;
import com.ogunjinmi.droneapp.model.LandResponse;
import com.ogunjinmi.droneapp.model.ReviewResponse;
import com.ogunjinmi.droneapp.model.SpeechResponse;
import com.ogunjinmi.droneapp.model.StartResponse;
import com.ogunjinmi.droneapp.model.Stop2Response;
import com.ogunjinmi.droneapp.model.StopResponse;
import com.ogunjinmi.droneapp.model.TextResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MessageService {

    @POST("/command")
    Call<LandResponse> sendCommand(DroneRequest droneRequest);

    @GET("/text")
    Call<TextResponse> getText();

    @GET("/stop")
    Call<StopResponse> getStop();

    @GET("/hover")
    Call<HoverResponse> getHover();

    @GET("/goLeft")
    Call<GoLeftResponse> getGoLeft();

    @GET("/goRight")
    Call<GoRightResponse> getGoRight();

    @GET("/land")
    Call<LandResponse> getLand();

    @GET("/Speech")
    Call<SpeechResponse> getSpeech();

    @GET("/Start")
    Call<StartResponse> getStart();

    @GET("/Stop2")
    Call<Stop2Response> getStop2();

    @GET("/Review")
    Call<ReviewResponse> getReview();
}
