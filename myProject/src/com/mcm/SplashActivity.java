package com.mcm;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mcm.asynclass.ConnectionDetector;
import com.mcm.asynclass.SplashAsynTask;
import com.mcm.database.AppConstant;
import com.mcm.database.MCMDatabase;
import com.mcm.database.PzDatabaseHelper;

public class SplashActivity extends Activity {
	
	public static PzDatabaseHelper databaseHelper;
	ProgressDialog mpProgressDialog;
	String url;
	Boolean isInternetPresent = false;
	public static ArrayList<ArrayList<String>>churchMemeberList;
	public static ArrayList<ArrayList<String>>approvedMemeberList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.splash_activity_layout);
			init();
			createTable();
			startAsynTask();
			
		}
	
	private void init() 
	{
		approvedMemeberList = new ArrayList<ArrayList<String>>();
		churchMemeberList = new ArrayList<ArrayList<String>>();
		databaseHelper = new PzDatabaseHelper(getApplicationContext(), com.mcm.database.AppConstant.DB_NAME, null, 1);
		url = "http://mcmwebapi.victoriatechnologies.com/api/MembershipTypes";
		mpProgressDialog = new ProgressDialog(SplashActivity.this);
	}

	private void startAsynTask()
	{
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		 
		Boolean isInternetPresent = cd.isConnectingToInternet();
		 isInternetPresent = cd.isConnectingToInternet();
         if (isInternetPresent) {
        	 Log.e("Internet ", "IS present");
        	 new SplashAsynTask(SplashActivity.this, url, mpProgressDialog).execute("");
         } else {
             Toast.makeText(SplashActivity.this, "You don't have internet connection", Toast.LENGTH_LONG).show();
         }
	}
	
	private void createTable()
	{
		MCMDatabase databaseTable = new MCMDatabase(SplashActivity.this);
		databaseTable.createdatabase();
	}
	

}