package com.mtm.party.mobile.model;

public class ImageList {
	
	private String id;
	private String imgUrl;
	private String type;
	//vertical 纵向  horizontal
	private String orientation;
	
	
	
	public ImageList(String id, String imgUrl, String type, String orientation) {
		super();
		this.id = id;
		this.imgUrl = imgUrl;
		this.type = type;
		this.orientation = orientation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	
	

}
