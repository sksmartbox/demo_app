package com.sampra.data.model;

import com.google.gson.annotations.SerializedName;

public class RecordsItem{

	@SerializedName("post_image")
	private String postImage;

	@SerializedName("post_id")
	private String postId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("post_link")
	private String postLink;

	@SerializedName("post_comment")
	private String postComment;

	@SerializedName("post_like")
	private String postLike;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("post_description")
	private String postDescription;

	@SerializedName("post_status")
	private String postStatus;

	@SerializedName("type")
	private int type;

	@SerializedName("post_time")
	private String postTime;

	public void setPostImage(String postImage){
		this.postImage = postImage;
	}

	public String getPostImage(){
		return postImage;
	}

	public void setPostId(String postId){
		this.postId = postId;
	}

	public String getPostId(){
		return postId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPostLink(String postLink){
		this.postLink = postLink;
	}

	public String getPostLink(){
		return postLink;
	}

	public void setPostComment(String postComment){
		this.postComment = postComment;
	}

	public String getPostComment(){
		return postComment;
	}

	public void setPostLike(String postLike){
		this.postLike = postLike;
	}

	public String getPostLike(){
		return postLike;
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

	public void setPostDescription(String postDescription){
		this.postDescription = postDescription;
	}

	public String getPostDescription(){
		return postDescription;
	}

	public void setPostStatus(String postStatus){
		this.postStatus = postStatus;
	}

	public String getPostStatus(){
		return postStatus;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setPostTime(String postTime){
		this.postTime = postTime;
	}

	public String getPostTime(){
		return postTime;
	}

	@Override
 	public String toString(){
		return 
			"RecordsItem{" + 
			"post_image = '" + postImage + '\'' + 
			",post_id = '" + postId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",post_link = '" + postLink + '\'' + 
			",post_comment = '" + postComment + '\'' + 
			",post_like = '" + postLike + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",post_description = '" + postDescription + '\'' + 
			",post_status = '" + postStatus + '\'' + 
			",type = '" + type + '\'' + 
			",post_time = '" + postTime + '\'' + 
			"}";
		}
}