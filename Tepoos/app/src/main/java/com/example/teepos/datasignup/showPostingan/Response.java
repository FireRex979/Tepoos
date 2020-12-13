package com.example.teepos.datasignup.showPostingan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("postingan")
	private Postingan postingan;

	@SerializedName("komentar")
	private List<KomentarItem> komentar;

	public void setPostingan(Postingan postingan){
		this.postingan = postingan;
	}

	public Postingan getPostingan(){
		return postingan;
	}

	public void setKomentar(List<KomentarItem> komentar){
		this.komentar = komentar;
	}

	public List<KomentarItem> getKomentar(){
		return komentar;
	}
}