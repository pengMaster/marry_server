package com.mtm.party.mobile.ctrl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mtm2000.common.hibernate.PageData;
import cn.mtm2000.common.util.DateUtil;
import cn.mtm2000.common.util.ValidUtil;

import com.google.gson.Gson;
import com.mtm.party.mobile.model.BlessComment;
import com.mtm.party.mobile.model.BlessUser;
import com.mtm.party.mobile.model.FeelingsMobileForm;
import com.mtm.party.mobile.model.HttpHeaderInfoBean;
import com.mtm.party.mobile.model.ImageList;
import com.mtm.party.mobile.model.NoticeMobileForm;
import com.mtm.party.mobile.service.MobileService;
import com.mtm.party.mobile.util.HttpHeaderUtils;
import com.mtm.party.user.model.User;
import com.mtm.party.user.service.UserService;
import com.mtm.party.util.Formats;
import com.mtm.party.util.HttpRequestor;
import com.mtm.party.util.MessageUtil;
import com.mtm.party.util.PayUtil;
import com.mtm.party.util.StringUtils;

/**
 * 中文参数接收方式:URLDecoder.decode(request.getParameter("body"), "UTF-8");
 * 
 * 
 * */

@Controller
@RequestMapping("/mobile")
public class MobileController {
	private final String SAVE_USER = "SAVE_USER";// 用户注册
	private final String GET_PAY = "GET_PAY";// 支付申请订单
	private final String SAVE_PAY = "SAVE_PAY";// 保存缴费记录
	private final String GET_PAY_RECORD = "GET_PAY_RECORD";// 获取缴费记录
	private final String GET_OPENID = "GET_OPENID";// 支付申请订单
	private final String LOGIN_IN = "LOGIN_IN";// 登录
	private final String UNBUNDLING = "UNBUNDLING";// 解绑用户
	private final String GET_NOTICE_LIST = "GET_NOTICE_LIST";// 获取公告、服务指南列表
	private final String SAVE_NOTICE_VIEW = "SAVE_NOTICE_VIEW";// 保存已查看公告、服务指南
	private final String GET_NOTICE = "GET_NOTICE";// 获取公告、服务指南
	private final String GET_REPOSITORY_DETAIL = "GET_REPOSITORY_DETAIL";// 获取知识库详情
	private final String GET_FEELING_DETAIL = "GET_FEELING_DETAIL";// 获取学习档案详情
	private final String GET_REPOSITORY_LIST = "GET_REPOSITORY_LIST";// 获取知识库列表
	private final String GET_FEELINGS_LIST = "GET_FEELINGS_LIST";// 获取心得（圈子）列表
	private final String GET_MY_FEELINGS_LIST = "GET_MY_FEELINGS_LIST";// 获取我的心得列表
	private final String SHOW_PRAISE_STATE = "SHOW_PRAISE_STATE";// 查看是否被点赞或者评论
	private final String SAVE_FEELINGS_VIEW = "SAVE_FEELINGS_VIEW";// 知识库、心得查看记录存储
	private final String SAVE_FEELINGS = "SAVE_FEELINGS";// 保存心得
	private final String SAVE_REPOSITORY = "SAVE_REPOSITORY";// 保存知识库
	private final String GET_MY_FEELINGS_COUNT = "GET_MY_FEELINGS_COUNT";// 获取点赞数、评论数、心得数
	private final String GIVE_ME_PRAISE_OR_COMMENT = "GIVE_ME_PRAISE_OR_COMMENT";// 获取被点赞、被评论列表
	private final String GET_MY_PRAISED_LIST = "GET_MY_PRAISED_LIST";// 获取我赞过的列表
	private final String GET_IMAGE = "GET_IMAGE";// 获取图片
	private final String GET_PRAISE = "GET_PRAISE";// 获取赞列表
	private final String SAVE_PRAISE = "SAVE_PRAISE";// 保存赞
	private final String GET_COMMENT = "GET_COMMENT";// 获取评论列表
	private final String SAVE_COMMENT = "SAVE_COMMENT";// 保存评论

	private JSONArray jsonArray = new JSONArray();
	@Resource
	private MobileService mobileService;
	@Resource
	private UserService userService;


