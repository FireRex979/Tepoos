package com.example.teepos.datasignup;

import com.google.gson.annotations.SerializedName;

public class ReadNotif{

	@SerializedName("success")
	private boolean success;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}
}