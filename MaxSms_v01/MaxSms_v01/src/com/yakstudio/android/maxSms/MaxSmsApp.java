package com.yakstudio.android.maxSms;

import com.yakstudio.android.maxSms.services.SendService;
import com.yakstudio.android.maxSms.services.SendService.SendServiceBinder;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MaxSmsApp extends Application{
	private SendService sendService=null;
	
	
	
	
	public void bindSendService(){
		if (sendService!=null){
			Intent intent = new Intent(this, SendService.class);
	        bindService(intent, SendServiceConnection, Context.BIND_AUTO_CREATE);
		}
	}
	
	public void unbindSendService(){
		if (sendService!=null){
            unbindService(SendServiceConnection);
            sendService=null;
        }
	}
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection SendServiceConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder service) {
			SendServiceBinder binder =(SendServiceBinder)service; 
			sendService=binder.getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			sendService=null;
		}        
    };
}
