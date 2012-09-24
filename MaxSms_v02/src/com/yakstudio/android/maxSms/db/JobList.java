package com.yakstudio.android.maxSms.db;

import android.content.ContentValues;
import android.database.Cursor;

public class JobList {
	//vars:
	public Long id;
	public Long job_id;
	public Long list_id;
	public Long created;
		
	public JobList(){ 
		id=null;
		job_id=null;		
		list_id=null;
		created=null;
		
	}
	
	public JobList(Cursor c){
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
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.JobLists.C_ID));
		job_id = cursor.getLong(cursor.getColumnIndex(DbHelper.JobLists.C_JOB_ID));
		list_id = cursor.getLong(cursor.getColumnIndex(DbHelper.JobLists.C_LIST_ID));
		created = cursor.getLong(cursor.getColumnIndex(DbHelper.JobLists.C_CREATED));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.JobLists.C_ID,id);
		values.put(DbHelper.JobLists.C_JOB_ID,job_id);
		values.put(DbHelper.JobLists.C_LIST_ID,list_id);
		values.put(DbHelper.JobLists.C_CREATED, created);
		
		return values; 
	}
}
