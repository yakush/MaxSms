package com.yakstudio.android.maxSms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yakstudio.android.maxSms.Activity;
import com.yakstudio.android.maxSms.R;

public class MainActivity extends Activity implements OnClickListener{
	static final String TAG="MainActivity";
	
	private Button btn_lists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_lists= (Button)findViewById(R.id.activity_main_btn_lists);
        
        
        
        btn_lists.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_main_btn_lists:
			startActivity(new Intent(this,ListsActivity.class));
			break;
			
		}
		
	}
	
}
