package com.example.teepos.datasignup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseSignUp implements Serializable {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
