package com.sampra.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AllModel{

	@SerializedName("pageNumber")
	private String pageNumber;

	@SerializedName("records")
	private List<RecordsItem> records;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setPageNumber(String pageNumber){
		this.pageNumber = pageNumber;
	}

	public String getPageNumber(){
		return pageNumber;
	}

	public void setRecords(List<RecordsItem> records){
		this.records = records;
	}

	public List<RecordsItem> getRecords(){
		return records;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AllModel{" + 
			"pageNumber = '" + pageNumber + '\'' + 
			",records = '" + records + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}