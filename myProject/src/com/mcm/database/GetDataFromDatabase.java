package com.mcm.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mcm.SplashActivity;

public class GetDataFromDatabase {

	public ArrayList<ArrayList<String>> getAllRowsAsArrays() {
		ArrayList<ArrayList<String>> dataArrays = new ArrayList<ArrayList<String>>();
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getReadableDatabase();
		Cursor cursor;
		try {
			cursor = database.query(AppConstant.MEMBER_TABLE_NAME,
					new String[] { AppConstant.MCM_MEMBER_MEMEBER_ID,
							AppConstant.MCM_MEMBER_CLIENT_ID,
							AppConstant.MCM_MEMBER_FIRST_NAME,
							AppConstant.MCM_MEMBER_LAST_NAME,
							AppConstant.MCM_MEMBER_EMAIL_ID,
							AppConstant.MCM_MEMBER_PASSWORD }, null, null,
					null, null, null);
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
		// Log.w("--Result-----", "" + dataArrays);
		return dataArrays;
	}

	public ArrayList<ArrayList<String>> getClientChurch() {
		ArrayList<ArrayList<String>> dataArrays = new ArrayList<ArrayList<String>>();
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getReadableDatabase();
		Cursor cursor;
		try {
			cursor = database.query(AppConstant.CLIENT_TABLE_NAME,
					new String[] { AppConstant.MCM_CLIENT_CLIENT_ID,
							AppConstant.MCM_CLIENT_MEMBER_ID,
							AppConstant.MCM_CLIENT_FIRST_NAME,
							AppConstant.MCM_CLIENT_LAST_NAME,
							AppConstant.MCM_CLIENT_CLIENT_CHURCH,
							AppConstant.MCM_CLIENT_CLIENT_EMAIL,
							AppConstant.MCM_CLIENT_CLIENT_PASSWORD }, null,
					null, null, null, null);
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
					dataList.add(cursor.getString(6));
					dataArrays.add(dataList);

				} while (cursor.moveToNext());
			}
		} catch (SQLException e) {
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
		// Log.w("--Result-----", "" + dataArrays);
		return dataArrays;
	}

	public String getEmailField() {
		String email = "";
		ArrayList<ArrayList<String>> emailList = getAllRowsAsArrays();
		if (emailList.size() == 0) {
			email = "abc@gmail.com";
		} else {
			email = emailList.get(getAllRowsAsArrays().size() - 1).get(4)
					.toString();
		}
		return email.trim();
	}

	public String getPasswordField() {
		String password = "";
		ArrayList<ArrayList<String>> passwordList = getAllRowsAsArrays();
		if (passwordList.size() == 0) {
			password = "123";
		} else {
			password = passwordList.get(getAllRowsAsArrays().size() - 1).get(5)
					.toString();
		}
		return password.trim();
	}

	public int getClientIdField() {
		int clientID = 1;
		ArrayList<ArrayList<String>> clientIDList = getAllRowsAsArrays();
		if (clientIDList.size() == 0) {
			clientID = 1;
		} else {
			try {
				clientID = Integer
						.parseInt(clientIDList
								.get(getAllRowsAsArrays().size() - 1).get(1)
								.toString());
			} catch (NumberFormatException nfe) {
			}
		}
		return clientID;
	}

	public boolean checkForTables() {
		String whereClause = "Select * from " + AppConstant.MEMBER_TABLE_NAME;
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getReadableDatabase();
		Cursor cursor = database.rawQuery(whereClause, null);
		if (cursor.getCount() == 0)
			return false;

		if (cursor.getCount() > 0)
			return true;

		cursor.close();
		return false;
	}

	public boolean checkForClientTables() {
		String whereClause = "Select * from " + AppConstant.CLIENT_TABLE_NAME;
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getReadableDatabase();
		Cursor cursor = database.rawQuery(whereClause, null);
		if (cursor.getCount() == 0)
			return false;
		if (cursor.getCount() > 0)
			return true;

		cursor.close();
		return false;
	}

}
