package com.mcm.database;

import com.mcm.SplashActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MCMDatabase {

	Context context;

	public MCMDatabase(Context context) {
		this.context = context;
	}

	public void memberTable(SQLiteDatabase database) {
		SplashActivity.databaseHelper.createTable(database,
				" CREATE TABLE IF NOT EXISTS " + AppConstant.MEMBER_TABLE_NAME
						+ " (" + AppConstant.MCM_MEMBER_MEMEBER_ID
						+ " Integer, " + AppConstant.MCM_MEMBER_CLIENT_ID
						+ " Integer," + AppConstant.MCM_MEMBER_FIRST_NAME
						+ " Text," + AppConstant.MCM_MEMBER_LAST_NAME
						+ " Text," + AppConstant.MCM_MEMBER_EMAIL_ID + " Text,"
						+ AppConstant.MCM_MEMBER_PASSWORD + " Text" + " );");
	}

	public void clientTable(SQLiteDatabase database) {
		String ClientTableQuery = "CREATE TABLE IF NOT EXISTS "
				+ AppConstant.CLIENT_TABLE_NAME + "("
				+ AppConstant.MCM_CLIENT_CLIENT_ID + " Integer, "
				+ AppConstant.MCM_CLIENT_MEMBER_ID + " Integer,"
				+ AppConstant.MCM_CLIENT_FIRST_NAME + " Text,"
				+ AppConstant.MCM_CLIENT_LAST_NAME + " Text,"
				+ AppConstant.MCM_CLIENT_CLIENT_CHURCH + " Text, "
				+ AppConstant.MCM_CLIENT_CLIENT_EMAIL + " Text, "
				+ AppConstant.MCM_CLIENT_CLIENT_PASSWORD + " Text, "
				+ "FOREIGN KEY (" + AppConstant.MCM_CLIENT_CLIENT_ID
				+ ") REFERENCES " + AppConstant.MEMBER_TABLE_NAME + " ("
				+ AppConstant.MCM_MEMBER_CLIENT_ID + "), " + "FOREIGN KEY ("
				+ AppConstant.MCM_CLIENT_MEMBER_ID + ") REFERENCES "
				+ AppConstant.MEMBER_TABLE_NAME + "("
				+ AppConstant.MCM_MEMBER_CLIENT_ID + "))";
		SplashActivity.databaseHelper.createTable(database,
				ClientTableQuery);
	}

	public void createdatabase() {
		if (SplashActivity.databaseHelper.isDatabaseExists(context
				.getApplicationContext())) {
			Log.e("DATABASE ALREADY EXIST", "" + "IT IS EXIST");
		} else {
			memberTable(SplashActivity.databaseHelper.getWritableDatabase());
			clientTable(SplashActivity.databaseHelper.getWritableDatabase());
		}
	}

}
