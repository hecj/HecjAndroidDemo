package com.hecj.demo.sms.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hecj.demo.R;
import com.hecj.demo.sms.entity.SMSSalarm;
import com.hecj.demo.sms.reciver.SmsReciver;
import com.hecj.demo.sms.sqlite3.SMSDBHelper;
import com.hecj.demo.sms.sqlite3.SMSSqliteOpenHelper;
import com.hecj.demo.sms.util.SMSStringUtil;
/**
 * set sms content
 * @author HECJ
 *
 */
public class SmsAlarmSetContent extends Activity implements OnClickListener{
	private Button sms_alarm_set_sms_clock = null ;
	private EditText sms_alarm_telphone = null ;
	private EditText sms_alarm_content = null ;
	private DatePicker sms_alarm_datePicker1 = null ;
	private TimePicker sms_alarm_timePicker1 = null ;
	private SMSDBHelper dbHelper = new SMSDBHelper(this);
	private Cursor _cursor = null ;
	private static final String SMSRECEIVE = "com.hecj.demo.sms.reciver.smsReciver";
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_alarm_set_content);
		
		sms_alarm_set_sms_clock = (Button)this.findViewById(R.id.sms_alarm_set_sms_clock);
		sms_alarm_telphone = (EditText)this.findViewById(R.id.sms_alarm_telphone);
		sms_alarm_content = (EditText)this.findViewById(R.id.sms_alarm_content);
		sms_alarm_datePicker1 = (DatePicker)this.findViewById(R.id.sms_alarm_datePicker1);
		sms_alarm_timePicker1 = (TimePicker)this.findViewById(R.id.sms_alarm_timePicker1);
		sms_alarm_set_sms_clock.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sms_alarm_set_sms_clock:
			/**
			 * ��Ӳ��裬��ѯ��ǰ���ݿ��������ӡ�
			 * �������ӣ���ѯ��ǰ�����ʱ�������ݿ�����ʱ��ƥ�䡣
			 * �������ݣ���ѵ�ǰ��ӵ���������Ϊ����
			 */
			try{
				String telPhone = sms_alarm_telphone.getText().toString().trim(); 
				String telContent = sms_alarm_content.getText().toString().trim();
				int year = sms_alarm_datePicker1.getYear();
				int month = sms_alarm_datePicker1.getMonth();
				int dayOfMonth = sms_alarm_datePicker1.getDayOfMonth();
				int hour = sms_alarm_timePicker1.getCurrentHour();
				int minute = sms_alarm_timePicker1.getCurrentMinute();
				
				
				//clock time
				Calendar _caCalendar = Calendar.getInstance();
				_caCalendar.set(Calendar.YEAR, year);
				_caCalendar.set(Calendar.MONTH, month);
				_caCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				_caCalendar.set(Calendar.HOUR_OF_DAY, hour);
				_caCalendar.set(Calendar.MINUTE, minute);
				
				//clock long
				Date sendDate = _caCalendar.getTime();
				Long clockTime = sendDate.getTime();
				Log.v("hecj", format.format(sendDate));
				
				_cursor = dbHelper.searchSMSSalarmLimit1();
				if(_cursor.getCount()>0){
					_cursor.moveToFirst();
					Date historyClockTime = format.parse(_cursor.getString(3));
					
					if(clockTime<historyClockTime.getTime() && clockTime>new Date().getTime()){
						//���õ�ǰ��ӵ�����Ϊ��������
						AlarmManager _aAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
						//set clock
						Intent _intent = new Intent();
						_intent.putExtra("telPhone", telPhone);
						_intent.putExtra("telContent", telContent);
						_intent.setAction(SMSRECEIVE);
						
						PendingIntent p1 = PendingIntent.getBroadcast(this, 0,_intent, PendingIntent.FLAG_CANCEL_CURRENT);
						_aAlarmManager.set(AlarmManager.RTC_WAKEUP, clockTime, p1);
					}
				}else{
					//���õ�ǰ��ӵ�����Ϊ��������
					AlarmManager _aAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
					//set clock
					Intent _intent = new Intent();
					_intent.putExtra("telPhone", telPhone);
					_intent.putExtra("telContent", telContent);
					_intent.setAction(SMSRECEIVE);
					
					PendingIntent p1 = PendingIntent.getBroadcast(this, 0,_intent, PendingIntent.FLAG_CANCEL_CURRENT);
					_aAlarmManager.set(AlarmManager.RTC_WAKEUP, clockTime, p1);
				}
				
				SMSSalarm smsSalarm = new SMSSalarm();
				smsSalarm.setTelphone(telPhone);
				smsSalarm.setContent(telContent);
				smsSalarm.setSendTime(sendDate);
				smsSalarm.setCreatetime(new Date());
				
				dbHelper.addSMSSalarm(smsSalarm);
			
				Toast.makeText(this, SMSStringUtil.showTimeString(sendDate, new Date()) + "�󴥷���", Toast.LENGTH_SHORT).show();
				
			}catch(Exception ex){
				ex.printStackTrace();
				Toast.makeText(this, "������������ʧ�ܣ�", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		dbHelper.closeCursor(_cursor);
		dbHelper.closeDB();
		
	}
}
