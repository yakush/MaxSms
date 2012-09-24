package com.yakstudio.android.maxSms.db;

import android.content.ContentValues;
import android.database.Cursor;

public class JobContact {
	//vars:
	public Long id;
	public Long job_id;
	public Long contact_id;
	public Integer status;
	public Integer retries;
	public Long created;
		
	public JobContact(){ 
		id=null;
		job_id=null;
		contact_id=null;
		status=DbHelper.JobContacts.STATUS_PENDING;
		retries=0;
		created=null;
		
	}
	
	public JobContact(Cursor c){
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
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.JobContacts.C_ID));
		job_id = cursor.getLong(cursor.getColumnIndex(DbHelper.JobContacts.C_JOB_ID));
		contact_id = cursor.getLong(cursor.getColumnIndex(DbHelper.JobContacts.C_CONTACT_ID));
		status = cursor.getInt(cursor.getColumnIndex(DbHelper.JobContacts.C_STATUS));
		retries = cursor.getInt(cursor.getColumnIndex(DbHelper.JobContacts.C_RETRIES));
		created = cursor.getLong(cursor.getColumnIndex(DbHelper.JobContacts.C_CREATED));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.JobContacts.C_ID,id);
		values.put(DbHelper.JobContacts.C_JOB_ID,job_id);
		values.put(DbHelper.JobContacts.C_CONTACT_ID,contact_id);
		values.put(DbHelper.JobContacts.C_STATUS,status);
		values.put(DbHelper.JobContacts.C_RETRIES,retries);
		values.put(DbHelper.JobContacts.C_CREATED, created);
		
		return values; 
	}
}
