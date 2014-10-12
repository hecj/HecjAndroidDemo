package com.hecj.demo.changebackground;

import com.hecj.demo.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class RotateSampleActivity extends Activity {
	
	private static final String TAG = "RotateSampleActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotate_layout1);
		Log.v(TAG, "onCreated");
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.v(TAG, "onConfigurationChanged");
		
		if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			setContentView(R.layout.rotate_layout1);
		}else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
			setContentView(R.layout.rotate_layout2);
		}
		
		
		
	}
}
