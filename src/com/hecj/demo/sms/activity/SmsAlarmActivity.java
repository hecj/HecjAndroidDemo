package com.hecj.demo.sms.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hecj.demo.R;

public class SmsAlarmActivity extends Activity implements OnClickListener{
	
	private ImageView sms_alarm_set1 = null ;
	private ImageView sms_alarm_exist_set = null ;
	private ImageView sms_alarm_curr_alarm = null ;
	private ImageView sms_alarm_point_select = null ;
	private Intent _intent = null ;
	private static final String SMSRECEIVE = "com.hecj.demo.sms.reciver.smsReciver";
	
	private List<View> dataView = new ArrayList<View>();
	private ViewPager viewPager = null ;
	private View viewPage1 = null ;
	private View viewPage2 = null ;
	
	private ImageView leftIV = null ;
	private ImageView rightIV = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_alarm_main);
		//load viewpager
		viewPage1 =  LayoutInflater.from(this).inflate(R.layout.sms_alarm_main_viewpager1, null);
		viewPage2 =  LayoutInflater.from(this).inflate(R.layout.sms_alarm_main_viewpager2, null);
		dataView.add(viewPage1) ;
		dataView.add(viewPage2) ;
		
		//获取左右2个小图标
		leftIV = (ImageView) this.findViewById(R.id.sms_alarm_left_iv);
		rightIV = (ImageView) this.findViewById(R.id.sms_alarm_right_iv);
		sms_alarm_point_select = (ImageView) this.findViewById(R.id.sms_alarm_point_select);
		
		//ViewPager
		viewPager = (ViewPager) this.findViewById(R.id.sms_alarm_viewPager);
		viewPager.setAdapter(new MyPageAdapter(dataView));
		
		sms_alarm_set1 = (ImageView) viewPage1.findViewById(R.id.sms_alarm_set1);
		sms_alarm_exist_set = (ImageView) viewPage1.findViewById(R.id.sms_alarm_exist_set);
		sms_alarm_curr_alarm = (ImageView) viewPage1.findViewById(R.id.sms_alarm_curr_alarm);
		sms_alarm_set1.setOnClickListener(this);
		sms_alarm_exist_set.setOnClickListener(this);
		sms_alarm_curr_alarm.setOnClickListener(this);
		//滑动页触发事件
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}
	/**
	 * onclick view 
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.sms_alarm_set1:
			
			Toast.makeText(this, "触发了...", Toast.LENGTH_SHORT).show();
			_intent = new Intent(this,SmsAlarmSetContent.class);
			startActivity(_intent);
			
			break;
		case R.id.sms_alarm_exist_set:
			
			Toast.makeText(this, "查询设置闹钟...", Toast.LENGTH_SHORT).show();
			_intent = new Intent(this,SmsAlarmSearchActivity.class);
			startActivity(_intent);
			
			break;
		case R.id.sms_alarm_curr_alarm:
			
			
			AlarmManager _aAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			//set clock
			Intent _intent = new Intent();
			_intent.setAction(SMSRECEIVE);
			PendingIntent p1 = PendingIntent.getBroadcast(this, 0,_intent, PendingIntent.FLAG_CANCEL_CURRENT);
			
			_aAlarmManager.cancel(p1);
			Toast.makeText(this, "取消闹钟成功！", Toast.LENGTH_SHORT).show();
			
			
			break;
			
		default:
			break;
		}
	}
	
	class MyPageAdapter extends PagerAdapter{
		
		private List<View> dataView = null ;
		
		public MyPageAdapter(List<View> dataView ){
			this.dataView = dataView;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		
		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(dataView.get(position));
			return dataView.get(position);
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(dataView.get(position));
		}

	}
	
	/**
	 * 滑动页触发
	 * @author HECJ
	 *
	 */
	class MyOnPageChangeListener implements OnPageChangeListener{
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			//Log.v("hecjlog", "onPageScrollStateChanged:"+arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			//Log.v("hecjlog", "onPageScrollStateChanged:"+arg0+","+arg1+","+arg2);
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Log.v("hecjlog", "onPageSelected:"+arg0);
			Log.v("hecjlog", "onPageSelected:"+viewPager.getChildCount());
			RotateAnimation rotateAnimation = null;
			if(arg0 == 0){
				leftIV.setVisibility(View.GONE);
				rightIV.setVisibility(View.VISIBLE);
				
				rotateAnimation = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,  RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			}else if(viewPager.getChildCount()-1 == arg0 ){
				rightIV.setVisibility(View.GONE);
				leftIV.setVisibility(View.VISIBLE);
				rotateAnimation = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,  RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			}else{
				leftIV.setVisibility(View.VISIBLE);
				rightIV.setVisibility(View.VISIBLE);
			}
			
			/**
			 * 改变小图标点
			 */
			rotateAnimation.setDuration(0);
			rotateAnimation.setFillAfter(true); //设置动画结束后停在结束位置
			
			sms_alarm_point_select.startAnimation(rotateAnimation);
			
			
		}
		
	}
}
