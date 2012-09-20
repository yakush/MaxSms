package com.yakstudio.android.maxSms.db;

import android.content.ContentValues;
import android.database.Cursor;

public class Contact {
	//vars:
	public Long id;
	public long list_id;
	public String name;
	public String number;
	public Long created;
		
	public Contact(){ 
		id=null;
		name=null;		
		number=null;
		created=null;
	}
	
	public Contact(Cursor c){
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
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.Contacts.C_ID));
		list_id= cursor.getLong(cursor.getColumnIndex(DbHelper.Contacts.C_LIST_ID));
		name = cursor.getString(cursor.getColumnIndex(DbHelper.Contacts.C_NAME));
		number = cursor.getString(cursor.getColumnIndex(DbHelper.Contacts.C_NUMBER));
		created = cursor.getLong(cursor.getColumnIndex(DbHelper.Contacts.C_CREATED));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.Contacts.C_ID,id);
		values.put(DbHelper.Contacts.C_LIST_ID,list_id);
		values.put(DbHelper.Contacts.C_NAME,name);
		values.put(DbHelper.Contacts.C_NUMBER,number);
		values.put(DbHelper.Contacts.C_CREATED, created);
		
		return values; 
	}
}
