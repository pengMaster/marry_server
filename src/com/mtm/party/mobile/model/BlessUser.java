package com.mtm.party.mobile.model;

public class BlessUser {
	
	private String id;
	private String create_time;
	private String open_id; 
	private String nick_image;
	private String nick_name;
	private String user_id;
	
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String openId) {
		open_id = openId;
	}
	public String getNick_image() {
		return nick_image;
	}
	public void setNick_image(String nickImage) {
		nick_image = nickImage;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nickName) {
		nick_name = nickName;
	}

	

}
