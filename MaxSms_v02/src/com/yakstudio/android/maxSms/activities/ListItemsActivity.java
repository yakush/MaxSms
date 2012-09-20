package com.yakstudio.android.maxSms.activities;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.yakstudio.android.maxSms.Activity;
import com.yakstudio.android.maxSms.Constants;
import com.yakstudio.android.maxSms.R;
import com.yakstudio.android.maxSms.db.Contact;
import com.yakstudio.android.maxSms.db.DbHelper;

public class ListItemsActivity extends Activity{
	static final String TAG="ListItemsActivity";
	
	private ListView list_items;
	//private Button btn_add;
	
	private Cursor cursor;
	private Adapter adapter;
	
	private DbHelper db;
	
	private long list_id;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitems);
        
        Intent callingIntent = getIntent();
        list_id=callingIntent.getLongExtra(Constants.INTENT_EXTRA_KEY_LIST_ID, -1);
        //DB:
        db=new DbHelper(this);
        
        list_items=(ListView)findViewById(R.id.activity_listitems_list_items);
        //btn_add=(Button)findViewById(R.id.activity_lists_btn_add);
        
        //
        //btn_add.setOnClickListener(this);
        
        registerForContextMenu(list_items);
		cursor=null;
		adapter = new Adapter(this, R.layout.listitem_listitems, cursor);
		list_items.setAdapter(adapter);
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
	
	// //////////////////////////////////////
	// HELPERS:
	private void closeCursor() {
		Log.d(TAG, "close cursor");
		if (cursor != null && !cursor.isClosed()) {
			Log.d(TAG, "DONE");
			cursor.close();
		} else {
			Log.d(TAG, "NOT..." + (cursor != null ? "true" : "null"));
		}
	}
	
	private void populateList() {
		Log.d(TAG, "Loading lists");

		class Task extends AsyncTask<Void, Void, Cursor> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				Log.d(TAG, "loading - pre...");
			}

			@Override
			protected Cursor doInBackground(Void... params) {
				Log.d(TAG, "loading - do in bkg...");
				// DbHelper db=new DbHelper(ListsActivity.this);

				return db.contacts.queryByListId(list_id);

			}

			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
				Log.d(TAG, "loading - post...");

				adapter.changeCursor(result);
				closeCursor();
				ListItemsActivity.this.cursor = result;
			}
		}
		;

		Task task = new Task();
		task.execute();

	}
	
	private class Adapter extends ResourceCursorAdapter {

		public Adapter(Context context, int layout, Cursor c) {
			super(context, layout, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView name = (TextView) view
					.findViewById(R.id.listitem_listitems_txt_name);
			TextView number = (TextView) view
					.findViewById(R.id.listitem_listitems_txt_number);

			final Contact contact = new Contact(cursor);

			name.setText(contact.name);
			number.setText(contact.number);

			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.d(TAG,
							String.format("click %d : %s (%s)", contact.id, contact.name,contact.number));
					// Log.d(TAG,String.format("click %d : %.02f",mood.id,mood.value/10.0f));
					// Intent intent = new
					// Intent(HistoryDayActivity.this,MoodDisplayActivity.class);
					// intent.putExtra(Constants.INTENT_EXTRA_KEY_MOOD_LOGICAL_DAY,
					// mood.logicalDay);
					// intent.putExtra(Constants.INTENT_EXTRA_KEY_MOOD_VALUE,
					// mood.value);
					// intent.putExtra(Constants.INTENT_EXTRA_KEY_NOTE,
					// mood.note);
					// intent.putExtra(Constants.INTENT_EXTRA_KEY_PHOTO_FILE,
					// mood.photoFile);
					// startActivity(intent);
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

		Log.d(TAG, "contextMenu");

		switch (v.getId()) {
		case R.id.activity_listitems_list_items:
			try {
				AdapterView.AdapterContextMenuInfo info;
				info = (AdapterView.AdapterContextMenuInfo) menuInfo;
				Log.d(TAG, String.format("item id %d: position: %d", info.id,
						info.position));

				if (cursor != null) {
					cursor.moveToPosition(info.position);
					if (!cursor.isAfterLast() && !cursor.isBeforeFirst()) {
						// Contact contact = new Contact(cursor);

						MenuInflater inflater = getMenuInflater();
						inflater.inflate(R.menu.menu_listitem_lists, menu);

					}
				}

			} catch (ClassCastException e) {
				Log.d(TAG, "bad menuInfo", e);
			}
		}
	}
	// --------------------------------------------
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = null;
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.d(TAG, "bad menuInfo", e);
		}
		/*
		switch (item.getItemId()) {
		
		case R.id.menuitem_listitem_lists_delete:
			if (info != null && cursor != null) {
				cursor.moveToPosition(info.position);
				if (!cursor.isAfterLast() && !cursor.isBeforeFirst()) {
					List list = new List(cursor);
					Log.d(TAG, String.format("delete : id - %d", list.id));
					deleteList(list);
					return true;

				}
			}

		case R.id.menuitem_listitem_lists_import:
			if (info != null && cursor != null) {
				cursor.moveToPosition(info.position);
				if (!cursor.isAfterLast() && !cursor.isBeforeFirst()) {
					List list = new List(cursor);
					Log.d(TAG, String.format("import : id - %d", list.id));

					importList(list);
					return true;

				}
			}

		}
		*/
		return super.onContextItemSelected(item);
	}
}
