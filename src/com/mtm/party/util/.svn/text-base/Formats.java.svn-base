package com.mtm.party.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formats {

	public static String getNextDayByNum(String startDate,int month){
		Calendar c = Calendar.getInstance();//获得一个日历的实例
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try{
			date = sdf.parse(startDate);//初始日期       
			c.setTime(date);//设置日历时间       
			c.add(Calendar.MONTH,month);//在日历的月份上增加6个月    
			return sdf.format(c.getTime());
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
