package com.hecj.demo.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ClockActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("闹钟来了");
		alert.setButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ClockActivity.this.finish();
			}
		});
		alert.show();
	}
}
