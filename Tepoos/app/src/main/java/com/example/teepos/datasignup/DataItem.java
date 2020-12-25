package com.example.teepos.datasignup;

public class DataItem{
	private String foto;
	private String updatedAt;
	private int like;
	private String tglPostingan;
	private String caption;
	private String createdAt;
	private int id;
	private int idUser;
	private User user;

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

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}
}
