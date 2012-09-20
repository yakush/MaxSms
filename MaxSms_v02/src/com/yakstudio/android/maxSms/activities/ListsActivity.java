package com.yakstudio.android.maxSms.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yakstudio.android.maxSms.Activity;
import com.yakstudio.android.maxSms.Constants;
import com.yakstudio.android.maxSms.R;
import com.yakstudio.android.maxSms.db.Contact;
import com.yakstudio.android.maxSms.db.DbHelper;
import com.yakstudio.android.maxSms.db.List;


public class ListsActivity extends Activity implements OnClickListener{
	static final String TAG="ListsActivity";
	
	private ListView list_lists;
	private Button btn_add;
	
	private Cursor cursor;
	private Adapter adapter;
	
	private DbHelper db;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        
        //DB:
        db=new DbHelper(ListsActivity.this);
        
        list_lists=(ListView)findViewById(R.id.activity_lists_list_lists);
        btn_add=(Button)findViewById(R.id.activity_lists_btn_add);
        
        //
        btn_add.setOnClickListener(this);
        
        registerForContextMenu(list_lists);
		cursor=null;
		adapter = new Adapter(this, R.layout.listitem_lists, cursor);
		list_lists.setAdapter(adapter);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		populateList();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		closeCursor();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"onDestroy");
		db.close();
	}
	////////////////////////////////////////
	// HELPERS:
	private void closeCursor(){
		Log.d(TAG,"close cursor");
		if (cursor!=null && !cursor.isClosed()){
			Log.d(TAG,"DONE");
			cursor.close();
		}else{
			Log.d(TAG,"NOT..."+(cursor!=null ? "true":"null"));
		}
	}
	
	private void populateList() {
		Log.d(TAG,"Loading lists");
		
		class Task extends AsyncTask<Void, Void, Cursor>{

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				Log.d(TAG,"loading - pre...");
			}
			
			@Override
			protected Cursor doInBackground(Void... params) {
				Log.d(TAG,"loading - do in bkg...");
				//DbHelper db=new DbHelper(ListsActivity.this);
				
				return db.lists.query();
				
			}
			
			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}
			
			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
				Log.d(TAG,"loading - post...");
				
				adapter.changeCursor(result);
				closeCursor();
				ListsActivity.this.cursor=result;
			}		
		};
		
		Task task= new Task();
		task.execute();
		
	} 

	private class Adapter extends ResourceCursorAdapter{

		public Adapter(Context context, int layout, Cursor c) {
			super(context, layout, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView name = (TextView)view.findViewById(R.id.listitem_lists_txt_name);
			TextView extra = (TextView)view.findViewById(R.id.listitem_lists_txt_extra);
			
			final List list = new List(cursor);
			
			name.setText(list.name);
			extra.setText(list.contacts_count.toString() + " numbers");
			
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.d(TAG,String.format("click %d : %s",list.id,list.name));
	    			
	    			Intent intent = new Intent(ListsActivity.this,ListItemsActivity.class);
	    			intent.putExtra(Constants.INTENT_EXTRA_KEY_LIST_ID, list.id);
	    			startActivity(intent);
	    		}
			});
		}
		
	}
	
	
	// --------------------------------------------
	// context menu
	// --------------------------------------------
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		Log.d(TAG,"contextMenu");
		
		switch (v.getId()){
		case R.id.activity_lists_list_lists:
			try {
				AdapterView.AdapterContextMenuInfo info;
				info = (AdapterView.AdapterContextMenuInfo)menuInfo;
				Log.d(TAG, String.format("item id %d: position: %d",info.id,info.position));
				
				if(cursor!=null){
					cursor.moveToPosition(info.position);
					if(!cursor.isAfterLast() && !cursor.isBeforeFirst()){
						//List list = new List(cursor);
						
						MenuInflater inflater = getMenuInflater();
					    inflater.inflate(R.menu.menu_listitem_lists, menu);
						
					}
				}
			    
			} catch (ClassCastException  e) {
				Log.d(TAG, "bad menuInfo", e);
			}
		}
	}
	// --------------------------------------------
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info=null;
		try {
			info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		} catch (ClassCastException  e) {
			Log.d(TAG, "bad menuInfo", e);
		}
		
		switch (item.getItemId()) {
		case R.id.menuitem_listitem_lists_delete:
			if(info !=null && cursor!=null){
				cursor.moveToPosition(info.position);
				if(!cursor.isAfterLast() && !cursor.isBeforeFirst()){
					List list= new List(cursor);
					Log.d(TAG,String.format("delete : id - %d",list.id));
					deleteList(list);
					return true;
				
				}
			}
			
		case R.id.menuitem_listitem_lists_import:
			if(info !=null && cursor!=null){
				cursor.moveToPosition(info.position);
				if(!cursor.isAfterLast() && !cursor.isBeforeFirst()){
					List list= new List(cursor);
					Log.d(TAG,String.format("import : id - %d",list.id));
					
					importList(list);
					return true;
				
				}
			}
			
			
		}
		
		
		
		
		return super.onContextItemSelected(item);
	}
		
	
	
	public void addNewList(){
		Log.d(TAG,"add");
		
		final EditText input = new EditText(this);
		
		new AlertDialog.Builder(this)
	    .setTitle("New List")
	    .setMessage("New List Name")
	    .setView(input)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            //Editable value = input.getText();
	        	String name = input.getText().toString().trim();
	        	if (!name.isEmpty()){
		        	//DbHelper db=new DbHelper(ListsActivity.this);
		    		List list=new List();
		    		list.name=name;
		    		list.created=System.currentTimeMillis();
		    		
		    		db.lists.insert(list);
		    		populateList();
	        	}else{
	        		Toast.makeText(ListsActivity.this, "list name cannot be empty", Toast.LENGTH_LONG).show();
	        	}
	        }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            // Do nothing.
	        }
	    }).show();
		
		
		
	}
	
	private void importList(final List list) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("Importing");
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		
		class Task extends AsyncTask<Long, Void, Void>{

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				Log.d(TAG,"importList - pre...");
				dialog.show();
				//closeCursor();
			}
			
			@Override
			protected Void doInBackground(Long... params) {
				Log.d(TAG,"importList - do in bkg...");
				//DbHelper db=new DbHelper(ListsActivity.this);
				
				for (int i=0;i<100;i++){
					//create contact:
					Contact contact = new Contact();
					contact.name=String.format("test %d",i);
					contact.number=String.format("wrong number %d",i);
					contact.list_id=list.id;
					
					db.contacts.insert(contact);
				}
				//db.lists.updateCount(list.id);
				return null;
			}
			
			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Log.d(TAG,"importList - post...");
				dialog.dismiss();
				populateList();
			}		
		};
		
		Task task= new Task();
		task.execute(list.id);
		
	}

	
	private void deleteList(List list) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("Deleting");
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		
		class Task extends AsyncTask<Long, Void, Void>{

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				Log.d(TAG,"delete - pre...");
				dialog.show();
			}
			
			@Override
			protected Void doInBackground(Long... params) {
				Log.d(TAG,"delete - do in bkg...");
				//DbHelper db=new DbHelper(ListsActivity.this);
				
				db.lists.delete(params[0]);
				Log.d(TAG,"closing db");
				
				return null;
			}
			
			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Log.d(TAG,"delete - post...");
				dialog.dismiss();
				populateList();
			}		
		};
		
		Task task= new Task();
		task.execute(list.id);
		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_lists_btn_add:
			addNewList();
			break;

		
		}
		
	}
}
