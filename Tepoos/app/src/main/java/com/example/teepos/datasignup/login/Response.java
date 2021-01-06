package com.example.teepos.datasignup.login;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("expires_at")
	private String expiresAt;

	@SerializedName("success")
	private boolean success;

	@SerializedName("token_type")
	private String tokenType;

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setExpiresAt(String expiresAt){
		this.expiresAt = expiresAt;
	}

	public String getExpiresAt(){
		return expiresAt;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setTokenType(String tokenType){
		this.tokenType = tokenType;
	}

	public String getTokenType(){
		return tokenType;
	}
}