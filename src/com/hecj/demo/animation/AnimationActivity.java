package com.hecj.demo.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.hecj.demo.R;

public class AnimationActivity extends Activity {
	private ImageView animation_imageView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.animation_layout);
		
		animation_imageView1 = (ImageView)this.findViewById(R.id.animation_imageView1);
		Button Button_alpha = (Button)this.findViewById(R.id.Button_alpha);
		Button Button_rotate = (Button)this.findViewById(R.id.Button_rotate);
		Button Button_scale = (Button)this.findViewById(R.id.Button_scale);
		Button Button_translate = (Button)this.findViewById(R.id.Button_translate);
		Button Button_complex = (Button)this.findViewById(R.id.Button_complex);
		Button_alpha.setOnClickListener(new MyOnClickLisentener(AnimationType.alpha));
		Button_rotate.setOnClickListener(new MyOnClickLisentener(AnimationType.rotate));
		Button_scale.setOnClickListener(new MyOnClickLisentener(AnimationType.scale));
		Button_translate.setOnClickListener(new MyOnClickLisentener(AnimationType.translate));
		Button_complex.setOnClickListener(new MyOnClickLisentener(AnimationType.complex));
		
	}
	/**
	 * 枚举
	 */
	enum AnimationType{
		alpha,
		rotate,
		scale,
		translate,
		complex
	}
	
	class MyOnClickLisentener implements OnClickListener{
		protected static final String TAG = "hecjlog";
		AnimationType animationType;
		public MyOnClickLisentener(AnimationType animationType) {
			this.animationType = animationType;
		}

		@Override
		public void onClick(View v) {
			switch (animationType) {
			
			case alpha:
				//透明度
				AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.1f);
				alphaAnimation.setFillEnabled(true);
				alphaAnimation.setRepeatCount(5);
				alphaAnimation.setDuration(3000);
				alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
				alphaAnimation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						Log.v(TAG, "onAnimationStart");
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						Log.v(TAG, "onAnimationRepeat");
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						Log.v(TAG, "onAnimationEnd");
					}
				});
				
				AlphaAnimation xmlAlpha = (AlphaAnimation) AnimationUtils.loadAnimation(AnimationActivity.this,R.anim.alphaanim );
				
				animation_imageView1.startAnimation(xmlAlpha);
				break;
			case rotate:
				//旋转
				RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
							0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation.setRepeatCount(3);
				rotateAnimation.setDuration(1000);
				animation_imageView1.startAnimation(rotateAnimation);
				break;
			case scale:
				//缩放
				ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				scaleAnimation.setRepeatCount(3);
				scaleAnimation.setDuration(1000);
				scaleAnimation.setRepeatMode(Animation.REVERSE);
				animation_imageView1.startAnimation(scaleAnimation);
				break;
			case translate:
				//移动
				TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 2f);
				translateAnimation.setRepeatCount(3);
				translateAnimation.setDuration(1000);
				translateAnimation.setRepeatMode(Animation.REVERSE);
				animation_imageView1.startAnimation(translateAnimation);
				break;
			case complex:
				AnimationSet animationSet = new AnimationSet(false);
				//组合
				TranslateAnimation translateAnimation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 2f);
				translateAnimation2.setRepeatCount(3);
				translateAnimation2.setDuration(1000);
				translateAnimation2.setRepeatMode(Animation.REVERSE);
				
				animationSet.addAnimation(translateAnimation2);

				ScaleAnimation scaleAnimation2 = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				scaleAnimation2.setRepeatCount(3);
				scaleAnimation2.setDuration(1000);
				scaleAnimation2.setRepeatMode(Animation.REVERSE);
				animationSet.addAnimation(scaleAnimation2);
				
				//旋转
				RotateAnimation rotateAnimation2 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
							0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation2.setRepeatCount(3);
				rotateAnimation2.setDuration(1000);
				animationSet.addAnimation(rotateAnimation2);
				animation_imageView1.startAnimation(animationSet);
				
				break;

			default:
				break;
			}
			
		}
	}
	
	
	
}
