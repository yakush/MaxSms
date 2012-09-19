package com.yakstudio.android.maxSms.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Batch {
	//consts: 
	public static final int STATUS_CREATED		= 1;
	public static final int STATUS_QUEUED		= 2;
	public static final int STATUS_RUNNING		= 3;
	public static final int STATUS_PAUSED		= 4;
	public static final int STATUS_CANCELED		= 5;
	public static final int STATUS_FINISHED		= 6;
	
	//vars:
	public Long id;
	public String message;
	public Integer status;
	public Long startTime;
	public Long endTime;
		
	public Batch(){ 
		id=null;
		message=null;
		status=null;
		startTime=null;
		endTime=null;
	}
	
	public Batch(Cursor c){
		this();
		fromCursor(c);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass()==Batch.class){
			Batch action=(Batch)o; 
			return (action.id!=null && id!=null && action.id==id);  
		}
		
		return super.equals(o);
	}
	public void fromCursor(Cursor cursor){
		if (cursor==null){
			throw new IllegalStateException("this should only be called when the cursor is valid");
		}
		
		if (cursor.isClosed()){
			throw new IllegalStateException("this should only be called when the cursor is not closed");
		}
		
		if (cursor.isAfterLast() || cursor.isBeforeFirst()){
			throw new IllegalStateException("this should only be called when the cursor is pointing to row in range");
		}
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.Batches.C_ID));
		message = cursor.getString(cursor.getColumnIndex(DbHelper.Batches.C_MESSAGE));
		status = cursor.getInt(cursor.getColumnIndex(DbHelper.Batches.C_STATUS));
		startTime = cursor.getLong(cursor.getColumnIndex(DbHelper.Batches.C_START_TIME));
		endTime = cursor.getLong(cursor.getColumnIndex(DbHelper.Batches.C_END_TIME));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.Batches.C_ID,id);
		values.put(DbHelper.Batches.C_MESSAGE,id);
		values.put(DbHelper.Batches.C_STATUS,id);
		values.put(DbHelper.Batches.C_START_TIME,id);
		values.put(DbHelper.Batches.C_END_TIME,id);
		
		return values; 
	}
}
