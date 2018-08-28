package com.mtm.party.util.wxLogin;


/**
 * 网页授权信息
 * @author Administrator
 *
 */
public class WeixinOauth2Token {
	
	//网页授权接口调用凭证
	private  String accessToken;
	
	//凭证有效时长
	private int expiresIn;
	
	//用于刷新凭证
	private String refreshToken;
	
	//用户标识
	private String openId;
	
	//用户授权作用域（当scope=snsapi_base时，不弹出授权页面,直接跳转 只能获取到openId,当scope=snsapi=userinfo时弹出授权页面,获取用户信息）
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
	
	

}
