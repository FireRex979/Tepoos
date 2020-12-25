package com.example.teepos.datasignup;

public class User{
	private String kelamin;
	private String updatedAt;
	private String name;
	private String createdAt;
	private Object emailVerifiedAt;
	private int id;
	private String fotoProfile;
	private String email;
	private String tglLahir;

	public void setKelamin(String kelamin){
		this.kelamin = kelamin;
	}

	public String getKelamin(){
		return kelamin;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFotoProfile(String fotoProfile){
		this.fotoProfile = fotoProfile;
	}

	public String getFotoProfile(){
		return fotoProfile;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTglLahir(String tglLahir){
		this.tglLahir = tglLahir;
	}

	public String getTglLahir(){
		return tglLahir;
	}
}
