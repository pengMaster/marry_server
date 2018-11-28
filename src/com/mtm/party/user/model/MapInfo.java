package com.mtm.party.user.model;


/**
 * 注册用户表实体
 * TUnit entity. @author MyEclipse Persistence Tools
 */

public class MapInfo implements java.io.Serializable {

	private String  id;//用户id
	private String  userId;
	private String  createTime;
	private String  updateTime;
	private String  inviteName;
	private String  inviteDateOne;
	private String  inviteDateTwo;
	private String  inviteAddress; 
	private String  inviteLongitude; 
	private String  inviteLatitude; 
	private String  inviteBgUrl;
	private String  isOriginal;// 0 否 1 是
	
	
	
	
	
	public String getInviteBgUrl() {
		return inviteBgUrl;
	}
	public void setInviteBgUrl(String inviteBgUrl) {
		this.inviteBgUrl = inviteBgUrl;
	}
	public String getInviteName() {
		return inviteName;
	}
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}
	public String getInviteDateOne() {
		return inviteDateOne;
	}
	public void setInviteDateOne(String inviteDateOne) {
		this.inviteDateOne = inviteDateOne;
	}
	public String getInviteDateTwo() {
		return inviteDateTwo;
	}
	public void setInviteDateTwo(String inviteDateTwo) {
		this.inviteDateTwo = inviteDateTwo;
	}
	public String getInviteAddress() {
		return inviteAddress;
	}
	public void setInviteAddress(String inviteAddress) {
		this.inviteAddress = inviteAddress;
	}
	public String getInviteLongitude() {
		return inviteLongitude;
	}
	public void setInviteLongitude(String inviteLongitude) {
		this.inviteLongitude = inviteLongitude;
	}
	public String getInviteLatitude() {
		return inviteLatitude;
	}
	public void setInviteLatitude(String inviteLatitude) {
		this.inviteLatitude = inviteLatitude;
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

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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