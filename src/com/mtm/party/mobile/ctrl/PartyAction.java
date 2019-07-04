package com.mtm.party.mobile.ctrl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import cn.mtm2000.common.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mtm.party.mobile.model.ResponseBean;
import com.mtm.party.mobile.model.UserImageBean;
import com.mtm.party.mobile.service.MobileService;
import com.mtm.party.user.service.UserService;

/**
 * 中文参数接收方式:URLDecoder.decode(request.getParameter("body"), "UTF-8");
 *
 * 幻想西游自动刷怪脚本
 * 
 * 后台管理对接
 */

@Controller
@RequestMapping("/")
public class PartyAction {

	private  String filterNpc = "【阎罗王】";
	private  String homeStatus = "〖全区物品拍卖中心〗";
	private  String attackType = "普攻";
	private  String attackBossName = "【蒸笼鬼】";
	private  int totalExperience = 0;
	private  int totalBossCount = 0;
	private  String sid = "";
	private  String initCmd = "";
	private StringBuffer buffer = new StringBuffer();
	private boolean isAction = true;

	private  String loginUrl = "http://zmxyjz.yytou.com/im_login.do;jsessionid=72546D32B06736B7616DBC7A53D73F87?id=18141924293&pwd=pengwen521&game_host=g1570";
	private static String attackAutoUrl = "http://zmxyjz6.yytou.com/gCmd.do?cmd=823e4&sid=cj9ulc4d3819fae2ex97t&gid=g1570";

	private PrintWriter writer;

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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// http://localhost:8080/party//marry
	@RequestMapping(value = "/marry", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		List list = (List) userService.getUsers();
		request.setAttribute("list", list);
		System.out.println(list.toString());
		return "statistic/statisticIndex";
	}

