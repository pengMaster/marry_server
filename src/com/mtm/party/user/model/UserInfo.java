package com.mtm.party.user.model;

import java.util.Date;

/**
 * 注册用户表实体
 * TUnit entity. @author MyEclipse Persistence Tools
 */

public class UserInfo implements java.io.Serializable {
	
	private String  avatarUrl;
	private String  city;
	private String  nickName;
	private String  province;
	private int gender;
	private String  language;
	private String  country;
	
	
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
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

}