package com.mtm.party.util.wxLogin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 发送https请求
	 * @param requestUrl  请求地址
	 * @param requestMethod  请求方式 get post
	 * @param outputStr      提交的数据
	 * @return JSONObject  (通过JSONobject.getKey("key")的方式获取JSON对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject=null;
		try {
			//创建SSLContext对象 并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			//设置请求方式
			conn.setRequestMethod(requestMethod);
			
			//当outputStr 不为null时 向输出流写数据
			if(null!=outputStr){
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			
			
			// 读取返回数据
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new  InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while((str=bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			
			//释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream=null;
			conn.disconnect();
			jsonObject=JSONObject.fromObject(buffer.toString());
			
			
		} catch (Exception e) {
           log.error("异常了。。。。。。");
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	
	
	
	/** 
     * 发起http get请求获取网页源代码 
     *  
     * @param requestUrl 
     * @return 
     */  
    public static String httpRequest(String requestUrl) {  
        StringBuffer buffer = null;  
  
        try {  
            // 建立连接  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
  
            // 获取输入流  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            // 读取返回结果  
            buffer = new StringBuffer();  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
  
            // 释放资源  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            httpUrlConn.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer.toString();  
    }  
    
    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    

}
