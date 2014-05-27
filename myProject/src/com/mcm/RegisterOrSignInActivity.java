package com.mcm;

import java.util.ArrayList;

import com.mcm.database.AppConstant;
import com.mcm.registration.RegisterActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RegisterOrSignInActivity extends Activity {
	
	String url = "http://mcmwebapi.victoriatechnologies.com/api/Client?EmailId=";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_or_sign_in_activity_layout);
	}

	public void registerBtnClick(View v) {
		Intent in = new Intent(RegisterOrSignInActivity.this,
				RegisterActivity.class);
		startActivity(in);
	}

	public void signInBtnClick(View v) {
		
	}

	public ArrayList<ArrayList<String>> getAllRowsAsArrays() {
		ArrayList<ArrayList<String>> dataArrays = new ArrayList<ArrayList<String>>();
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getReadableDatabase();
		Cursor cursor;
		try {
			cursor = database.query(AppConstant.MEMBER_TABLE_NAME, new String[] {
					AppConstant.MCM_MEMBER_MEMEBER_ID,
					AppConstant.MCM_MEMBER_CLIENT_ID,
					AppConstant.MCM_MEMBER_FIRST_NAME,
					AppConstant.MCM_MEMBER_LAST_NAME,
					AppConstant.MCM_MEMBER_EMAIL_ID,
					AppConstant.MCM_MEMBER_PASSWORD }, null, null, null, null,
					null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				do {
					ArrayList<String> dataList = new ArrayList<String>();
					dataList.add("" + cursor.getInt(0));
					dataList.add("" + cursor.getInt(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataList.add(cursor.getString(5));
					dataArrays.add(dataList);

				} while (cursor.moveToNext());
			}
		} catch (SQLException e) {
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
		Log.w("--Result-----", "" + dataArrays);
		return dataArrays;
	}

	private String getEmailField() {
		String email = "";
		ArrayList<ArrayList<String>> emailList = getAllRowsAsArrays();
		for (int i = 0; i < emailList.size(); i++) {
			email = emailList.get(i).get(4).toString();
			break;
		}
		return email;
	}

}
