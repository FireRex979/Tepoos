package com.example.teepos.datasignup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseLogin implements Serializable {

    @SerializedName("access_token")
    private String access_token;

    public String getToken() {
        return access_token;
    }
}
