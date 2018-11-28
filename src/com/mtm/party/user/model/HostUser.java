package com.mtm.party.user.model;


/**
 * 注册用户表实体
 * TUnit entity. @author MyEclipse Persistence Tools
 */

public class HostUser implements java.io.Serializable {

	private String  id;//用户id
	private String  openId;
	private String  avatarUrl;
	private String  city;
	private String  nickName;
	private String  province;
	private String  createTime;
	private String  updateTime;
	private String  isOriginal;// 0 否 1 是
	private String  userPhone;
	private String  userWechat;
	
	
	
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserWechat() {
		return userWechat;
	}
	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}
	
	public String getIsOriginal() {
		return isOriginal;
	}
	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	
	

	

}