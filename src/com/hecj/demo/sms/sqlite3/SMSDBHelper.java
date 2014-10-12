package com.hecj.demo.sms.sqlite3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hecj.demo.sms.entity.SMSSalarm;

public class SMSDBHelper {

	private SMSSqliteOpenHelper dbOpenHelper = null ;
	private SQLiteDatabase dbW = null ;
	private SQLiteDatabase dbR = null ;
	private Context _context = null ;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
	public SMSDBHelper(Context context) {
		super();
		// TODO Auto-generated constructor stub
		this._context = context ;
	}
	
	private synchronized SMSSqliteOpenHelper getDBOpenHelper(){
		
		if(dbOpenHelper == null){
				Log.i("hecjlog","getDBOpenHelper");
				dbOpenHelper = new SMSSqliteOpenHelper(_context, "sms", null, 1);
		}
		return dbOpenHelper ;
	}
	/**
	 * getWritableDatabase
	 * @return
	 */
	private SQLiteDatabase getWritableDatabase(){
		if(dbW == null|| !dbW.isOpen()){
			Log.i("hecjlog","getWritableDatabase");
			dbW = getDBOpenHelper().getWritableDatabase();
		}
		return dbW;
	}
	/**
	 * getReadableDatabase
	 * @return
	 */
	private SQLiteDatabase getReadableDatabase(){
		if(dbR == null || !dbR.isOpen()){
			Log.i("hecjlog","getReadableDatabase");
			dbR = getDBOpenHelper().getReadableDatabase();
		}
		return dbR;
	}
	/**
	 * add SMSSalarm
	 * @param sMSSalarm
	 */
	public long addSMSSalarm(SMSSalarm sMSSalarm){
		long n = 0;
		try{
			Log.i("hecjlog","addSMSSalarm start...");
			
			ContentValues values = new ContentValues();
			values.put("telphone", sMSSalarm.getTelphone());
			values.put("content", sMSSalarm.getContent());
			values.put("sendTime", format.format(sMSSalarm.getSendTime()));
			values.put("createtime", format.format(sMSSalarm.getCreatetime()));
			dbW = getWritableDatabase();
			n = dbW.insert("smsalarm", null, values);
			
			Log.i("hecjlog","addSMSSalarm end...n:"+n);
		}catch(Exception ex){
			Log.e("hecjlog", ex.getMessage());
			ex.printStackTrace();
		}finally{
			dbW.close();
		}
		return n;
	}
	
	/**
	 * search SMSSalarm
	 * @return
	 */
	public Cursor searchSMSSalarm(){
		Cursor _cursor = null ;
		try{
			Log.i("hecjlog","searchSMSSalarm start...");
			dbR = getReadableDatabase();
			_cursor = dbR.query("smsalarm", new String[]{"_id","telphone","content","sendTime","createtime"}, "sendTime>?", new String[]{format.format(new Date())}, null, null, "sendTime");
			
			Log.i("hecjlog","searchSMSSalarm end...");
			
		}catch(Exception ex){
			Log.e("hecjlog", ex.getMessage());
			ex.printStackTrace();
		}
		return _cursor ;
		
	}
	
	/**
	 * search searchSMSSalarmLimit1
	 * @return
	 */
	public Cursor searchSMSSalarmLimit1(){
		Cursor _cursor = null ;
		try{
			Log.i("hecjlog","searchSMSSalarmLimit1 start...");
			dbR = getReadableDatabase();
			_cursor = dbR.query("smsalarm", new String[]{"_id","telphone","content","sendTime","createtime"}, "sendTime>?", new String[]{format.format(new Date())}, null, null, "sendTime","1");
			Log.i("hecjlog","searchSMSSalarmLimit1 end...");
			
		}catch(Exception ex){
			Log.e("hecjlog", ex.getMessage());
			ex.printStackTrace();
		}
		return _cursor ;
		
	}
	
	/**
	 * close Cursor
	 */
	public void closeCursor(Cursor _cursor){
		if(_cursor!= null && !_cursor.isClosed()){
			_cursor.close();
		}
	}
	/**
	 * close db
	 */
	public void closeDB(){
		if(dbW!=null && dbW.isOpen()){
			dbW.close();
		}
		if(dbR!=null && dbR.isOpen()){
			dbR.close();
		}
	}
	
	
}