	public MobileService getMobileService() {
		return mobileService;
	}

	public void setMobileService(MobileService mobileService) {
		this.mobileService = mobileService;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 接口方法总入口
	 * 
	 * @author wangsong
	 * @return
	 * @throws Exception
	 *
	 */
	//http://localhost:8080/party//mobile/mobileIn.do
	//http://localhost:8080/party/wechat/image/arrow_chart.png
	@RequestMapping("mobileIn")
	public String mobileIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
//		request.setCharacterEncoding("application/json; charset=UTF-8");
		HttpHeaderInfoBean headerInfoBean = HttpHeaderUtils
				.getHeaderInfos(request);
		if (ValidUtil.isEmpty(headerInfoBean.getMethod())) {
			headerInfoBean = HttpHeaderUtils.getHeaderInfosTest(request);
		}
		String method = headerInfoBean.getMethod();
		String json = "";
		try {
			request.setCharacterEncoding("UTF-8");
			if (SAVE_USER.equals(method)) {
				// 用户注册接口
				json = saveUser(request, response);
			}else if (GET_OPENID.equals(method)) {
				// 获取openiD接口
				json = getOpenID(request, response);
			} else if (LOGIN_IN.equals(method)) {
				// 用户登录方法
				json = loginin(request, response);
			}else if (GET_IMAGE.equals(method)) {
				//  获取图片
				json = getImages(request, response);
			}else if (GET_PRAISE.equals(method)) {
				//  获取赞列表
				json = getPraiseList(request, response);
			} else if (SAVE_PRAISE.equals(method)) {
				// 点赞
				json = savePraise(request, response);
			}else if (SAVE_COMMENT.equals(method)) {
				// 保存评论
				json = saveComment(request, response);
			}else if (GET_COMMENT.equals(method)) {
				// 获取评论列表
				json = getCommentList(request, response);
			} 
			else{
				json = "测试";
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 保存赞
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String savePraise(HttpServletRequest request,
			HttpServletResponse response) {
		String nickName = request.getParameter("nickName");
		String nickImage = request.getParameter("nickImage");
		String openId = request.getParameter("openId");
		String openId = request.getParameter("openId");
		try {
			List obj = mobileService.getBlessUserByNickImage(nickImage);
			if (null!=obj && obj.size()>0) {
				return "你已经点过赞了";
			}
			BlessUser blessUser = new BlessUser();
			blessUser.setNick_image(nickImage+"");
			blessUser.setNick_name(nickName+"");
			blessUser.setCreate_time(System.currentTimeMillis()+"");
			blessUser.setId(System.currentTimeMillis()+"");
			blessUser.setOpen_id(openId);
			mobileService.save(blessUser);
			return "点赞成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "点赞失败";
	}

	/**
	 * 保存评论
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String saveComment(HttpServletRequest request,
			HttpServletResponse response) {
		
		String nickName = request.getParameter("nickName");
		String nickImage = request.getParameter("nickImage");
		String comment = request.getParameter("comment");
		String time = request.getParameter("time");
		String openId = request.getParameter("openId");
		
		try {
			BlessComment blessComment = new BlessComment();
			blessComment.setNick_image(nickImage+"");
			blessComment.setNick_name(nickName+"");
			blessComment.setComment(comment+"");
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分"); 
			blessComment.setCreate_time(df.format(new Date()));
			blessComment.setId(System.currentTimeMillis()+"");
			mobileService.save(blessComment);
			return "评论成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "评论失败";
	}
	
	/**
	 * 获取赞列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String getPraiseList(HttpServletRequest request,
			HttpServletResponse response) {
		List object;
		try {
			 object = mobileService.getAllBlessUser();
				return new Gson().toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	/**
	 * 获取评论列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String getCommentList(HttpServletRequest request,
			HttpServletResponse response) {
		List object;
		try {
			 object = mobileService.getAllBlessComment();
				return new Gson().toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String getImages(HttpServletRequest request,
			HttpServletResponse response) {
		
		String type = request.getParameter("homeType");//banner detail
		String moduleId = request.getParameter("moduleId");

		List<ImageList> imageList = new ArrayList<ImageList>();
		String baseUrl = "";
		try {
		
			String path = "";
			if ("banner".equals(type)) {
				path = request.getSession().getServletContext().getRealPath("/wechat")+"/marry/banner/";
				baseUrl = "https://pengmaster.com/party/wechat/marry/banner/";
			}else {
				path = request.getSession().getServletContext().getRealPath("/wechat")+"/marry/"+moduleId+"/";
				baseUrl = "https://pengmaster.com/party/wechat"+"/marry/"+moduleId+"/";
			}
			System.out.println("path:"+path);
			File file = new File(path);
			File[] tempList = file.listFiles();
			if (null==tempList) {
				return "数据为空";
			}
			for(int i=0;i<tempList.length;i++){
				String name = tempList[i].getName();
				String orientation = "";
				File picture = new File(path+name);
		        BufferedImage sourceImg =ImageIO.read(new FileInputStream(picture)); 
		         if (sourceImg.getWidth()>sourceImg.getHeight()) {
		        	 orientation = "horizontal";
				}else {
					 orientation = "vertical";
				}
		         String nameType = "";
		         if (null!=name) {
		        	 nameType = name.substring(0,name.indexOf("."));
				}
				imageList.add(new ImageList(StringUtils.generateRefID()
						, baseUrl+name, nameType, orientation));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new Gson().toJson(imageList);
	}
 


	/**
	 * 用户注册
	 * 
	 * @author wangsong
	 * @return
	 */
	public String saveUser(HttpServletRequest request,
			HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User u = new User();
		String account = request.getParameter("account");// 用户名
		String pwd = request.getParameter("password");// 密码
		String name = "";
		try {
			name = URLDecoder.decode(request.getParameter("name"), "UTF-8");// 名字
		} catch (UnsupportedEncodingException e1) {
			System.out.println("error:saveUser,URLDecoder.decode异常");
		}
		String cardType = request.getParameter("cardType");// 证件类型
		String idcard = request.getParameter("idCard");// 身份证号
		String phone = request.getParameter("phone");// 手机号
		String branchCode = request.getParameter("branchCode");// 所属党支部编码
		String salary = request.getParameter("salary");// 月收入
		String address = request.getParameter("address");// 住址
		String joiningTime = request.getParameter("joiningTime");// 入党时间
		String sponsor = request.getParameter("sponsor");// 入党介绍人
		String openId = request.getParameter("openId");// 微信识别码
		String rank = request.getParameter("rank");// 用户权限，级别
		String startTime = request.getParameter("startTime");// 开始交党费日期
		String endTime = request.getParameter("endTime");// 党费交至日期
		// String updateTime=request.getParameter("account");//修改时间
		// String deleteTime=request.getParameter("account");//删除时间
		// Md5 md5 = new Md5(pwd);
		// md5.processString();
		// String password = md5.getStringDigest();
		User user = userService.getUserByIdCardAndPhone(idcard, phone);
		JSONObject JsonObject = new JSONObject();
		try {
			if (user == null) {
				JsonObject.put("result", "用户不存在！");
				JsonObject.put("success", "204");
			} else {
				if (ValidUtil.isNoEmpty(user.getOpenId())) {
					JsonObject.put("result", "该用户已关联！");
					JsonObject.put("success", "200");
					JsonObject.put("user", user);
				} else {
					if (ValidUtil.isNoEmpty(account)) {
						user.setAccount(account);
					}
					// if (ValidUtil.isNoEmpty(pwd)) {
					// u.setPassword(password);
					// }
					if (ValidUtil.isNoEmpty(name)
							&& !user.getName().equals(name)) {
						user.setName(name);
					}
					if (ValidUtil.isNoEmpty(cardType)) {
						user.setCardType(cardType);
					}
					if (ValidUtil.isNoEmpty(idcard)
							&& !user.getIdcard().equals(idcard)) {
						user.setIdcard(idcard);
					}
					if (ValidUtil.isNoEmpty(phone)
							&& !user.getPhone().equals(phone)) {
						user.setPhone(phone);
					}
					if (ValidUtil.isNoEmpty(branchCode)) {
						user.setBranchCode(branchCode);
					}
					if (ValidUtil.isNoEmpty(salary)) {
						user.setSalary(salary);
					}
					if (ValidUtil.isNoEmpty(address)) {
						user.setAddress(address);
					}
					if (ValidUtil.isNoEmpty(joiningTime)) {
						user.setJoiningTime(joiningTime);
					}
					if (ValidUtil.isNoEmpty(sponsor)) {
						user.setSponsor(sponsor);
					}
					if (ValidUtil.isNoEmpty(openId)) {
						user.setOpenId(openId);
					}
					if (ValidUtil.isNoEmpty(rank)) {
						user.setRank(rank);
					}
					if (ValidUtil.isNoEmpty(startTime)) {
						user.setStartTime(startTime);
					}
					if (ValidUtil.isNoEmpty(endTime)) {
						user.setEndTime(endTime);
					}
					user.setCreateTime(DateUtil.getCurrentDatetime());
					user.setIsDelete("0");
					userService.updateUser(user);
					JsonObject.put("result", "关联成功！");
					JsonObject.put("success", "201");
					JsonObject.put("user", user);
				}
			}
		} catch (Exception e) {
			JsonObject.put("result", "关联失败！");
			JsonObject.put("success", "203");
			e.printStackTrace();
		}
		return JsonObject.toString();
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String loginin(HttpServletRequest request,
			HttpServletResponse response) {
		String openId = request.getParameter("openId");
		User user = userService.getUserByOpenId(openId);
		JSONObject JsonObject = new JSONObject();
		if (ValidUtil.isNoEmpty(user)) {
			JsonObject.put("result", "该用户已关联！");
			JsonObject.put("success", "200");
			JsonObject.put("user", user);
		} else {
			JsonObject.put("result", "请关联用户！");
			JsonObject.put("success", "202");
		}
		return JsonObject.toString();
	}

	/**
	 * 解绑微信
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String unbundling(HttpServletRequest request,
			HttpServletResponse response) {
		String idCard = request.getParameter("idCard");
		JSONObject JsonObject = new JSONObject();
		User user = userService.getUserByuserIdCard(idCard);
		if (ValidUtil.isNoEmpty(user)) {
			if (ValidUtil.isNoEmpty(user.getOpenId())) {
				user.setOpenId("");
				userService.updateUser(user);
				JsonObject.put("result", "解绑成功！");
				JsonObject.put("success", "300");
			} else {
				JsonObject.put("result", "请先关联用户！");
				JsonObject.put("success", "301");
			}
		} else {
			JsonObject.put("result", "身份证号码输入错误！");
			JsonObject.put("success", "302");
		}
		return JsonObject.toString();
	}


	/**
	 * 获取openid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected String getOpenID(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject JsonObject = new JSONObject();
		try {
			String code = request.getParameter("code");
			String appid = request.getParameter("appid");
			String secret = request.getParameter("secret");
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
					+ appid
					+ "&secret="
					+ secret
					+ "&code="
					+ code
					+ "&grant_type=authorization_code";
			// 第一次请求 获取access_token 和 openid
			String oppid;
			oppid = new HttpRequestor().doGet(requestUrl);
			JSONObject oppidObj = JSONObject.fromObject(oppid);
			// String access_token = (String) oppidObj.get("access_token");
			String openid = (String) oppidObj.get("openid");
			if (openid != null && !"".equals(openid)) {
//				User user = userService.getUserByOpenId(openid);
//				if (user == null) {
//					JsonObject.put("flag", false);
//					JsonObject.put("openid", openid);
//				} else {
//					JsonObject.put("flag", true);
//					JsonObject.put("openid", openid);
//					JsonObject.put("user", user);
//				}
				JsonObject.put("flag", true);
				JsonObject.put("openid", openid);
			} else {
				JsonObject.put("flag", false);
				JsonObject.put("openid", "");
				JsonObject.put("message", "获取openID失败！请重试！");
			}
			return JsonObject.toString();
		} catch (Exception e) {
			JsonObject.put("flag", false);
			JsonObject.put("message", "服务器异常");
			e.printStackTrace();
			return JsonObject.toString();
		}
	}


}








