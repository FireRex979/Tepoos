package com.example.teepos.datasignup;

import com.google.gson.annotations.SerializedName;

public class ErorLogin{

    @SerializedName("message")
    private String message;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}