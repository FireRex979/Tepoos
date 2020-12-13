package com.example.teepos.datasignup;

import com.google.gson.annotations.SerializedName;

public class JsonMember0{

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("like")
	private int like;

	@SerializedName("tgl_postingan")
	private String tglPostingan;

	@SerializedName("caption")
	private String caption;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("id")
	private int id;

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setLike(int like){
		this.like = like;
	}

	public int getLike(){
		return like;
	}

	public void setTglPostingan(String tglPostingan){
		this.tglPostingan = tglPostingan;
	}

	public String getTglPostingan(){
		return tglPostingan;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}