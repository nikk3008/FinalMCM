package com.mcm.login;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.SplashActivity;
import com.mcm.database.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.forgetpassword.ForgetPassword;
import com.mcm.listener.LoginClickListner;
import com.mcm.menuandnotification.Menu;
import com.mcm.registration.InterfaceSPinnerId;

public class LoginActivity extends Activity implements InterfaceSPinnerId,
		PopulateChurchListOnValidating {

	EditText login, passWord;
	Spinner spinnerChurchMember;
	Button logiButton;
	TextView tv_message, tv_forgotPassword;
	TextView tv_header;
	private ProgressDialog mProgressDialog;
	String clientid;
	ArrayList<ArrayList<String>> clientChurchList;
	GetDataFromDatabase getDataFromDatabase;
	boolean is_Table_Empty = false;
	boolean is_Find = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		header();
		addItemIfDatabaseIsNotEmpty();
		addListenerOnSpinnerItemSelection();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	private void init() {
		is_Table_Empty = getIntent().getBooleanExtra(AppConstant.CHECK_TABLE,
				false);
		getDataFromDatabase = new GetDataFromDatabase();
		mProgressDialog = new ProgressDialog(LoginActivity.this);
		clientChurchList = new ArrayList<ArrayList<String>>();
		tv_message = (TextView) findViewById(R.id.la_tvErrorMsg);
		tv_forgotPassword = (TextView) findViewById(R.id.la_forfotpaswword);
		login = (EditText) findViewById(R.id.la_editEmail);
		passWord = (EditText) findViewById(R.id.la_editPass);
		logiButton = (Button) findViewById(R.id.la_btnLogin);
		spinnerChurchMember = (Spinner) findViewById(R.id.la_spChurchCentre);
		logiButton.setOnClickListener(loginClkListener);
		tv_forgotPassword.setOnClickListener(loginClkListener);
		spinnerChurchMember.setOnTouchListener(Spinner_OnTouch);
		tv_message.setVisibility(View.INVISIBLE);
	}

	private void header() {
		tv_header = (TextView) findViewById(R.id.headerTextView);
		tv_header.setText("Login");
	}

	private void addItemIfDatabaseIsNotEmpty() {
		boolean isClientTableEmpty = getDataFromDatabase.checkForClientTables();
		if (!isClientTableEmpty) {

		} else {
			clientChurchList = getDataFromDatabase.getClientChurch();
			addItemsOnChurchMemberShipTypeSpinner(clientChurchList);
		}
	}

	public void addItemsOnChurchMemberShipTypeSpinner(
			ArrayList<ArrayList<String>> churchList) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < churchList.size(); i++) {
			list.add(churchList.get(i).get(4));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				LoginActivity.this, android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChurchMember.setAdapter(dataAdapter);
		spinnerChurchMember.setPrompt("Church Center");
		spinnerChurchMember.setEnabled(true);
	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerChurchMember
				.setOnItemSelectedListener(new ActiveMemeberShipSpinner(
						LoginActivity.this, LoginActivity.this,
						spinnerChurchMember));
	}

	@Override
	public void getSpinnerId(int pos) {
		clientid = clientChurchList.get(pos).get(0).toString();
	}

	LoginClickListner loginClkListener = new LoginClickListner() {

		@Override
		public void onLogInBtnClk(View view) {
			// TODO Auto-generated method stub
			validationForLoginButton();
		}

		@Override
		public void onforgotPasswordClk(View view) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
		}
	};

	private void validationForLoginButton() {
		if (!checkEmail())
			return;
		if (!checkEmailPattern())
			return;
		if (!checkpassWord())
			return;
		else {
			authorizedUser();
		}
	}

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

	private boolean checkpassWord() {
		if (passWord.length() == 0) {
			setErrMsg("Please fill password field.");
			return false;
		}
		return true;
	}

	View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				showToast();
			}
			return false;
		}

		private void showToast() {
			GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
			if (!getDataFromDatabase.checkForTables()) {
				spinnerChurchMember.setEnabled(false);
				setErrMsg("Enter Your Email and Password");
			} else {
				if (spinnerChurchMember.getAdapter().getCount() == 1) {
					Log.e("Spinner GEtCount", ""
							+ spinnerChurchMember.getAdapter().getCount());
					spinnerChurchMember.setEnabled(false);
				} else {
					spinnerChurchMember.setEnabled(true);
				}
			}
		}
	};

	public void setErrMsg(String msg) {
		tv_message.setVisibility(View.VISIBLE);
		tv_message.setText(msg);
		tv_message.setTextColor(Color.RED);
	}

	private void authorizedUser() {
		if (!checkForClientTables()) {
			Log.e("Calling False one", "" + checkForClientTables());
			validateFromApi(checkForClientTables());
		} else {
			Log.e("Calling True one", "" + checkForMemberTables());
			validatefromDatabase();
		}

	}

	public boolean checkForMemberTables() {
		return getDataFromDatabase.checkForTables();
	}
	
	public boolean checkForClientTables() {
		return getDataFromDatabase.checkForTables();
	}


	private void validateFromApi(boolean checkMemberTable) {
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getWritableDatabase();
		String url = "http://mcmwebapi.victoriatechnologies.com/api/Member/GetRegisteredMemberByEmailPwd?EmailId="
				+ login.getText().toString().trim()
				+ "&"
				+ "Password="
				+ passWord.getText().toString().trim();
		new LogInAsync(LoginActivity.this, mProgressDialog, url, login
				.getText().toString().trim(), passWord.getText().toString()
				.trim(), LoginActivity.this, database, spinnerChurchMember,
				checkMemberTable).execute("");
	}

	private void validatefromDatabase() {
		if (clientid == null) {
			showOKAleart("Error", "Invalid Credential");
			return;
		}
		if (!checkForClientID()) {
			showOKAleart("Error", "Invalid Credential");
			return;
		}
		if (!checkForEmail()) {
			showOKAleart("Error", "Invalid Credential");
			return;
		}
		if (!checkForPassword()) {
			showOKAleart("Error", "Invalid Credential");
			return;
		} else {
			startActivity(new Intent(LoginActivity.this, Menu.class));
			finish();
		}
	}

	private boolean checkForClientID() {
		for (int i = 0; i < clientChurchList.size(); i++) {
			if (clientChurchList.get(i).get(0).equals(clientid)) {
				is_Find = true;
				break;
			}
		}
		return is_Find;
	}

	private boolean checkForEmail() {
		for (int i = 0; i < clientChurchList.size(); i++) {
			if (clientChurchList.get(i).get(5).toString().trim()
					.equals(login.getText().toString().trim())) {
				is_Find = true;
				break;
			}
		}
		return is_Find;
	}

	private boolean checkForPassword() {
		for (int i = 0; i < clientChurchList.size(); i++) {
			if (clientChurchList.get(i).get(6).toString().trim()
					.equals(passWord.getText().toString().trim())) {
				is_Find = true;
				break;
			}
		}
		return is_Find;
	}

	public void showOKAleart(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginActivity.this);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", null).show();
	}

	@Override
	public void populateChurchList(ArrayList<ArrayList<String>> myChurchList) {
		// TODO Auto-generated method stub
		clientChurchList = myChurchList;
		is_Table_Empty = true;
	}
}
