package com.hecj.demo.viewpager;

import java.util.List;
import java.util.ArrayList;

import com.hecj.demo.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class ViewPagerActivity extends Activity {
	private ViewPager viewPager;
	private ImageView imageView;
	private List<View> lists = new ArrayList<View>();
	private ViewPagerAdapter myAdapter;
	private Bitmap cursor;
	private int offSet;
	private int currentItem;
	private Matrix matrix = new Matrix();
	private int bmWidth;
	private Animation animation;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.com_hecj_demo_viewpager);
		imageView = (ImageView) findViewById(R.id.page_cursor);
		
		textView1 = (TextView) findViewById(R.id.page_textView1);
		textView2 = (TextView) findViewById(R.id.page_textView2);
		textView3 = (TextView) findViewById(R.id.page_textView3);
		
		lists.add(getLayoutInflater().inflate(R.layout.com_hecj_demo_viewpager_page1, null));
		lists.add(getLayoutInflater().inflate(R.layout.com_hecj_demo_viewpager_page2, null));
		lists.add(getLayoutInflater().inflate(R.layout.com_hecj_demo_viewpager_page3, null));
		
		initeCursor();
		myAdapter = new ViewPagerAdapter(lists); //设置布局集合
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(myAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) { // 当滑动式
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					if (currentItem == 1) {
						animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(offSet * 4 + 2 * bmWidth, 0, 0, 0);
					}
					break;
				case 1:
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, offSet * 2+ bmWidth, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(4 * offSet + 2* bmWidth, offSet * 2 + bmWidth, 0, 0);
					}
					break;
				case 2:
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, 4 * offSet + 2* bmWidth, 0, 0);
					} else if (currentItem == 1) {
						animation = new TranslateAnimation(offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth,0, 0);
					}
				}
				currentItem = arg0;
				animation.setDuration(500);
				animation.setFillAfter(true);
				imageView.startAnimation(animation);
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		textView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});
		textView2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});
		textView3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(2);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initeCursor() {
		cursor = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
		bmWidth = cursor.getWidth(); //得到图片的宽度
		DisplayMetrics dm = getResources().getDisplayMetrics(); //得到屏幕的尺寸
		offSet = (dm.widthPixels - 3 * bmWidth) / 6;
		matrix.setTranslate(offSet, 0);
		imageView.setImageMatrix(matrix); 
		currentItem = 0;
	}
}