package com.example.teepos.datasignup.getnotification;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("tgl_komentar")
	private String tglKomentar;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_postingan")
	private int idPostingan;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	public void setTglKomentar(String tglKomentar){
		this.tglKomentar = tglKomentar;
	}

	public String getTglKomentar(){
		return tglKomentar;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIdPostingan(int idPostingan){
		this.idPostingan = idPostingan;
	}

	public int getIdPostingan(){
		return idPostingan;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}
}