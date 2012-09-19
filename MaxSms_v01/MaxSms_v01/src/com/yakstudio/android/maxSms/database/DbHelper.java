package com.yakstudio.android.maxSms.database;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper {
	static final String TAG = "DbHelper";
	
	public static final String DB_NAME = "maxSms.db";
	public static final int DB_VERSION = 1001;

	
	//actions table:
	public static final String TABLE_ACTIONS = "moods";
	//action items table:
	public static final String TABLE_ACTIONS_ITEMS = "moods";
	//action lists table:
	public static final String TABLE_ACTIONS_LISTS = "moods";
	
	
	private DbOpenHelper dbOpenHelper;
	
	public DbHelper(Context context) {
		Log.d(TAG,"construct");
		dbOpenHelper= new DbOpenHelper(context);
	}
	
	// Contacts:
	public class Contacts{
		//contacts table:
		public static final String TABLE_NAME = "contacts";
		public static final String C_ID = "_id"; //INT
		public static final String C_NAME = "name"; //text
		public static final String C_NUMBER = "number"; //text
		
		public long insert(Contact contact){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.insert(TABLE_NAME, null, contact.toContentValues());
		}
		
		public long update(Contact contact){
			ContentValues cv=contact.toContentValues();
			cv.remove(C_ID);
			
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.update(TABLE_NAME, contact.toContentValues(),String.format("%s=%d", C_ID,contact.id),null);
		}
		
		public Cursor query(){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s ASC", C_NAME));
			return result;
		}
		
		public Cursor query(long id){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s ASC", C_NAME));
			return result;
		}
		
		public long delete(long id){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
		}
	}
	
	// Lists:
	public class Lists{
		//contacts table:
		public static final String TABLE_NAME = "lists";
		public static final String C_ID = "_id"; //INT
		public static final String C_NAME = "name"; //text
		
		public long insert(Contact contact){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.insert(TABLE_NAME, null, contact.toContentValues());
		}
		
		public long update(Contact contact){
			ContentValues cv=contact.toContentValues();
			cv.remove(C_ID);
			
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.update(TABLE_NAME, contact.toContentValues(),String.format("%s=%d", C_ID,contact.id),null);
		}
		
		public Cursor query(){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s ASC", C_NAME));
			return result;
		}
		
		public Cursor query(long id){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s ASC", C_NAME));
			return result;
		}
		
		public long delete(long id){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
		}
	}
	
	
	// ListItems:
	public class ListItems{
		//contacts table:
		public static final String TABLE_NAME = "listItems";
		public static final String C_ID = "_id"; //INT
		public static final String C_LIST_ID = "list_id"; //INT
		public static final String C_CONTACT_ID = "contact_id"; //INT
		
		public long insert(ListItem item){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.insert(TABLE_NAME, null, item.toContentValues());
		}
		
		public long update(ListItem item){
			ContentValues cv=item.toContentValues();
			cv.remove(C_ID);
			
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.update(TABLE_NAME, item.toContentValues(),String.format("%s=%d", C_ID,item.id),null);
		}
		
		public Cursor query(){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public Cursor query(long id){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public long delete(long id){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
		}
	}
	
	
	public class Batches{
		//contacts table:
		public static final String TABLE_NAME = "batches";
		public static final String C_ID = "_id"; //INT
		public static final String C_MESSAGE = "message"; //INT
		public static final String C_STATUS = "status"; //INT
		public static final String C_START_TIME = "start_time"; //TIME
		public static final String C_END_TIME = "end_time"; //TIME
		
		public long insert(Batch item){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.insert(TABLE_NAME, null, item.toContentValues());
		}
		
		public long update(Batch item){
			ContentValues cv=item.toContentValues();
			cv.remove(C_ID);
			
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.update(TABLE_NAME, item.toContentValues(),String.format("%s=%d", C_ID,item.id),null);
		}
		
		public Cursor query(){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public Cursor query(long id){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public long delete(long id){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
		}
	}
	// //////////
	public class BatchLists{
		//contacts table:
		public static final String TABLE_NAME = "batchLists";
		public static final String C_ID = "_id"; //INT
		public static final String C_BATCH_ID = "batch_id"; //INT
		public static final String C_LIST_ID = "list_id"; //INT
		
		
		public long insert(ListItem item){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.insert(TABLE_NAME, null, item.toContentValues());
		}
		
		public long update(ListItem item){
			ContentValues cv=item.toContentValues();
			cv.remove(C_ID);
			
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.update(TABLE_NAME, item.toContentValues(),String.format("%s=%d", C_ID,item.id),null);
		}
		
		public Cursor query(){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public Cursor query(long id){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public long delete(long id){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
		}
	}
	// //////////
	public class BatchSendStatuses{
		//contacts table:
		public static final String TABLE_NAME = "batchSendStatuses";
		public static final String C_ID = "_id"; //INT
		public static final String C_BATCH_ID = "batch_id"; //INT
		public static final String C_CONTACT_ID = "contact_id"; //INT
		public static final String C_STATUS = "status"; //INT
		
		public long insert(ListItem item){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.insert(TABLE_NAME, null, item.toContentValues());
		}
		
		public long update(ListItem item){
			ContentValues cv=item.toContentValues();
			cv.remove(C_ID);
			
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.update(TABLE_NAME, item.toContentValues(),String.format("%s=%d", C_ID,item.id),null);
		}
		
		public Cursor query(){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, null, null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public Cursor query(long id){
			SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
			Cursor result=db.query(TABLE_NAME, null, String.format("%s=%d", C_ID,id), null, null, null, String.format("%s ASC", C_ID));
			return result;
		}
		
		public long delete(long id){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			return db.delete(TABLE_NAME, String.format("%s=%d",C_ID, id),null);
		}
	}
	// //////////
	
	// DbHelper class:
	private class DbOpenHelper extends SQLiteOpenHelper {

		private static final String TAG = "DbOpenHelper";

		public DbOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			Log.d(TAG,"constructor");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG,"onCreate");
			
			String sql = "" +
					"CREATE TABLE " + Contacts.TABLE_NAME 
					+ "(" + Contacts.C_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT"
//					+ "," + C_MOODS_VALUE + " INTEGER"
//					+ "," + C_MOODS_NOTE + " TEXT"
//					+ "," + C_MOODS_PHOTO_FILE + " INTEGER"
//					+ "," + C_MOODS_LOGICAL_DAY + " INTEGER"
//					+ "," + C_MOODS_SHARED_FLAGS+ " INTEGER"
//					+ "," + C_MOODS_CREATED_LOCALLY + " BOOL"
//					+ "," + C_MOODS_LAST_CHANGED + " INTEGER"
//					+ "," + C_MOODS_COMMITED+ " BOOL"
//					+ "," + C_MOODS_DUMMY+ " BOOL"
					+ ")";
			
			Log.d(TAG,sql);
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG,"onUpgrade");
			db.execSQL("DROP TABLE IF EXISTS " + Contacts.TABLE_NAME);
			onCreate(db);
		}
	}
}
