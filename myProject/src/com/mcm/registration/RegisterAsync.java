package com.mcm.registration;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.mcm.GMailSender;
import com.mcm.appconstant.RegistrationAppConstant;
import com.mcm.database.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.library.PostJson;

public class RegisterAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	SQLiteDatabase database;
	ArrayList<String> myStrings;

	String appPin, fName, sName, eMail, passWord, address_One, address_Two,
			street, town, city, county, state, country, postalCode,
			mobileNumber, sex, homeTelephone;
	int churMemeberShip_ID;
	Button previous, submit;
	String url;
    AlertMessage alertMessage;
    String message;
	public RegisterAsync(Context context, ProgressDialog mProgressDialog,
			SQLiteDatabase database, String appPin, String fName, String sName,
			String eMail, String passWord, String address_One,
			String address_Two, String street, String town, String city,
			String county, String state, String country, String postalCode,
			String mobileNumber, String sex, int churMemeberShip_ID,
			String homeTelephone, String url, AlertMessage alertMessage,String message) {

		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.database = database;
		this.appPin = appPin;
		this.fName = fName;
		this.sName = sName;
		this.eMail = eMail;
		this.passWord = passWord;
		this.address_One = address_One;
		this.address_Two = address_Two;
		this.street = street;
		this.town = town;
		this.city = city;
		this.county = county;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.mobileNumber = mobileNumber;
		this.sex = sex;
		this.homeTelephone = homeTelephone;
		this.churMemeberShip_ID = churMemeberShip_ID;
		this.url = url;
		this.alertMessage = alertMessage;
		this.message = message;
		Log.e("MAY AYA", "" + "do in context");
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
		Log.e("MAY AYA", "" + "do in background");
		enterValueIndatabase();
		return null;
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		alertMessage.showMessage(message);
   }

	void showDialog() {
		mProgressDialog.setMessage("Please Wait For While We Are Registering You.");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	private String parseJSONString() {
		try {
			JSONObject jsonObject = new JSONObject(postRegistration());
			 message = jsonObject.getString(RegistrationAppConstant.APP_REGISTRATION_STATUS);
			Log.e("My Message", "" + message);
				InsertTable insertTable = new InsertTable(database);
				insertTable.addRowforMemberTable(
						jsonObject.getString(RegistrationAppConstant.MEMBER_ID),
						jsonObject.getString(RegistrationAppConstant.CLIENT_ID),
						jsonObject.getString(RegistrationAppConstant.FIRSTNAME),
						jsonObject.getString(RegistrationAppConstant.SURNAME),
						jsonObject.getString(RegistrationAppConstant.EMAIL_ID),
						jsonObject.getString(RegistrationAppConstant.PASSWORD));

			Log.e("MEMBER EMAIL MESSAGE", "" + message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	private String postRegistration() {
		PostJson postJson = new PostJson(url, appPin, fName, sName, eMail,
				passWord, address_One, address_Two, street, town, city, county,
				state, country, postalCode, mobileNumber, sex,
				churMemeberShip_ID, homeTelephone);
		return postJson.postDataToServer().toString().trim();
	}

	public void showOKAleart(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", null).show();
	}
	
	private void enterValueIndatabase()
	{
		GetDataFromDatabase getDataFromDatabase =  new GetDataFromDatabase();
		try {
			if (!getDataFromDatabase.checkForTables()) {
				parseJSONString();
			} else {
				database.delete(AppConstant.MEMBER_TABLE_NAME, null, null);
				parseJSONString();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
