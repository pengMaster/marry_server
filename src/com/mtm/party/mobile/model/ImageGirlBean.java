package com.mtm.party.mobile.model;

public class ImageGirlBean {
	
	private String id;
	private String name;
	private String imageUrl;
	
	
	
	public ImageGirlBean(String id, String name, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "ImageGirlBean [id=" + id + ", imageUrl=" + imageUrl + ", name="
				+ name + "]";
	}
	
	

}
