package com.yakstudio.android.maxSms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
	final static String TAG ="SmsReceiver"; 

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive");
		//intent.getIntExtra(name, defaultValue)
	}

}
