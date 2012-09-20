package com.yakstudio.android.maxSms.db;

import com.yakstudio.android.maxSms.db.DbHelper.Lists;

import android.content.ContentValues;
import android.database.Cursor;

public class List {
	//vars:
	public Long id;
	public String name;
	public Long created;
	public Long contacts_count;
		
	public List(){ 
		id=null;
		name=null;		
		created=null;
		contacts_count = null;
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
		contacts_count = cursor.getLong(cursor.getColumnIndex(DbHelper.Lists.C_CONTACTS_COUNT));
		created = cursor.getLong(cursor.getColumnIndex(DbHelper.Lists.C_CREATED));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.Lists.C_ID,id);
		values.put(DbHelper.Lists.C_NAME,name);
		values.put(Lists.C_CONTACTS_COUNT,contacts_count);
		values.put(DbHelper.Lists.C_CREATED, created);
		
		
		
		return values; 
	}
}
