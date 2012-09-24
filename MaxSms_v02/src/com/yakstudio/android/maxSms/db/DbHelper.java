package com.yakstudio.android.maxSms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper {
	static final String TAG = "DbHelper";
	
	public static final String DB_NAME = "maxSms.db";
	public static final int DB_VERSION = 1006;
	
	private DbOpenHelper dbOpenHelper;
	private SQLiteDatabase db; 
	
	public DbHelper(Context context) {
		Log.d(TAG,"construct");
		dbOpenHelper= new DbOpenHelper(context);
		db = dbOpenHelper.getWritableDatabase();
	}
	
	public void close(){
		dbOpenHelper.close();
		db.close();
	}
	
	// Lists:
	public Lists lists = new Lists(); 
	public class Lists{
		//contacts table:
		public static final String TABLE_NAME = "lists";
		public static final String C_ID = "_id"; //INT
		public static final String C_NAME = "name"; //text
		public static final String C_CONTACTS_COUNT = "contacts_count"; //text
		public static final String C_CREATED = "created"; //INT
		
		public void createTable(SQLiteDatabase db){
			Log.d(TAG,"creating lists table");
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			
			String sql = "" +
					"CREATE TABLE " + TABLE_NAME 
					+ "(" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "," + C_NAME + " TEXT"
					+ "," + C_CONTACTS_COUNT + " INTEGER"
					+ "," + C_CREATED + " INTEGER"
					+ ")";
			
			Log.d(TAG,sql);
			db.execSQL(sql);
			//db.close();
			
		}
		
		public void dropTable(SQLiteDatabase db){
			Log.d(TAG,"dropping lists table");
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			Log.d(TAG,sql);
			
			db.execSQL(sql);
			//db.close();
		}
		
		
		public long insert(List list){
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res= db.insert(TABLE_NAME, null, list.toContentValues());
			//db.close();
			return res;
		}
		
		public long update(List list){
			ContentValues cv=list.toContentValues();
			cv.remove(C_ID);
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res= db.update(TABLE_NAME, list.toContentValues(),String.format("%s=%d", C_ID,list.id),null);
			//db.close();
			return res;
		}
		
		public Cursor query(){
			
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s DESC", C_CREATED));
			return result;
			
			/* !not good... makes the db writable....!
			
			Cursor result= db.rawQuery(""
					+ " SELECT main.* , count(j1." + Contacts.C_ID +") AS count "
					+ " FROM " + TABLE_NAME + " AS main "
					
					+ " LEFT JOIN " + Contacts.TABLE_NAME + " AS j1 "
					+ " ON main." + C_ID + " = j1." + Contacts.C_LIST_ID   
					
					+ " GROUP BY main." + C_ID
					+ " ORDER BY main." + C_CREATED + " DESC " 
					,null);
			
			return result;
			*/
		}
		
		public Cursor query(long id){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public long delete(long id){
			//remove contacts:
			db.delete(Contacts.TABLE_NAME, String.format("%s=%d",Contacts.C_LIST_ID, id),null);

			//remove list:
			long res=db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
			
			return res;
		}
		
		public long empty(long id){
			long res=db.delete(Contacts.TABLE_NAME, String.format("%s=%d",Contacts.C_LIST_ID, id),null);
			updateCount(id);
			return res;
		}
		
		public long countItems(long id){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			
			Cursor mCount= db.rawQuery("select count(*) from " + Contacts.TABLE_NAME + " WHERE " + Contacts.C_LIST_ID + " = " + id,null);
			mCount.moveToFirst();
			int count= mCount.getInt(0);
			mCount.close();
			//db.close();
			return count;
		}
		
		public long updateCount(long id){
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			
			
			/*String q=""
					+ " UPDATE " + TABLE_NAME + " SET "
					+ "("
					+ " SELECT count(" + Contacts.C_ID +") AS " + C_CONTACTS_COUNT
					+ " FROM " + Contacts.TABLE_NAME 
					+ " WHERE " +  Contacts.C_LIST_ID + " = " + id
					+ ")"
					+ " WHERE " + C_ID + " = " + id
					;
			*/
			/*
			String q=""
					+ " UPDATE " + TABLE_NAME + " SET "
					+ "`" + C_CONTACTS_COUNT + "`" + " = '" + countItems(id) +"'"  
					+ " WHERE `" + C_ID + "` = '" + id +"'"
					;
			
			Log.d(TAG,q);
			db.execSQL(q);
			Log.d(TAG,"execSQL done");
			*/
			
			ContentValues cv=new ContentValues();
			cv.put(C_CONTACTS_COUNT, countItems(id));
			
			long res= db.update(TABLE_NAME, cv ,String.format("%s=%d", C_ID,id),null);
			
			return res;
		}
	}
	
	
	// Contacts:
	public Contacts contacts = new Contacts();
	public class Contacts{
		//contacts table:
		public static final String TABLE_NAME = "contacts";
		public static final String C_ID = "_id"; //INT
		public static final String C_LIST_ID = "list_id"; //INT
		public static final String C_NAME = "name"; //text
		public static final String C_NUMBER = "number"; //text
		public static final String C_CREATED = "created"; //INT
		
		public void createTable(SQLiteDatabase db){
			Log.d(TAG,"creating contacts table");
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			
			String sql = "" +
					"CREATE TABLE " + TABLE_NAME 
					+ "(" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "," + C_LIST_ID+ " INTEGER"
					+ "," + C_NAME + " TEXT"
					+ "," + C_NUMBER + " TEXT"
					+ "," + C_CREATED + " INTEGER"
					+ ")";
			
			Log.d(TAG,sql);
			db.execSQL(sql);
			//db.close();
			
		}
		
		public void dropTable(SQLiteDatabase db){
			Log.d(TAG,"dropping contacts table");
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			Log.d(TAG,sql);
			
			db.execSQL(sql);
			//db.close();
		}
		
		
		public long insert(Contact contact){
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res = db.insert(TABLE_NAME, null, contact.toContentValues());
			
			//update list's count:
			lists.updateCount(contact.list_id);
			
			//db.close();
			return res;
		}
		
		public long update(Contact contact){
			ContentValues cv=contact.toContentValues();
			cv.remove(C_ID);
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res=db.update(TABLE_NAME, contact.toContentValues(),String.format("%s=%d", C_ID,contact.id),null);
			
			//update list's count:
			lists.updateCount(contact.list_id);
			
			//db.close();
			return res;
		}
		
		public Cursor query(){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s DESC", C_CREATED));
			return result;
			
		}
		
		public Cursor query(long id){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public Cursor queryByListId(long list_id){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_LIST_ID,list_id), null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public long delete(long id){
			
			Cursor cursor=query(id);
			cursor.moveToFirst();
			Contact contact=new Contact(cursor);
			cursor.close();
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res=db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
			
			
			//update list's count:
			lists.updateCount(contact.list_id);
			
			//db.close();
			return res;
		}
		
	}
	
	// Jobs:
	public Jobs jobs = new Jobs();
	public class Jobs{
		//contacts table:
		public static final String TABLE_NAME = "jobs";
		public static final String C_ID = "_id"; //INT
		public static final String C_MESSAGE = "message"; //TEXT
		public static final String C_STATUS = "status"; //INT
		public static final String C_CREATED = "created"; //INT
		
		public static final int STATUS_PENDING = 	0;
		public static final int STATUS_RUNNIG = 	1;
		public static final int STATUS_CANCELED = 	2;
		public static final int STATUS_ERROR = 		3;
		
		public void createTable(SQLiteDatabase db){
			Log.d(TAG,"creating " + TABLE_NAME + " table");
			
			String sql = "" +
					"CREATE TABLE " + TABLE_NAME 
					+ "(" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "," + C_MESSAGE+ " TEXT"
					+ "," + C_STATUS + " INTEGER"
					+ "," + C_CREATED + " INTEGER"
					+ ")";
			
			Log.d(TAG,sql);
			db.execSQL(sql);
		}
		
		public void dropTable(SQLiteDatabase db){
			Log.d(TAG,"dropping " + TABLE_NAME + " table");
			
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			Log.d(TAG,sql);
			db.execSQL(sql);
		}
		
		public long insert(Job job){
			long res = db.insert(TABLE_NAME, null, job.toContentValues());
			return res;
		}
		
		public long update(Job job){
			ContentValues cv=job.toContentValues();
			cv.remove(C_ID);
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res=db.update(TABLE_NAME, job.toContentValues(),String.format("%s=%d", C_ID,job.id),null);

			return res;
		}
		
		public Cursor query(){
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public Cursor query(long id){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public long delete(long id){
			long res=db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
			return res;
		}
		
	}
	
	// JobLists
	public JobLists jobLists= new JobLists();
	public class JobLists{
		//cols
		public static final String TABLE_NAME = "jobLists";
		public static final String C_ID = "_id"; //INT
		public static final String C_JOB_ID = "job_id"; //long
		public static final String C_LIST_ID = "list_id"; //long
		public static final String C_CREATED = "created"; //INT
		
		public void createTable(SQLiteDatabase db){
			Log.d(TAG,"creating " + TABLE_NAME + " table");
			
			String sql = "" +
					"CREATE TABLE " + TABLE_NAME 
					+ "(" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "," + C_JOB_ID+ " INTEGER"
					+ "," + C_LIST_ID + " INTEGER"
					+ "," + C_CREATED + " INTEGER"
					+ ")";
			
			Log.d(TAG,sql);
			db.execSQL(sql);
		}
		
		public void dropTable(SQLiteDatabase db){
			Log.d(TAG,"dropping " + TABLE_NAME + " table");
			
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			Log.d(TAG,sql);
			db.execSQL(sql);
		}
		
		public long insert(JobList jobList){
			long res = db.insert(TABLE_NAME, null, jobList.toContentValues());
			return res;
		}
		
		public long update(JobList jobList){
			ContentValues cv=jobList.toContentValues();
			cv.remove(C_ID);
			
			//SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			long res=db.update(TABLE_NAME, jobList.toContentValues(),String.format("%s=%d", C_ID,jobList.id),null);

			return res;
		}
		
		public Cursor query(){
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public Cursor query(long id){
			//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public long delete(long id){
			long res=db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
			return res;
		}
		
	}
	
	// JobLists
	public JobContacts jobContacts= new JobContacts();
	public class JobContacts{
		//cols
		public static final String TABLE_NAME = "jobContacts";
		public static final String C_ID = "_id"; //INT
		public static final String C_JOB_ID = "job_id"; //long
		public static final String C_CONTACT_ID = "contact_id"; //long
		public static final String C_STATUS = "status"; //int
		public static final String C_RETRIES = "retries"; //int
		public static final String C_CREATED = "created"; //INT
		
		public static final int STATUS_PENDING = 	0;
		public static final int STATUS_SENDING = 	1;
		public static final int STATUS_SENT = 		2;
		public static final int STATUS_RECIEVED = 	3;
		public static final int STATUS_ERROR = 		4;
		
		public void createTable(SQLiteDatabase db){
			Log.d(TAG,"creating " + TABLE_NAME + " table");
			
			String sql = "" +
					"CREATE TABLE " + TABLE_NAME 
					+ "(" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "," + C_JOB_ID+ " INTEGER"
					+ "," + C_CONTACT_ID + " INTEGER"
					+ "," + C_STATUS + " INTEGER"
					+ "," + C_RETRIES + " INTEGER"
					+ "," + C_CREATED + " INTEGER"
					+ ")";
			
			Log.d(TAG,sql);
			db.execSQL(sql);
		}
		
		public void dropTable(SQLiteDatabase db){
			Log.d(TAG,"dropping " + TABLE_NAME + " table");
			
			String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
			Log.d(TAG,sql);
			db.execSQL(sql);
		}
		
		public long insert(JobContact jobContact){
			long res = db.insert(TABLE_NAME, null, jobContact.toContentValues());
			return res;
		}
		
		public long update(JobContact jobContact){
			ContentValues cv=jobContact.toContentValues();
			cv.remove(C_ID);
			
			long res=db.update(TABLE_NAME, jobContact.toContentValues(),String.format("%s=%d", C_ID,jobContact.id),null);

			return res;
		}
		
		public Cursor query(){
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public Cursor query(long id){
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s DESC", C_CREATED));
			return result;
		}
		
		public long delete(long id){
			long res=db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
			return res;
		}
		
	}
	//////////////////////////////////////////
	//OPEN HELPER:
	private class DbOpenHelper extends SQLiteOpenHelper{
		private static final String TAG = "DbOpenHelper";
		
		public DbOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			Log.d(TAG,"constructor");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG,"onCreate");
			
			//created tables:
			lists.createTable(db);
			contacts.createTable(db);
			jobs.createTable(db);
			jobLists.createTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG,"onUpgrade");
			
			//drop all tables for now (LOSE ALL DATA!):
			lists.dropTable(db);
			contacts.dropTable(db);
			jobs.dropTable(db);
			jobLists.dropTable(db);
			
			//recreate tables:
			onCreate(db);			
		}
	
	}
}
