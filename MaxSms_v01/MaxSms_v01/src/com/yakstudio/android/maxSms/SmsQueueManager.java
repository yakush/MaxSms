package com.yakstudio.android.maxSms;

import java.util.ArrayList;

import com.yakstudio.android.maxSms.database.Batch;
import com.yakstudio.android.maxSms.services.SendService;
import com.yakstudio.android.maxSms.services.SendService.SendServiceBinder;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class SmsQueueManager {
	private ArrayList<Batch> batchQueue=new ArrayList<Batch>();
	private Batch currentBatch=null;
	
	private Context context;
	private SendService sendService=null;
	
	SmsQueueManager(Context context){
		this.context=context;
	}
	
	////////////////////////////////////////////////////////
	// queue
	////////////////////////////////////////////////////////
	public void addBatch(Batch batch){
		batchQueue.add(batch);
		start();
	}
	
	public void removeBatch(Batch batch){
		if (sendService!=null && getCurrentBatch().equals(batch)){
			sendService.cancel();
			unbindSendService();
		}
		batchQueue.remove(batch);
		start();
	}
	
	public void removeAllBatches(){
		if (sendService!=null){
			sendService.cancel();
			unbindSendService();
		}
		batchQueue.clear();
	}
	
	public Batch getCurrentBatch(){
		return currentBatch;
	}
	
	////////////////////////////////////////////////////////
	// methods:  
	////////////////////////////////////////////////////////
	void start(){
		if (sendService==null && !batchQueue.isEmpty()){
			bindSendService();
			Batch batch=batchQueue.get(batchQueue.size()-1);
			sendService.setBatch(batch);
			sendService.start();
			currentBatch=batch;
		}
	}
	
	void pause(){
		if (sendService!=null){
			sendService.pause();
		}
	}
	
	
	////////////////////////////////////////////////////////
	// service stuff
	////////////////////////////////////////////////////////
	public void bindSendService(){
		if (sendService!=null){
			Intent intent = new Intent(context, SendService.class);
	        context.bindService(intent, sendServiceConnection, Context.BIND_AUTO_CREATE);
	        
		}
	}
	
	public void unbindSendService(){
		if (sendService!=null){
			context.unbindService(sendServiceConnection);
            sendService=null;
        }
	}
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection sendServiceConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder service) {
			SendServiceBinder binder =(SendServiceBinder)service; 
			sendService=binder.getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			sendService=null;
		}        
    };
}
