package com.mtm.party.mobile.ctrl;

import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Reptilian {
	static String downLoadPath = "D:\\workTime\\MEP_Project\\party\\party\\WebRoot\\wechat\\marry\\downLoadImage\\";	
	
	public static void main(String[] args) throws Exception{

		String htmlPath = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1531296845120_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&hs=2&word=%E5%8A%A8%E6%BC%AB";

		String htmlString = getHtml2String(htmlPath);
		
		ArrayList imageList = getImageStr(htmlString);

		for (int i = 0; i < imageList.size(); i++) {
			final String imageUrl = imageList.get(i)+"";
			new Thread(new Runnable() {
				
				public void run() {
					try {
						downLoadImage(imageUrl);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			System.err.println(imageList.get(i)+"\n");

			
		}
	
	}
	private static ArrayList returnImageList(){
		ArrayList imageList = new ArrayList();
		String baseUrl = "http://img1.mm131.me/pic/3388";
		imageList.add(baseUrl+".jpg");
		for (int i = 1; i < 24; i++) {
			imageList.add(baseUrl+"/"+i+".jpg");
		}
		return imageList;
	}

	/**
	 * 将网页源码截取出图片Url
	 * 
	 * @param htmlString
	 * @return
	 */
	private static ArrayList getImageStr(String htmlString) {
		String [] htmlStr = htmlString.split("URL\":\"http");
		ArrayList imageList = new ArrayList();
		for (int i = 0; i < htmlStr.length; i++) {
			String html = htmlStr[i];
			String [] imageStr = html.split("jpg");
			for (int j = 0; j < imageStr.length; j++) {
				String imageUrl = "http"+imageStr[j]+"jpg";
				imageList.add(imageUrl);
			}
		}
		return imageList;
		
	}

	/**
	 * url转换为网页源码
	 * @param htmlUrl
	 * @return
	 * @throws Exception
	 */
	private static String getHtml2String(String htmlUrl) throws Exception {

		URL url = null;
		try {
			url = new URL(htmlUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String charset = "utf-8";
		int sec_cont = 1000;
		HttpURLConnection url_con = (HttpURLConnection) url.openConnection();
		url_con.setDoOutput(true);
		url_con.setReadTimeout(10 * sec_cont);
		url_con.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		InputStream htm_in = url_con.getInputStream();
		String htm_str = InputStream2String(htm_in, charset);
		return htm_str;

	}

	/**
	 * 下载图片
	 * @throws Exception
	 */
	private static void downLoadImage(String pathUrl) throws Exception {
		// new一个URL对象
		URL url = new URL(pathUrl);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(inStream);
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		String imageName = System.currentTimeMillis()+".jpg";
		
		File imageFile = new File(downLoadPath + imageName);
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(data);
		// 关闭输出流
		outStream.close();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	
	  public static String InputStream2String(InputStream in_st,String charset) throws IOException{
		    BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
		    StringBuffer res = new StringBuffer();
		    String line = "";
		    while((line = buff.readLine()) != null){
		      res.append(line);
		    }
		    return res.toString();
		  }
}