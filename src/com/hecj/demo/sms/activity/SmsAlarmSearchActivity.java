package com.hecj.demo.sms.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.hecj.demo.R;
import com.hecj.demo.sms.sqlite3.SMSDBHelper;
import com.hecj.demo.sms.util.SMSStringUtil;

public class SmsAlarmSearchActivity extends Activity implements Runnable {

	private SMSDBHelper dbHelper = new SMSDBHelper(this);
	private ListView _listView = null ;
	private Cursor _cursor = null ;
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
	private Handler _handler = null ;
	private List<Map<String,Object>> data = null;
	private SimpleAdapter _adapter = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_alarm_search);
		
		_listView =  (ListView) this.findViewById(R.id.sms_alarm_search_listview);
		setListView();
		
		_handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what == 1111){
					updateListView();
				}
				
			}
		};
		Thread _thread = new Thread(this);
		_thread.start();
	}
	
	/**
	 * 设置界面的值
	 */
	private void setListView(){
		try{
			_cursor = dbHelper.searchSMSSalarm();
			Log.v("hecjlog",_cursor.getCount()+"");
			data = new ArrayList<Map<String,Object>>();
			int i = 0;
			Date currDate = new Date();
			while(_cursor.moveToNext()){
				String telPhone = _cursor.getString(1);
				String content = _cursor.getString(2);
				String sendTime = _cursor.getString(3);
				String createTime = _cursor.getString(4);
				Log.v("hecjlog",sendTime+","+telPhone+","+content+","+sendTime+","+createTime);
				Map map = new HashMap();
				map.put("id", i++);
				map.put("telphone", telPhone);
				map.put("content", content);
				map.put("sendTime", sendTime);
				map.put("showtime", SMSStringUtil.showTimeString(format.parse(sendTime), currDate)+"后触发");
				data.add(map);
			}
			_adapter = new SimpleAdapter(this, data, R.layout.sms_alarm_search_listitem, 
					new String[]{"id","telphone","content","sendTime","showtime"}, new int[]{R.id.sms_alarm_search_id,R.id.sms_alarm_search_telphone,R.id.sms_alarm_search_content,R.id.sms_alarm_search_sendtime,R.id.sms_alarm_search_showtime});
			_listView.setAdapter(_adapter);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 更新listview
	 */
	private void updateListView() {
		// TODO Auto-generated method stub
		Date currDate = new Date();
		try{
			for(int i=0;i<data.size();i++){
				Map map = data.get(i);
				Date sendDate = format.parse((String)map.get("sendTime"));
				map.put("showtime", SMSStringUtil.showTimeString(sendDate, currDate)+"后触发");
				if(sendDate.before(currDate)){
					data.remove(map);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		_adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("hecj", "close cursor and db");
		dbHelper.closeCursor(_cursor);
		dbHelper.closeDB();
		
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message _msg = new Message();
			_msg.what = 1111;
			_handler.sendMessage(_msg);
		}
	}
}
