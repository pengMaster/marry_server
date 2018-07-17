package com.mtm.party.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class StringUtils {
	public static   String   getDataString (Date date,String format){
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);//yyyyMMddHHmmss
			return formatter.format(date);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return "";
	}
	
	public static String generateRefID() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Properties prop = new Properties(System.getProperties());
		String userName = prop.getProperty("user.name");
		String refId = "ST_" + userName + "_" + sdf.format(now);
		return refId;
	}

}
