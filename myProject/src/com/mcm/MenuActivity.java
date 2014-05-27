package com.mcm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity_layout);
		Toast.makeText(MenuActivity.this, "You are loggedin successfully", Toast.LENGTH_LONG).show();
	}

}
