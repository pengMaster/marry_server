package com.mtm.party.mobile.util;

import javax.servlet.http.HttpServletRequest;

import com.mtm.party.mobile.model.HttpHeaderInfoBean;


public class HttpHeaderUtils {
	private static final String IMEI = "imei";
	private static final String VERSION_CODE = "versionCode";
	private static final String VERSION_NAME = "versionName";
	private static final String SYSTEM_MODEL = "systemModel";
	private static final String SYSTEM_VERSION = "systemVersion";
	private static final String METHOD = "method";

	public static HttpHeaderInfoBean getHeaderInfos(HttpServletRequest request) {
		HttpHeaderInfoBean headerInfoFrom = new HttpHeaderInfoBean();

		headerInfoFrom.setImei(request.getHeader(IMEI));
		headerInfoFrom.setMethod(request.getHeader(METHOD));
		headerInfoFrom.setSystemModel(request.getHeader(SYSTEM_MODEL));
		headerInfoFrom.setSystemVersion(request.getHeader(SYSTEM_VERSION));
		headerInfoFrom.setVersionCode(request.getHeader(VERSION_CODE));
		headerInfoFrom.setVersionName(request.getHeader(VERSION_NAME));

		return headerInfoFrom;
	}
	public static HttpHeaderInfoBean getHeaderInfosTest(HttpServletRequest request) {
		HttpHeaderInfoBean headerInfoFrom = new HttpHeaderInfoBean();

		headerInfoFrom.setImei(request.getParameter(IMEI));
		headerInfoFrom.setMethod(request.getParameter(METHOD));
		headerInfoFrom.setSystemModel(request.getParameter(SYSTEM_MODEL));
		headerInfoFrom.setSystemVersion(request.getParameter(SYSTEM_VERSION));
		headerInfoFrom.setVersionCode(request.getParameter(VERSION_CODE));
		headerInfoFrom.setVersionName(request.getParameter(VERSION_NAME));

		return headerInfoFrom;
	}
}
