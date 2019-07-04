package com.mtm.party.mobile.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: WebContextFilter.java
 * @Module: web请求上下文
 * @Description: 设置跨域访问
 * 
 * @author: liuhoujie
 * @date: 2016年10月21日 上午10:38:12
 * 
 */
public class WebContextFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		//设置运行跨域访问
		HttpServletResponse response = (HttpServletResponse)arg1;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		response.setHeader("Access-Control-Allow-Headers", "Authentication,con");
		response.setHeader("Access-Control-Allow-Headers","Origin, method,X-Requested-With, Content-Type, Accept");
		//设置页面不缓存
//		response.addHeader("Pragma","no-cache");
//		response.setHeader("Cache-Control","no-cache");
//		response.setHeader("Expires","0");
		chain.doFilter(arg0, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
