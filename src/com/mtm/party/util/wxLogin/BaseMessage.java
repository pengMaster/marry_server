package com.mtm.party.util.wxLogin;

/**
 * 响应消息基类（公众号发给用户）
 * @author Administrator
 *
 */
public class BaseMessage {

	//接收方账号（收到的OpenID）
	private String ToUserName;
	
	//开发者微信号（公众号）
	private String FromUserName;
	
	//消息创建时间(整形)
	private long CreateTime;
	
	//消息类型（text，image,location,link,voice）
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
