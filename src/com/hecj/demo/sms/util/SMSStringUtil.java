package com.hecj.demo.sms.util;

import java.util.Date;

/**
 * 工具类
 * @author HECJ
 *
 */
public class SMSStringUtil {
	
	/**
	 * 
	 * @param date
	 * @param currTime
	 * @return
	 */
	public static String showTimeString(Date date,Date currTime){
		
		long minuxTime = date.getTime() - currTime.getTime() ; 
		long day = minuxTime/(1000*60*60*24);
		minuxTime =minuxTime%(1000*60*60*24); 
		long hour = minuxTime/(1000*60*60);
		minuxTime = minuxTime%(1000*60*60);
		long minute = minuxTime/(1000*60);
		minuxTime = minuxTime%(1000*60);
		long second = minuxTime/1000;

		String str = "";
		if(day>0){
			str = day+"天";
		}
		if(hour>0 || str.length()>0){
			str += hour+"小时";
		}
		if(minute>0 || str.length()>0){
			str += minute+"分钟";
		}
		str += second+"秒";
		
		return str ;
	}
}
