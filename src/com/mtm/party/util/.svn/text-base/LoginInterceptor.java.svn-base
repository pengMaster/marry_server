package com.mtm.party.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mtm.party.user.model.User;




public class LoginInterceptor implements HandlerInterceptor{
	private static final String[] IGNORE_URI = {"/user/"};
	private static final String[] INTERCEPTOR_URI = {"/user/loginSuccess.do"};
	/** 
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中 
     */ 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    boolean flag = false;
        String url = request.getRequestURL().toString();
        System.out.println(">>>: " + url);
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if(flag){
        	for (String s : INTERCEPTOR_URI) {
        		if (url.contains(s)) {
        			flag = false;
        			break;
        		}
        	}
        }
        if (!flag) {
        	HttpSession session = request.getSession(true);
        	User user = (User) session.getAttribute("auditUser");
    		if (user != null){
    	        	flag = true;
	        }else{
	        	//response.sendRedirect(request.getContextPath()+"/unit/goLogin.do");  
	        	PrintWriter out = response.getWriter();  
	            out.println("<html>");      
	            out.println("<script>");      
	            out.println("window.open ('"+request.getContextPath()+"/user/goLogin.do','_top')");      
	            out.println("</script>");      
	            out.println("</html>");   
	        }
        }
      //  System.out.println(flag);
        return flag;
	}
	/** 
     * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在 
     * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。 
     */  
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/** 
     * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放 
     */ 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
