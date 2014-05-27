package com.mcm.forgetpassword;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.mcm.R;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.listener.ForgetListener;
import com.mcm.login.ActiveMemeberShipSpinner;
import com.mcm.login.LoginActivity;
import com.mcm.registration.AlertMessage;
import com.mcm.registration.InterfaceSPinnerId;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ForgetPassword extends Activity implements InterfaceSPinnerId,AlertMessage {

	TextView header;
	EditText login;
	Spinner spinnerChurchMember;
	Button submitButton;
	TextView tv_message;
	ProgressDialog mProgressDialog;
	String clientid;
	boolean is_Table_Empty = false;
	GetDataFromDatabase getDataFromDatabase;
	ArrayList<ArrayList<String>> churchList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password_activity_layout);
		header();
		init();
		addItemIfDatabaseIsNotEmpty();
		addListenerOnSpinnerItemSelection();
	}

	private void init() {
		getDataFromDatabase = new GetDataFromDatabase();
		churchList = getDataFromDatabase.getClientChurch();
		mProgressDialog = new ProgressDialog(ForgetPassword.this);
		tv_message = (TextView) findViewById(R.id.la_tvErrorMsg);
		login = (EditText) findViewById(R.id.la_editEmail);
		submitButton = (Button) findViewById(R.id.la_btnLogin);
		spinnerChurchMember = (Spinner) findViewById(R.id.la_spChurchCentre);
		submitButton.setOnClickListener(forgotListener);

	}

	private void header() {
		// TODO Auto-generated method stub
		header = (TextView) findViewById(R.id.headerTextView);
		header.setText("Forgot Password");
	}

	private void addItemIfDatabaseIsNotEmpty() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		boolean isClientTableEmpty = getDataFromDatabase.checkForClientTables();
		if (!isClientTableEmpty) {

		} else {
			addItemsOnChurchMemberShipTypeSpinner();
		}
	}

	public void addItemsOnChurchMemberShipTypeSpinner() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < churchList.size(); i++) {
			list.add(churchList.get(i).get(1));
		}
		Log.e("MY CHURCH LIST ", "" + list);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				ForgetPassword.this, android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChurchMember.setAdapter(dataAdapter);
		spinnerChurchMember.setPrompt("Church Center");
		spinnerChurchMember.setEnabled(true);
	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerChurchMember
				.setOnItemSelectedListener(new ActiveMemeberShipSpinner(
						ForgetPassword.this, ForgetPassword.this,
						spinnerChurchMember));
	}

	@Override
	public void getSpinnerId(int pos) {
		// TODO Auto-generated method stub
		clientid = churchList.get(pos).get(0).toString();
		Log.e("I AM IN ID VALUE", "" + clientid);
	}

	ForgetListener forgotListener = new ForgetListener() {

		@Override
		public void onSubmitBtnClk(View view) {
			// TODO Auto-generated method stub
			validateSubmitButton();
		}

		private void validateSubmitButton() {
			// TODO Auto-generated method stub
			if (!checkEmail())
				return;
			if (!checkEmailPattern())
				return;
			else {
				authorizedUser();
			}
		}
	};

	private boolean checkEmailPattern() {
		Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}"
				+ "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
				+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}" + ")+");
		if (!EMAIL_PATTERN.matcher(login.getText().toString()).matches()) {
			setErrMsg("Please fill valid Email Address.");
			return false;
		}
		return true;
	}

	private boolean checkEmail() {
		if (login.length() == 0) {
			setErrMsg("Please fill Email field.");
			return false;
		}
		return true;
	}

	public void setErrMsg(String msg) {
		tv_message.setVisibility(View.VISIBLE);
		tv_message.setText(msg);
		tv_message.setTextColor(Color.RED);
	}

	private void authorizedUser() {
		String url = "http://mcmwebapi.victoriatechnologies.com/api/Member/ForgotPassword?ClientId="
				+ clientid + "&EmailId=" + login.getText().toString().trim();
		new ForgotPasswordAsy(ForgetPassword.this, mProgressDialog, url,ForgetPassword.this).execute("");
	}
	
	public void showOKAleartForAlreadyRegistered(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ForgetPassword.this);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).show();
	}

	@Override
	public void showMessage(String message) {
		showOKAleartForAlreadyRegistered("Message", message);
	}
}
