package com.example.teepos.datasignup;

import java.util.List;

public class Response{
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}
}