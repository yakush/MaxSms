package com.yakstudio.android.maxSms.services;

import com.yakstudio.android.maxSms.database.Batch;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SendService extends Service {
	private final IBinder binder = new SendServiceBinder();
	private Batch batch=null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	
	/////////////
	//Binder
	public class SendServiceBinder extends Binder{
		public SendService getService(){
			return SendService.this;
		}
	}
	
	/////////////
	// client methods:
	
	public Batch getBatch(){
		return this.batch;
	}
	
	public void setBatch(Batch batch){
		this.batch=batch;
	}
	
	public void start(){
		if (getBatch()!=null){
			
		}else{
			throw new NullPointerException("The batch was not set");
		}
	}
	
	public void pause(){
		if (getBatch()!=null){
			
		}else{
			throw new NullPointerException("The batch was not set");
		}
	}
	
	public void resume(){
		if (getBatch()!=null){
			
		}else{
			throw new NullPointerException("The batch was not set");
		}
	}
	
	public void cancel(){
		if (getBatch()!=null){
			
		}else{
			throw new NullPointerException("The batch was not set");
		}
	}
	
	public void retry(){
		if (getBatch()!=null){
			
		}else{
			throw new NullPointerException("The batch was not set");
		}
	}
	
}
