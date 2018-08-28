package com.mtm.party.mobile.ctrl;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.mtm2000.common.domain.entity.User;

import com.google.gson.Gson;
import com.mtm.party.mobile.model.typeBean;
import com.mtm.party.util.wxLogin.SNSUserInfo;
import com.mtm.party.util.wxLogin.WeiXinUtil;
import com.mtm.party.util.wxLogin.WeixinOauth2Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * 中文参数接收方式:URLDecoder.decode(request.getParameter("body"), "UTF-8");
 * 
 * 微信登陆对接 - 目前正在审核中
 */
@Controller
@RequestMapping(value = "/index")
public class loginController {
    //返回微信二维码，可供扫描登录
    @RequestMapping(value = "/weixin")
    @ResponseBody
    public String weixin(HttpServletRequest request) throws IOException {
    	Gson gson  = new Gson();
        Map<String,Object> map = new HashMap<String,Object>();
        WeiXinUtil wxU = new WeiXinUtil();
        //http://www.shike.com.cn/index/WeiXinTest是手机用户扫码后会执行的后台方法
        //www.shike.com.cn此域名要与微信公共平台配置的一致
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=wxc028256ea6d51af1&" +
                "redirect_uri=http://localhost:8080/party/index/WeiXinTest&" +
                "response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
        //将url转换成短链接，提高扫码速度跟成功率
        String shorturl=wxU.shortURL(url, wxU.appid, wxU.appSecret);
        map.put("shorturl", shorturl);
        return gson.toJson(map);
    }

    int type = 0;
    String type2 = "";
    //判断用户是否扫码登录成功，以便于前台页面跳转
    @RequestMapping(value = "/successDL")
    @ResponseBody
    public String successDL(String a,HttpServletRequest req){
    	Gson gson  = new Gson();
        typeBean typeb = new typeBean();
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if("ok".equals(a)){
            type=1;
        }
        if(type==1){
        	typeb.setUserId(type2);
        }
    	typeb.setType(type+"");
        return gson.toJson(typeb);
    }

    @RequestMapping(value = "/type")
    public String type(int a){
    	ModelAndView mav = new ModelAndView();
    	Gson gson  = new Gson();
        type=a;   
        return gson.toJson(mav);
    }

    //微信获取用户信息
    @RequestMapping(value = "/WeiXinTest")
    public String WeiXinTest(ModelMap map, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	Gson gson  = new Gson();
        ModelAndView mav = new ModelAndView();
        response.setCharacterEncoding("utf-8");
        String code = request.getParameter("code");
     // 通过code获取access_token
        WeixinOauth2Token oauth2Token;
            oauth2Token = WeiXinUtil.getOauth2AccessToken(WeiXinUtil.appid, WeiXinUtil.appSecret, code);
        System.out.println(oauth2Token.toString());
           String accessToken=oauth2Token.getAccessToken();
           String openId=oauth2Token.getOpenId();
           //获取到用户的基本信息
           SNSUserInfo snsUserInfo=null;
           if(type==0){
              snsUserInfo = WeiXinUtil.getSNSUserInfo(accessToken, openId); 
           }
//         aa(map,request);
           if(snsUserInfo!=null){
               type=1;
           String id = snsUserInfo.getOpenId();//微信用户id
           String name = snsUserInfo.getNickname();//微信用户Name
           String logo = snsUserInfo.getHeadImgUrl();//微信用户头像
           type2=id;
/*--------------------登录逻辑请写这-------------------*/
//         查询库中是否有user

           mav.setViewName("info");
        }else{
            mav.setViewName("info2");
        }
        return gson.toJson(mav);
    }
    
    //扫码登录成功后，将用户信息放入session中，并且跳转页面
    @RequestMapping(value = "/fangSession")
    public String fangSession(HttpServletRequest request,String userId,String h,String chapterId){
        ModelAndView mav = new ModelAndView();
    	Gson gson  = new Gson();
        HttpSession session = request.getSession();
           User sessionuser=new User();
           sessionuser.setId(userId);
/*----------建议存完整的user，此时session中的user只有Id---------------*/
           session.setAttribute("login_user", sessionuser);
           User uu = (User)session.getAttribute("login_user");
           mav.setViewName("/front/personalCenter");  
           return gson.toJson(mav);
    }

}