	// http://localhost:8080/party//game.do?cmd=906b3&sid=j74zm84d3acs51e4n355b&attackBossName=【蒸笼鬼】
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public String game(HttpServletRequest request, HttpServletResponse response) {
		try {
			initCmd = request.getParameter("cmd");
			attackBossName = request.getParameter("attackBossName");
			sid = request.getParameter("sid");
			isAction = true;
			//刷副本自动攻击的怪
			writer = response.getWriter();
			attackAutoAttackMonster(initCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	// http://localhost:8080/party/gameBack
	@RequestMapping(value = "/gameBack", method = RequestMethod.GET)
	public String gameBack(HttpServletRequest request, HttpServletResponse response) {
		isAction = false;
		return "";
	}

	// http://localhost:8080/party//gameNpc.do?cmd=8d006&sid=&debx44d39l12ge3uhx1zfilterNpc=推磨鬼
	@RequestMapping(value = "/gameNpc", method = RequestMethod.GET)
	public String gameNpc(HttpServletRequest request, HttpServletResponse response) {
		try {
			initCmd = request.getParameter("cmd");
			filterNpc = request.getParameter("filterNpc");
			sid = request.getParameter("sid");
			isAction = true;
			//刷副本自动攻击的怪
			writer = response.getWriter();
			getExperienceByYLW(initCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveBanner(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ResponseBean resBean = new ResponseBean();
		Gson gson = new Gson();
		String data = request.getParameter("data");
		if (null == data) {
			resBean.setMessage("mehod_error");
			return gson.toJson(resBean);
		}

		List<UserImageBean> lists = new Gson().fromJson(data,
				new TypeToken<List<UserImageBean>>() {
				}.getType());
		String path = System.getProperty("catalina.home") + "/wechat/"
				+ "/userImage/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		for (int i = 0; i < lists.size(); i++) {
			UserImageBean imgBean = lists.get(i);
			String name = imgBean.getName();
			String base64 = imgBean.getBase64();
			Base64ToImage(base64, path + name);
		}
		resBean.setMessage("save_success");
		return gson.toJson(resBean);
	}
	private  void attackAutoAttackMonster(String cmd){
		if (!isAction)
			return;
		String initResult = sendGet(cmd);
		if (initResult.contains("刷新") && (initResult.contains(homeStatus)||
				initResult.contains("相关颜色选项") || initResult.contains(attackBossName))){
			//打Boss
			if (isContainsZLG(initResult)) {//initResult.contains(attackBossName)i
				if (!isAction)
					return;
				System.out.println("\n-----------------发现："+attackBossName+"-------------------\n");
				totalBossCount++;
				String attackCmd = getCmdByResult(initResult, attackBossName);
				String attackResult = sendGet(attackCmd);
				String attackCmd2 = getCmdByResult(attackResult, attackBossName);
				String attackResult2 = sendGet(attackCmd2);
				String attackCmd1 = getCmdByResult(attackResult2, "拔草");
				String attackResult1 = sendGet(attackCmd1);
				isToBackAttack(attackResult1);
			} else {
				//接着刷新
				if (!isAction)
					return;
				String refreshCmd = getCmdByResult(initResult, "刷新");
				attackAutoAttackMonster(refreshCmd);
			}
		}
		//去打怪
		else if (initResult.contains("拔草")){
			if (!isAction)
				return;
			String attackCmd = getCmdByResult(initResult, "拔草");
			String attackResult = sendGet(attackCmd);
			isToBackAttack(attackResult);
		}
	}

	private  void attackAutoAttackMonsterAndAttackBoss(String cmd){
		String initResult = sendGet(cmd);
		//接着刷新
		if (initResult.contains("刷新") && initResult.contains(homeStatus)){
			String refreshCmd = getCmdByResult(initResult, "刷新");
			attackAutoAttackMonster(refreshCmd);
		}
		//去打怪
		else if (initResult.contains("拔草")){
			String attackCmd = getCmdByResult(initResult, "拔草");
			String attackResult = sendGet(attackCmd);
			isToBackAttack(attackResult);
		}
	}
	private  void getCmdAndSidByUrl(String initUrl){
		if (initUrl.contains("cmd=")){
			String[] split = initUrl.split("cmd=");
			String s = split[split.length - 1];
			String[] split1 = s.split("&sid=");
			initCmd = split1[0];
			String[] split2 = split1[1].split("&gid=");
			sid = split2[0];
		}
	}

	private  void getExperienceByYLW(String cmd) {
		String result1 = sendGet(cmd);
		String cmdByFN1 = getCmdByResult(result1, filterNpc);
		String result2 = sendGet(cmdByFN1);
		String cmdByFN = getCmdByResult(result2, attackType);
		String result3 = sendGet(cmdByFN);
		isToBack(result3);
	}

	private  void isToBack(String result3) {
		if (result3.contains("返回游戏") && result3.contains("cmd=")&& result3.contains("经验+")) {
			if (!isAction)
				return;
			getTotalExperience(result3);
			backGame(result3);
		} else {
			String cmdByFN11 = getCmdByResult(result3, attackType);
			String result4 = sendGet(cmdByFN11);
			if (!isAction)
				return;
			isToBack(result4);
		}
	}

	private  void isToBackAttack(String result3) {
		if (!isAction)
			return;
		if (result3.contains("返回游戏") && result3.contains("cmd=")&& result3.contains("经验+")) {
			getTotalExperienceByAttack(result3);
			String[] split4 = result3.split("cmd=");
			if (split4[1].contains("&amp")) {
				String[] split5 = split4[1].split("&amp");
				String cmd3 = split5[0];
				attackAutoAttackMonster(cmd3);
			}

		} else {
			String cmdByFN11 = getCmdByResult(result3, attackType);
			String result4 = sendGet(cmdByFN11);
			isToBackAttack(result4);
		}
	}

	private  void backGame(String result3) {
		String[] split4 = result3.split("cmd=");
		if (split4[1].contains("&amp")) {
			writer.write(buffer.toString());
			String[] split5 = split4[1].split("&amp");
			String cmd3 = split5[0];
			String result4 = sendGet(cmd3);
			String cmdByResult = getCmdByResult(result4, filterNpc);
			getExperienceByYLW(cmdByResult);
		}
	}


	private  String getCmdByResult(String result, String filter) {

		if (result.contains(filter)) {
			String[] fns = result.split(filter);
			if (fns[0].contains("cmd=")) {
				String[] split3 = fns[0].split("cmd=");
				String s = split3[split3.length-1];
				String sid = "";
				if (s.contains("&sid"))
					sid = "&sid";
				else if(s.contains("&amp"))
					sid = "&amp";
				if (s.contains(sid) && !sid.equals("")) {
					String[] split4 = s.split(sid);
					return split4[0];
				}
				return s ;
			}

		}
		return initCmd;
	}

	private  boolean isContainsZLG(String result){
		if (result.contains("蒸汽司狱官")) {
			String[] split = result.split("蒸汽司狱官");
			if (split[0].contains(attackBossName))
				return true;
			else
				return false;
		}
		return false;
	}

	private  String getCmdByResultForRefreshForNpc(String result) {
		if (result.contains("\">继续") && result.contains("refresh")) {
			try {
				System.out.println("\n-----------------Thread.sleep(2000)-------------------\n");
				buffer.append("\n-----------------Thread.sleep(2000)-------------------\n");
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String cmdByResult = getCmdByResult(result, "\">继续");
			String result3 = sendGet(cmdByResult);
			if (result3.contains(homeStatus) && result3.contains(filterNpc)) {
				String cmdByFN1 = getCmdByResult(result3, filterNpc);
				getExperienceByYLW(cmdByFN1);
			} else {
				getCmdByResultForRefreshForNpc(result3);
			}
		}
		return result;

	}

	private  void refreshForAutoAttack(String result) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!isAction)
			return;
		if (result.contains("\">继续") && result.contains("refresh")) {
			try {
				System.out.println("\n-----------------Thread.sleep(2000)-------------------\n");
				buffer.append("\n-----------------Thread.sleep(2000)-------------------\n");

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			attackAutoAttackMonster(initCmd);
		}

	}

	private  void getTotalExperience(String result) {
		if (result.contains("经验+")) {
			String[] split = result.split("经验\\+");
			if (split[1].contains("森林")) {
				String[] s = split[1].split("<br/>森林");
				String s1 = s[0];
				try {
					totalExperience = totalExperience + Integer.parseInt(s1);
					System.out.println("\n------------------totalExperience:"+totalExperience+"------------------\n");
					buffer.append("\n------------------totalExperience:"+totalExperience+"------------------\n");

				} catch (Exception e) {
					System.out.println("\n------------------totalExperience:"+s1+"------------------\n");
					buffer.append("\\n------------------totalExperience:"+s1+"------------------\\n");

				}

			}
		}

	}

	private  void getTotalExperienceByAttack(String result) {
		if (result.contains("经验+")) {
			String[] split = result.split("经验\\+");
			for (int i = 0; i < split.length; i++) {
				if (i != 0) {
					String s = split[i];
					if (s.contains("<br/>")) {
						String[] split1 = s.split("<br/>");
						String s1 = split1[0];
						try {
							totalExperience = totalExperience + Integer.parseInt(s1);
						} catch (Exception e) {
							System.out.println("\n------------------道具获得:"+s1+"------------------\n");
							buffer.append("\n------------------道具获得:"+s1+"------------------\n");
						}
					}
				}

			}
			System.out.println("\n------------------打怪获得经验:"+totalExperience+"------------------");
			System.out.println("\n------------------击杀Boss数量:"+totalBossCount+"------------------\n");
			buffer.append("\n------------------totalExperience:"+totalExperience+"------------------");
			buffer.append("\n------------------totalBossCount:"+totalBossCount+"------------------\n");
		}

	}
	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param cmd 执行码
	 *            发送请求的URL
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public  String sendGet(String cmd) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlStart = "http://zmxyjz6.yytou.com/gCmd.do?cmd=";
			String urlEnd = "&sid="+sid+"&gid=g1570";
			String urlNameString = urlStart + cmd + urlEnd;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Referer", urlNameString);
			connection.setRequestProperty("Cookie", "lang=zh");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
	            /*for (String key : map.keySet()) {
	                 System.out.println(key + "--->" + map.get(key));
	             }*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		//System.out.println(result);
		refreshForAutoAttack(result);
		return result;
	}
	/**
	 * @描述 java生成流水号 14位时间戳 + 6位随机数
	 * @作者 shaomy
	 * @时间:2017-1-12 上午10:10:41
	 * @参数:@return
	 * @返回值：String
	 */
	public static String getId() {
		String id = "";
		// 获取当前时间戳
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String temp = sf.format(new Date());
		// 获取6位随机数
		int random = (int) ((Math.random() + 1) * 100000);
		id = temp + random;
		return id;
	}

	/**
	 * base64字符串转换成图片
	 * 
	 * @param imgStr
	 *            base64字符串
	 * @param imgFilePath
	 *            图片存放路径
	 * @return
	 * 
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:42:17
	 */
	public static boolean Base64ToImage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片

		if (null == imgStr || "".equals(imgStr)) // 图像数据为空
			return false;

		String[] imaStrs = imgStr.split("base64,");

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imaStrs[1]);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();

			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
