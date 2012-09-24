package com.yakstudio.android.maxSms.db;

import android.content.ContentValues;
import android.database.Cursor;

public class Job {
	//vars:
	public Long id;
	public String message;
	public Integer status;
	public Long created;
		
	public Job(){ 
		id=null;
		message=null;		
		status=DbHelper.Jobs.STATUS_PENDING;
		created=null;
		
	}
	
	public Job(Cursor c){
		this();
		fromCursor(c);
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
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.Jobs.C_ID));
		message = cursor.getString(cursor.getColumnIndex(DbHelper.Jobs.C_MESSAGE));
		status = cursor.getInt(cursor.getColumnIndex(DbHelper.Jobs.C_STATUS));
		created = cursor.getLong(cursor.getColumnIndex(DbHelper.Jobs.C_CREATED));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.Jobs.C_ID,id);
		values.put(DbHelper.Jobs.C_MESSAGE,message);
		values.put(DbHelper.Jobs.C_STATUS,status);
		values.put(DbHelper.Jobs.C_CREATED, created);
		
		return values; 
	}
}
