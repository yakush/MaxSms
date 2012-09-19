package com.yakstudio.android.maxSms.activities;

import java.util.ArrayList;

import com.yakstudio.android.maxSms.Activity;
import com.yakstudio.android.maxSms.R;
import com.yakstudio.android.maxSms.R.id;
import com.yakstudio.android.maxSms.R.layout;
import com.yakstudio.android.maxSms.R.menu;
import com.yakstudio.android.maxSms.receivers.SmsReceiver;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	final static String TAG ="MainActivity"; 

	Button btn_send;
	TextView txt_number;
	TextView txt_message;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_send=(Button)findViewById(R.id.activity_main_btn_send);
        txt_number=(TextView)findViewById(R.id.activity_main_txt_number);
        txt_message=(TextView)findViewById(R.id.activity_main_txt_message);
        
        
        btn_send.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		sendSms(txt_number.getText().toString(),txt_message.getText().toString());
	}
	
	private void sendSms(String number,String message){
		SmsManager smsManager = SmsManager.getDefault();
		
		ArrayList<String> parts= smsManager.divideMessage(message);
		
		
		if(parts.size()>1){
			try {
				//Intent sentIntents=null;
				//Intent deliveryIntents=null;
				//send multipart:
				Log.d(TAG,"sending multi part");
				smsManager.sendMultipartTextMessage(number, null, parts, null,null);
			} catch (IllegalArgumentException e) {
				Log.d(TAG, "IllegalArgument");
			}
		}else{
			//send single:
			try {
				//send single:
				PendingIntent sentIntent=PendingIntent.getBroadcast(this, -1, new Intent(this,SmsReceiver.class), 0);
				PendingIntent deliveryIntent=PendingIntent.getBroadcast(this, -1, new Intent(this,SmsReceiver.class), 0);
				Log.d(TAG,"sending single part");
				smsManager.sendTextMessage(number, null, message, sentIntent, deliveryIntent);
			} catch (IllegalArgumentException e) {
				Log.d(TAG, "IllegalArgument");
			}
		}
	}
}
