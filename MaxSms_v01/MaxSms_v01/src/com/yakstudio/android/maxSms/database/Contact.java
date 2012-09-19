package com.yakstudio.android.maxSms.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Contact {
	//vars:
	public Long id;
	public String name;
	public String number;
		
	public Contact(){ 
		id=null;
		name=null;
		number=null;
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
		name = cursor.getString(cursor.getColumnIndex(DbHelper.Contacts.C_NAME));
		number = cursor.getString(cursor.getColumnIndex(DbHelper.Contacts.C_NUMBER));
		
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.Contacts.C_ID,id);
		values.put(DbHelper.Contacts.C_NAME,name);
		values.put(DbHelper.Contacts.C_NUMBER,number);
		return values; 
	}
}
