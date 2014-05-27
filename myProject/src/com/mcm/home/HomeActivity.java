package com.mcm.home;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.countrypicker.CountryPicker;
import com.mcm.R;
import com.mcm.listener.HomeClickListner;
import com.mcm.login.SignInAsync;
import com.mcm.registration.RegisterActivity;

public class HomeActivity extends FragmentActivity {
	Button register, signIn;
	ProgressDialog mProgressDialog;
	String url = "http://mcmwebapi.victoriatechnologies.com/api/Client?EmailId=";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_or_sign_in_activity_layout);
		init();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	private void init() {
		mProgressDialog = new ProgressDialog(HomeActivity.this);
		register = (Button)findViewById(R.id.reg_btn);
		signIn = (Button)findViewById(R.id.signin_btn);
		register.setOnClickListener(homeClkListener);
		signIn.setOnClickListener(homeClkListener);
	}
	
	HomeClickListner homeClkListener = new HomeClickListner() {
		
		@Override
		public void onSignInBtnClk(View view) {
			new SignInAsync(HomeActivity.this, mProgressDialog,url,true).execute("");
		}
		
		@Override
		public void onRegisterBtnClk(View view) {
			Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
		}
	};
}
