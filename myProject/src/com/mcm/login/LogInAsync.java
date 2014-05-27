package com.mcm.login;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mcm.SplashActivity;
import com.mcm.appconstant.RegistrationAppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.home.HomeActivity;
import com.mcm.library.GetDataFromApi;

public class LogInAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String url;
	PopulateChurchListOnValidating populateChurchListOnValidating;
	int loginValue = 5;
	boolean isTableFalse;
	SQLiteDatabase database;
	String email;
	String password;
	Spinner spinnerChurchMember;
	String emailFiled, passwordField, firstname, lastname, message;
	int status = 0;

	public LogInAsync(Context context, ProgressDialog mProgressDialog,
			String url, String email, String password,
			PopulateChurchListOnValidating populateChurchListOnValidating,
			SQLiteDatabase database, Spinner spinnerChurchMember,
			boolean isTableFalse) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.email = email;
		this.password = password;
		this.database = database;
		this.spinnerChurchMember = spinnerChurchMember;
		this.isTableFalse = isTableFalse;
		this.populateChurchListOnValidating = populateChurchListOnValidating;
		Log.e("MERA URL DEKH LO", "" + url);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
		Log.e("MAY AYA", "" + "do in background in sigin");
		callLogin();
		return null;
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		onSuccesFull();
	}

	void showDialog() {
		mProgressDialog.setMessage("Please Wait...For Some Time.");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	private void callLogin() {
		GetDataFromApi getDataFromApi = new GetDataFromApi(url);
		message = getDataFromApi.postSignIn().toString().trim();
		syncroniseDatabase(message);
		if (status == 1) {
		} else {
			saveVlaueInInserTable();
		}

	}

	public void saveVlaueInInserTable() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		ArrayList<ArrayList<String>> memberList = getDataFromDatabase
				.getAllRowsAsArrays();
		int member_id = Integer.parseInt(memberList.get(memberList.size() - 1)
				.get(0).toString());
		firstname = memberList.get(memberList.size() - 1).get(2).toString()
				.trim();
		lastname = memberList.get(memberList.size() - 1).get(3).toString()
				.trim();
		emailFiled = memberList.get(memberList.size() - 1).get(4).toString()
				.trim();
		passwordField = memberList.get(memberList.size() - 1).get(5).toString()
				.trim();
		String churchListUrl = "http://mcmwebapi.victoriatechnologies.com/api/Client?EmailId=" + emailFiled;
		message = new GetDataFromApi(churchListUrl).postSignIn();
		try {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getReadableDatabase();
			InsertTable insertTable = new InsertTable(database);
			JSONArray jArray = new JSONArray(message.toString().trim());
			Log.e("JSON LENGHT ", "" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				int clien_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_ID);
				String church = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_NAME);
				insertTable.addRowforClientTable(clien_id, member_id,
						firstname, lastname, church, emailFiled, passwordField);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void syncroniseDatabase(String mesString) {
		InsertTable insertTable = new InsertTable(database);
		try {
			JSONArray jsonArray = new JSONArray(mesString);
			if (jsonArray.length() == 0) {
				Log.e("LEngh is zero ", "" + jsonArray.length());
				status = 1;
			} else {
				status = 2;
				Log.e("LEngh is Not zero ", "" + jsonArray.length());
				JSONObject jsonObject = null;
				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					insertTable
							.addRowforMemberTable(
									jsonObject
											.getString(RegistrationAppConstant.MEMBER_ID),
									jsonObject
											.getString(RegistrationAppConstant.CLIENT_ID),
									jsonObject
											.getString(RegistrationAppConstant.FIRSTNAME),
									jsonObject
											.getString(RegistrationAppConstant.SURNAME),
									jsonObject
											.getString(RegistrationAppConstant.EMAIL_ID),
									jsonObject
											.getString(RegistrationAppConstant.PASSWORD));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void addItemsOnChurchMemberShipTypeSpinner(
			ArrayList<ArrayList<String>> churchList) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < churchList.size(); i++) {
			list.add(churchList.get(i).get(4));
		}
		Log.e("MY CHURCH LIST ", "" + list);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChurchMember.setAdapter(dataAdapter);
		spinnerChurchMember.setPrompt("Church Center");
		spinnerChurchMember.setEnabled(true);
	}

	private void onSuccesFull() {
		if (status == 1) {
			showOKAleart(
					"Message",
					"You are not registered ,first registered and get approved by MyChurchMate Admin.");

		} else {
			ArrayList<ArrayList<String>> clientChurchList = new GetDataFromDatabase()
					.getClientChurch();
			populateChurchListOnValidating.populateChurchList(clientChurchList);
			addItemsOnChurchMemberShipTypeSpinner(clientChurchList);
		}
	}

	public void showOKAleart(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", null).show();
	}
}
