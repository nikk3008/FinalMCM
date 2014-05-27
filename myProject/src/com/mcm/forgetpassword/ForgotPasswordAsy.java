package com.mcm.forgetpassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.library.GetDataFromApi;
import com.mcm.registration.AlertMessage;

public class ForgotPasswordAsy extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String url;
	AlertMessage alertMessage;
	public ForgotPasswordAsy(Context context, ProgressDialog mProgressDialog,
			String url,AlertMessage alertMessage) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.alertMessage = alertMessage;
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

	private void callLogin() {
		// TODO Auto-generated method stub
		GetDataFromApi getDataFromApi = new GetDataFromApi(url);
		getDataFromApi.postSignIn();
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		alertMessage.showMessage("Check your email to get temporary password");
	}

	private void showDialog() {
		mProgressDialog.setMessage("Please Wait...For Some Time.");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
}
