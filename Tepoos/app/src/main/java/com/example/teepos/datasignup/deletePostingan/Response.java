package com.example.teepos.datasignup.deletePostingan;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("success")
	private boolean success;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}
}