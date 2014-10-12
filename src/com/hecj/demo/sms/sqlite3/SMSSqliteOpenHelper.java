package com.hecj.demo.sms.sqlite3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SMSSqliteOpenHelper extends SQLiteOpenHelper {

	public SMSSqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	/**

	CREATE TABLE smsalarm ( 
    _id         INTEGER         PRIMARY KEY AUTOINCREMENT,
    telphone   VARCHAR( 15 ),
    content    VARCHAR( 500 ),
    sendTime   DATETIME,
    createtime DATETIME 
);

	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		StringBuffer createTable = new StringBuffer();
		createTable.append("CREATE TABLE smsalarm ( ");
		createTable.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
		createTable.append("telphone VARCHAR(15),");
		createTable.append("content VARCHAR(500),");
		createTable.append("sendTime DATETIME,");
		createTable.append("createtime DATETIME");
		createTable.append(");");
		
		try{
			db.execSQL(createTable.toString());
			Log.i("hecjlog", "create table success!");
		}catch(Exception ex){
			ex.printStackTrace();
			Log.e("hecjlog", "create table fail!");
			Log.e("hecjlog", ex.getMessage());
		}finally{
			//db.close();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
