package com.hecj.demo.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		/*Intent _intent = new Intent(context,ClockActivity.class);
		_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(_intent);*/
		
		/*SmsManager sms = SmsManager.getDefault();
		PendingIntent _p = PendingIntent.getActivity(context, 0, new Intent(), 0);
		sms.sendTextMessage("5556", null, "send a sms!", _p,  null) ;*/
		Toast.makeText(context, "¥•∑¢¡À...", Toast.LENGTH_SHORT).show();
		
		
		
	}

}
