package com.hecj.demo.sms.reciver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.hecj.demo.sms.sqlite3.SMSDBHelper;
import com.hecj.demo.sms.util.SMSStringUtil;

public class SmsReciver extends BroadcastReceiver {
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent!=null){
			Bundle _bundle = intent.getExtras();
			String telPhone = _bundle.getString("telPhone");
			String telContent = _bundle.getString("telContent");
			Log.v("hecj", telPhone);
			Log.v("hecj", telContent);
			
			//send a sms
			SmsManager sms = SmsManager.getDefault();
			PendingIntent _p = PendingIntent.getActivity(context, 0,intent, 0);
			sms.sendTextMessage(telPhone.trim(), null, telContent.trim(), _p,  null) ;
			Toast.makeText(context, "on clock !", Toast.LENGTH_SHORT).show();
			SMSDBHelper dbHelper = new SMSDBHelper(context);
			Cursor _cursor = null ;
			try{
				
				//查询是否有下一个短信闹钟
				
				_cursor = dbHelper.searchSMSSalarmLimit1();
				
				Log.v("hecj", "COUNT:"+ _cursor.getCount());
				
				if(_cursor.getCount()>0){
					_cursor.moveToFirst();
					String _cursorTelphone = _cursor.getString(1);
					String _cursorcontent = _cursor.getString(2);
					String sendTime = _cursor.getString(3);
					Date sendDateTime = format.parse(sendTime);
					
					AlarmManager _aAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
					//set clock
					Intent _intent = new Intent(context,SmsReciver.class);
					_intent.putExtra("telPhone", _cursorTelphone);
					_intent.putExtra("telContent", _cursorcontent);
					
					PendingIntent p1 = PendingIntent.getBroadcast(context, 0,_intent, PendingIntent.FLAG_CANCEL_CURRENT);
					_aAlarmManager.set(AlarmManager.RTC_WAKEUP, sendDateTime.getTime(), p1);
					
					Toast.makeText(context, SMSStringUtil.showTimeString(sendDateTime, new Date()) + "后触发！", Toast.LENGTH_SHORT).show();
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				dbHelper.closeCursor(_cursor);
				dbHelper.closeDB();
			}
			
			
		}
		
	}

}
