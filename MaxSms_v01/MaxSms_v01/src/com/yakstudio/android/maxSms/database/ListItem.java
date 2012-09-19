package com.yakstudio.android.maxSms.database;

import android.content.ContentValues;
import android.database.Cursor;

public class ListItem {
	//vars:
	public Long id;
	public Long listID;
	public Long contactID;
		
	public ListItem(){ 
		id=null;
		listID=null;
		contactID=null;
	}
	
	public ListItem(Cursor c){
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
		
		
		id = cursor.getLong(cursor.getColumnIndex(DbHelper.ListItems.C_ID));
		listID = cursor.getLong(cursor.getColumnIndex(DbHelper.ListItems.C_LIST_ID));
		contactID = cursor.getLong(cursor.getColumnIndex(DbHelper.ListItems.C_CONTACT_ID));
	}
	
	
	public ContentValues toContentValues(){
		ContentValues values= new ContentValues();
		values.put(DbHelper.ListItems.C_ID,id);
		values.put(DbHelper.ListItems.C_LIST_ID,listID);
		values.put(DbHelper.ListItems.C_CONTACT_ID,contactID);
		
		return values; 
	}

}
