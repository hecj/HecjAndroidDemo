package com.hecj.demo.alarm;

import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hecj.demo.R;

public class AlarmActivity extends Activity {
	
	private Button alarm_button1 ;
	private final static int SHOW_DIALOG_DATE = 0; //dialog ID
	private final static int SHOW_DIALOG_TIME = 1; //dialog ID
	private AlarmManager alarmManager = null ; // clock manager
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.alarm_layout);
		
		alarm_button1 = (Button)this.findViewById(R.id.alarm_button1);
		//on click 
		alarm_button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(SHOW_DIALOG_TIME);
			}
		});
		
		// clock manager
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		
		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		
		Dialog _dialog = null ;
		switch (id) {
		case SHOW_DIALOG_DATE:
			_dialog = new DatePickerDialog(this, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Toast.makeText(AlarmActivity.this, "year:"+year+",monthOfYear:"+(monthOfYear+1)+",dayOfMonth:"+dayOfMonth, Toast.LENGTH_SHORT).show();
				}
			}, 2014, 9, 12);
			
			break;
			
		case SHOW_DIALOG_TIME:
			
			_dialog = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener(){

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					
					Intent _intent = new Intent(AlarmActivity.this,AlarmReceiver.class);
					PendingIntent p = PendingIntent.getBroadcast(AlarmActivity.this, 0, _intent, 0);
					// set a clock
					alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime()+1000*10, p);
					
					
					Toast.makeText(AlarmActivity.this, "hourOfDay"+hourOfDay+",minute:"+(minute), Toast.LENGTH_SHORT).show();
				
					
				
				}}, 23, 43, true);
				
			break;

		default:
			break;
		}
		
		
		return _dialog;
	}
	
	
}
