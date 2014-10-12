package com.hecj.demo.sms.util;

import java.util.Date;

/**
 * ������
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
			str = day+"��";
		}
		if(hour>0 || str.length()>0){
			str += hour+"Сʱ";
		}
		if(minute>0 || str.length()>0){
			str += minute+"����";
		}
		str += second+"��";
		
		return str ;
	}
}
