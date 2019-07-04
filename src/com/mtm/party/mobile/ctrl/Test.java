package com.mtm.party.mobile.ctrl;

import com.mchange.v2.log.LogUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Test {

	private static URL realUrl;

	private static String filterNpc = "【阎罗王】";
	private static String homeStatus = "〖全区物品拍卖中心〗";
	private static String attackType = "普攻";
	private static String attackBossName = "【蒸笼鬼】";
	private static int totalExperience = 0;
	private static int totalBossCount = 0;
	private static String sid = "";
	private static String initCmd = "";

	private static String loginUrl = "http://zmxyjz.yytou.com/im_login.do;jsessionid=72546D32B06736B7616DBC7A53D73F87?id=18141924293&pwd=pengwen521&game_host=g1570";
	private static String attackAutoUrl = "http://zmxyjz6.yytou.com/gCmd.do?cmd=47441&sid=1mdw3t4d74o6ht3whbs69&gid=g1570";


	public static void main(String[] args) {

		//刷NPC
//		String initResult = sendGet("49f54");
//		String cmdinitResult = getCmdByResult(initResult, filterNpc);
		//getExperienceByYLW("4a1fb");
		//刷副本自动攻击的怪
		getCmdAndSidByUrl(attackAutoUrl);
		try {
			realUrl = new URL("");
			attackAutoAttackMonster(initCmd);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void attackAutoAttackMonster(String cmd){
		String initResult = sendGet(cmd);
		if (initResult.contains("刷新") && (initResult.contains(homeStatus)||initResult.contains("相关颜色选项")
				|| initResult.contains(attackBossName))){
			//打Boss
			if (initResult.contains(attackBossName)) {//initResult.contains(attackBossName)
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
				String refreshCmd = getCmdByResult(initResult, "刷新");
				attackAutoAttackMonster(refreshCmd);
			}
		}
		//去打怪
		else if (initResult.contains("拔草")){
			String attackCmd = getCmdByResult(initResult, "拔草");
			String attackResult = sendGet(attackCmd);
			isToBackAttack(attackResult);
		}
	}

	private static void attackAutoAttackMonsterAndAttackBoss(String cmd){
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
	private static void getCmdAndSidByUrl(String initUrl){
		if (initUrl.contains("cmd=")){
			String[] split = initUrl.split("cmd=");
			String s = split[split.length - 1];
			String[] split1 = s.split("&sid=");
			initCmd = split1[0];
			String[] split2 = split1[1].split("&gid=");
			sid = split2[0];
		}
	}

	private static void getExperienceByYLW(String cmd) {
		String result1 = sendGet(cmd);
		String cmdByFN1 = getCmdByResult(result1, filterNpc);
		String result2 = sendGet(cmdByFN1);
		String cmdByFN = getCmdByResult(result2, attackType);
		String result3 = sendGet(cmdByFN);
		isToBack(result3);
	}

	private static void isToBack(String result3) {
		if (result3.contains("返回游戏") && result3.contains("cmd=")&& result3.contains("经验+")) {
			getTotalExperience(result3);
			backGame(result3);
		} else {
			String cmdByFN11 = getCmdByResult(result3, attackType);
			String result4 = sendGet(cmdByFN11);
			isToBack(result4);
		}
	}

	private static void isToBackAttack(String result3) {
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

	private static void backGame(String result3) {
		String[] split4 = result3.split("cmd=");
		if (split4[1].contains("&amp")) {
			String[] split5 = split4[1].split("&amp");
			String cmd3 = split5[0];
			String result4 = sendGet(cmd3);
			String cmdByResult = getCmdByResult(result4, filterNpc);
			getExperienceByYLW(cmdByResult);
		}
	}


	private static String getCmdByResult(String result, String filter) {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

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

	private static boolean isContainsZLG(String result){
		if (result.contains("蒸汽司狱官")) {
			String[] split = result.split("蒸汽司狱官");
			if (split[0].contains(attackBossName))
				return true;
			else
				return false;
		}
		return false;
	}

	private static String getCmdByResultForRefreshForNpc(String result) {
		if (result.contains("\">继续") && result.contains("refresh")) {
			try {
				System.out.println("\n-----------------Thread.sleep(2000)-------------------\n");
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

	private static void refreshForAutoAttack(String result) {
		if (result.contains("\">继续") && result.contains("refresh")) {
			try {
				System.out.println("\n-----------------Thread.sleep(2000)-------------------\n");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			attackAutoAttackMonster(initCmd);
		}

	}

	private static void getTotalExperience(String result) {
		if (result.contains("经验+")) {
			String[] split = result.split("经验\\+");
			if (split[1].contains("森林")) {
				String[] s = split[1].split("<br/>森林");
				String s1 = s[0];
				try {
					totalExperience = totalExperience + Integer.parseInt(s1);
					System.out.println("\n------------------totalExperience:"+totalExperience+"------------------\n");
				} catch (Exception e) {
					System.out.println("\n------------------totalExperience:"+s1+"------------------\n");
				}

			}
		}

	}

	private static void getTotalExperienceByAttack(String result) {
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
						}
					}
				}

			}
			System.out.println("\n------------------打怪获得经验:"+totalExperience+"------------------");
			System.out.println("\n------------------击杀Boss数量:"+totalBossCount+"------------------\n");
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
	public static String sendGet(String cmd) {
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
			connection.setRequestProperty("Cookie", "lang=zh; __cfduid=d0b425fee6cc5be80c003b8db6f8b2c501559615688; yjs_id=b90a87a045ec4d7b897bf6d961ca1fe7; ctrl_time=1");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
			// 建立实际的连接
			connection.connect();
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
		System.out.println(result);
		refreshForAutoAttack(result);
		return result;
	}


}
