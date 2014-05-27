package com.mcm.asynclass;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.GMailSender;

public class SendEmailAsyn extends AsyncTask<String, String, String> {

	private ProgressDialog mProgressDialog;
	private Context context;
	private SQLiteDatabase database;
	ArrayList<String> myStrings;

	String prepareSubject = "";
	String prepareMessage = "";
	String emailListForApproval = "";
	String senderMail = "apptest9887@gmail.com"; // must be any gmail id,
	String senderP = "AllIsWell";

	public SendEmailAsyn(Context context,String prepareSubject, String prepareMessage,
			String emailListForApproval) {
		this.context = context;
		this.prepareSubject = prepareSubject;
		this.prepareMessage = prepareMessage;
		this.emailListForApproval = emailListForApproval;
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
		sendEmail();
		return null;

	}

	@Override
	protected void onPostExecute(String unused) {
		// mProgressDialog.dismiss();
		closeDialog();
		// Intent i = new Intent(context, Re.class);
		// i.putStringArrayListExtra("KEY", myStrings);
		// context.startActivity(i);
	}

	private void sendEmail() {
		try {
			try {
				emailListForApproval = "nirmalgit@gmail.com";
				GMailSender sender = new GMailSender(senderMail, senderP);
				sender.sendMail(prepareSubject, prepareMessage, senderMail,
						emailListForApproval);
				Log.e("email", "is going..");
			} catch (Exception e) {
				Log.e("SendMail", e.getMessage(), e);
			}// end catch()...
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void showDialog() {
		mProgressDialog.setMessage("Please Wait...updating form.");
		mProgressDialog.setCancelable(false);
		// show dialog:
		mProgressDialog.show();
	}// end showDialog()

	void closeDialog() {
		// dismiss pDialog
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}// end if..
	}// end closeDialog()

}
