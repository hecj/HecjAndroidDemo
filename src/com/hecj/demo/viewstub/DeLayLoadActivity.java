package com.hecj.demo.viewstub;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;

import com.hecj.demo.R;

public class DeLayLoadActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewstub_layout);
		this.findViewById(R.id.viewstub_layout_load).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				ViewStub _viewStub= (ViewStub) findViewById(R.id.viewstub_layout_viewStub1);
				_viewStub.inflate();
			}
		});
		
	
	}
}
