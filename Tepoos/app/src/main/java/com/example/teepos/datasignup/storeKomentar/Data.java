package com.example.teepos.datasignup.storeKomentar;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("komentar")
	private String komentar;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("id_postingan")
	private int idPostingan;

	@SerializedName("user")
	private User user;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setKomentar(String komentar){
		this.komentar = komentar;
	}

	public String getKomentar(){
		return komentar;
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

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setIdPostingan(int idPostingan){
		this.idPostingan = idPostingan;
	}

	public int getIdPostingan(){
		return idPostingan;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}
}