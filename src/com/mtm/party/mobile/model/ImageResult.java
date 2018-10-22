package com.mtm.party.mobile.model;

/**
 * <pre>
 *     author : Wp
 *     e-mail : 18141924293@163.com
 *     time   : 2018/09/19
 *     desc   : 图片结果
 *     version: 1.0
 * </pre>
 */
public class ImageResult {

	private String name;
	private String url;
	private String desc;
	private String extendOne;

	

	public ImageResult(String name, String url, String desc, String extendOne) {
		super();
		this.name = name;
		this.url = url;
		this.desc = desc;
		this.extendOne = extendOne;
	}


	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getExtendOne() {
		return extendOne;
	}

	public void setExtendOne(String extendOne) {
		this.extendOne = extendOne;
	}

	@Override
	public String toString() {
		return "ImageResult [desc=" + desc + ", extendOne=" + extendOne
				+ ", name=" + name + ", url=" + url + "]";
	}
	
	

}
