package com.yakstudio.android.maxSms.database;

import android.content.ContentValues;
import android.database.Cursor;

public class List {
	//vars:
	public Long id;
	public String name;
		
	public List(){ 
		id=null;
		name=null;		
	}
	
	public List(Cursor c){
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
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.Lists.C_ID));
		name = cursor.getString(cursor.getColumnIndex(DbHelper.Lists.C_NAME));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.Lists.C_ID,id);
		values.put(DbHelper.Lists.C_NAME,name);
		
		return values; 
	}
}
