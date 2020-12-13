package com.example.teepos.datasignup;

import com.google.gson.annotations.SerializedName;

public class StorePostingan{

	@SerializedName("0")
	private JsonMember0 jsonMember0;

	@SerializedName("success")
	private boolean success;

	public void setJsonMember0(JsonMember0 jsonMember0){
		this.jsonMember0 = jsonMember0;
	}

	public JsonMember0 getJsonMember0(){
		return jsonMember0;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}
}