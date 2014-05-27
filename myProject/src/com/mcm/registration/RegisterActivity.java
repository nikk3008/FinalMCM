package com.mcm.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.mcm.R;
import com.mcm.R.color;
import com.mcm.SplashActivity;
import com.mcm.forgetpassword.ForgetPassword;
import com.mcm.listener.RegisterClickListner;

public class RegisterActivity extends FragmentActivity implements InterfaceSPinnerId,
		InterfaceMaleFemaleSPinnerId ,AlertMessage {

	EditText editAppPin, editFName, editSName, editEmail, editEmailConfirm,
			editPass, editConfirmPass, editMobile, editHomeTelephone,
			editAddress1, editAddress2, editStreet, editTown, editCity,
			editDistrictCounty, editState, editPostcode, editCountry;
	Spinner spSelectChurchMembership, spMaleFemale;
	ScrollView scrollView1, scrollView2 ,scrollView3;
	Button btnNext, btnPrevious, btnSubmit, btnPrevious_StepThree, btnSubmit_StepThree;
	TextView errorText;
	int churchMemeberPos;
	String maleFemalePos = "maleFemale";
	ProgressDialog mProgressDialog;
	SQLiteDatabase database;
	String url, message = "";
	RelativeLayout nextRelative, bottomRelative,bottomRelative_Step_Three;
	TextView step_One, step_Two, step_Three;
	CountryPicker picker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity_layout);
		Log.e("I AM IN NEW REGISTER", "YES I AM");
		init();
		automaticscroll();
		addItemsOnChurchMemberShipTypeSpinner();
		addListenerOnSpinnerItemSelection();
		addListenerOnMaleFeamleSpinnerItemSelection();
		PreviousHide();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	private void init() {
		url = "http://mcmwebapi.victoriatechnologies.com/api/Member";
		picker = CountryPicker.newInstance("Select Country");
		database = SplashActivity.databaseHelper.getWritableDatabase();
		nextRelative = (RelativeLayout) findViewById(R.id.next_ID);
		bottomRelative = (RelativeLayout) findViewById(R.id.bootom_ID);
		bottomRelative_Step_Three = (RelativeLayout)findViewById(R.id.bootom_submit_id);
		mProgressDialog = new ProgressDialog(RegisterActivity.this);
		editAppPin = (EditText) findViewById(R.id.ra_editAppPin);
		editFName = (EditText) findViewById(R.id.ra_editFName);
		editSName = (EditText) findViewById(R.id.ra_editSName);
		editEmail = (EditText) findViewById(R.id.ra_editEmail);
		editEmailConfirm = (EditText) findViewById(R.id.ra_editEmailConfirm);
		editPass = (EditText) findViewById(R.id.ra_editPass);
		editConfirmPass = (EditText) findViewById(R.id.ra_editConfirmPass);
		editMobile = (EditText) findViewById(R.id.ra_editMobile);
		editHomeTelephone = (EditText) findViewById(R.id.ra_editHomeTelephone);
		editAddress1 = (EditText) findViewById(R.id.ra_editAddress1);
		editAddress2 = (EditText) findViewById(R.id.ra_editAddress2);
		editStreet = (EditText) findViewById(R.id.ra_editStreet);
		editTown = (EditText) findViewById(R.id.ra_editTown);
		editCity = (EditText) findViewById(R.id.ra_editCity);
		editDistrictCounty = (EditText) findViewById(R.id.ra_editDistrictCounty);
		editState = (EditText) findViewById(R.id.ra_editState);
		editPostcode = (EditText) findViewById(R.id.ra_editpostCode);
		editCountry = (EditText) findViewById(R.id.ra_editCountry);
		editCountry.setKeyListener(null);
		spSelectChurchMembership = (Spinner) findViewById(R.id.ra_spSelectChurchMembership);
		spMaleFemale = (Spinner) findViewById(R.id.ra_spMaleFemale);
		scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		scrollView2 = (ScrollView) findViewById(R.id.scrollView2);
		scrollView3 = (ScrollView) findViewById(R.id.scrollView3);
		btnNext = (Button) findViewById(R.id.ra_btnNextStep2);
		btnSubmit = (Button) findViewById(R.id.ra_btnSubmit);
		btnPrevious = (Button)findViewById(R.id.ra_btnprevious);
		btnPrevious_StepThree = (Button)findViewById(R.id.ra_btnprevious_stepthree);
		btnSubmit_StepThree = (Button)findViewById(R.id.ra_btnSubmit_stepthree);
		errorText = (TextView) findViewById(R.id.ra_tvFormMsg);
		step_One = (TextView)findViewById(R.id.ra_Step1);
		step_Two = (TextView)findViewById(R.id.ra_Step2);
		step_Three = (TextView)findViewById(R.id.ra_Step3);
		btnNext.setOnClickListener(registerClkListener);
		btnSubmit.setOnClickListener(registerClkListener);
		btnPrevious.setOnClickListener(registerClkListener);
		btnPrevious_StepThree.setOnClickListener(registerClkListener);
		btnSubmit_StepThree.setOnClickListener(registerClkListener);
		editCountry.setOnClickListener(registerClkListener);
	}
	
	private void automaticscroll()
	{
		scrollView1.pageScroll(ScrollView.FOCUS_DOWN);
	}

	private void PreviousHide() {
		nextRelative.setVisibility(View.VISIBLE);
		bottomRelative.setVisibility(View.INVISIBLE);
		bottomRelative_Step_Three.setVisibility(View.INVISIBLE);
		scrollView1.setVisibility(View.VISIBLE);
		scrollView2.setVisibility(View.INVISIBLE);
		scrollView3.setVisibility(View.INVISIBLE);
		errorText.setVisibility(View.INVISIBLE);
		step_One.setBackgroundColor(getResources().getColor(color.bluedark));
		step_Two.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Three.setBackgroundColor(getResources().getColor(color.bluelight));
	}
	
	private void nextHide() {
		nextRelative.setVisibility(View.INVISIBLE);
		bottomRelative_Step_Three.setVisibility(View.INVISIBLE);
		bottomRelative.setVisibility(View.VISIBLE);
		scrollView1.setVisibility(View.INVISIBLE);
		scrollView3.setVisibility(View.INVISIBLE);
		scrollView2.setVisibility(View.VISIBLE);
		errorText.setVisibility(View.INVISIBLE);
		step_Two.setBackgroundColor(getResources().getColor(color.bluedark));
		step_One.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Three.setBackgroundColor(getResources().getColor(color.bluelight));
	}
	
	private void nextHideStepThree() {
		nextRelative.setVisibility(View.INVISIBLE);
		bottomRelative.setVisibility(View.VISIBLE);
		bottomRelative_Step_Three.setVisibility(View.VISIBLE);
		scrollView1.setVisibility(View.INVISIBLE);
		scrollView2.setVisibility(View.INVISIBLE);
		scrollView3.setVisibility(View.VISIBLE);
		errorText.setVisibility(View.INVISIBLE);
		step_One.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Two.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Three.setBackgroundColor(getResources().getColor(color.bluedark));
	}

	RegisterClickListner registerClkListener = new RegisterClickListner() {

		@Override
		public void onstepTwoNextBtnClk(View view) {
			onStepTwoNextValidation();
		}

		@Override
		public void onNextBtnClk(View view) {
			ValidationForNextButton();
		}

		@Override
		public void onPreviousBtnClk(View view) {
			PreviousHide();
		}

		@Override
		public void onStepThreePreviousBtnClk(View view) {
			// TODO Auto-generated method stub
			nextHide();
		}

		@Override
		public void onStepThreeSubmitBtnClk(View view) {
			// TODO Auto-generated method stub
			validationForSubmitButton();
		}

		@Override
		public void onCountryBtnClk(View view) {
			Log.e("CLICH HAPEEN", "IN COUNTRY");
			showCountryListAsDialog();
		}
	};
	
	private void onStepTwoNextValidation()
	{
		if (!checkMobileNumber())
			return;
		else{
			nextHideStepThree();
		}
	}

	private void ValidationForNextButton() {
		if (!checkAppPin())
			return;
		if (!checkFirstName())
			return;
		if (!checkSurName())
			return;
		if (!checkEmail())
			return;
		if (!checkEmailPattern())
			return;
		if (!checkConfirmEmail())
			return;
		if (!checkPassword())
			return;
		if (!checkConfirmPassword())
			return;
		else {
			nextHide();
		}
	}

	private void validationForSubmitButton() {
		
		if (!checkAddressOne())
			return;
		if (!checkStreet())
			return;
		if (!checkTown())
			return;
		if (!checkCounty())
			return;
		if (!checkPostal())
			return;
		if (!checkCountry())
			return;
		else {
			startRegistering();
		}

	}

	private boolean checkAppPin() {

		if (editAppPin.getText().toString().equals("")) {
			setErrMsg("App Pin field is Blank");
			return false;
		}
		return true;
	}

	private boolean checkFirstName() {

		if (editFName.getText().toString().equals("")) {
			setErrMsg("First Name field is Blank");
			return false;
		}
		return true;
	}

	private boolean checkSurName() {

		if (editSName.getText().toString().equals("")) {
			setErrMsg("Surname Name field is Blank");
			return false;
		}
		return true;
	}

	private boolean checkEmail() {

		if (editEmail.getText().toString().equals("")) {
			setErrMsg("Email field is Blank");
			return false;
		}
		return true;
	}

	public boolean checkEmailPattern() {
		Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}"
				+ "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
				+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}" + ")+");
		if (!EMAIL_PATTERN.matcher(editEmail.getText().toString()).matches()) {
			setErrMsg("Please enter the correct email");
			return false;
		}
		return true;
	}

	private boolean checkConfirmEmail() {

		if (editEmailConfirm.getText().toString().equals("")) {
			setErrMsg("Confirm Email field is Blank");
			return false;
		}
		return true;
	}

	public boolean checkConfirmEmailPattern() {
		if (!editEmail.getText().toString()
				.equals(editEmailConfirm.getText().toString())) {
			setErrMsg("Email And Confirm Email Field Are Not Same");
			return false;

		}
		return true;
	}

	private boolean checkPassword() {

		if (editPass.getText().toString().equals("")) {
			setErrMsg("Password field is Blank");
			return false;
		}
		return true;
	}

	private boolean checkConfirmPassword() {

		if (!editPass.getText().toString()
				.equals(editConfirmPass.getText().toString())) {
			setErrMsg("Password and Confirm Password field is Not Same");
			return false;
		}
		return true;
	}

	private boolean checkMobileNumber() {
		if (editMobile.getText().toString().trim().equals("")) {
			setErrMsg("Please fill mobile number");
			return false;
		}
		return true;
	}

	private boolean checkAddressOne() {
		if (editAddress1.getText().toString().trim().equals("")) {
			Log.e("WHY ITS NOT PRINTING ADDRESS", "ITS PRINTING NOT REASON");
			setErrMsg("Please fill Address");
			return false;
		}
		return true;
	}

	private boolean checkStreet() {
		if (editStreet.getText().toString().trim().equals("")) {
			setErrMsg("Please fill Street");
			return false;
		}
		return true;
	}

	private boolean checkTown() {
		if (editTown.getText().toString().trim().equals("")) {
			setErrMsg("Please fill Town");
			return false;
		}
		return true;
	}

	private boolean checkCounty() {
		if (editDistrictCounty.getText().toString().trim().equals("")) {
			setErrMsg("Please fill County");
			return false;
		}
		return true;
	}

	private boolean checkPostal() {
		if (editPostcode.getText().toString().trim().equals("")) {
			Log.e("WHY ITS NOT PRINTING POSTAL", "ITS PRINTING NOT REASON");
			setErrMsg("Please fill Postal code");
			return false;
		}
		return true;
	}

	private boolean checkCountry() {
		if (editCountry.getText().toString().trim().equals("")) {
			setErrMsg("Please fill Country");
			return false;
		}
		return true;
	}

	public void showOKAleartNewlyRegisterdMember(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				RegisterActivity.this);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).show();
	}
	
	public void showOKAleartForAlreadyRegistered(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				RegisterActivity.this);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(RegisterActivity.this,ForgetPassword.class));
						finish();
					}
				}).show();
	}

	public void setErrMsg(String msg) {
		errorText.setVisibility(View.VISIBLE);
		errorText.setText(msg);
		errorText.setTextColor(Color.RED);
	}
	
	private void showCountryListAsDialog()
	{
		picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
		picker.setListener(countryListener);
	}
	
	CountryPickerListener countryListener = new CountryPickerListener() {
		
		@Override
		public void onSelectCountry(String name, String code) {
			// TODO Auto-generated method stub
			Log.e("COUNTRY NAME", "" + name + "  COUNTRY CODE " + "  " + code);
			editCountry.setText(name);
			picker.dismiss();
		}
	};

	public void addItemsOnChurchMemberShipTypeSpinner() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < SplashActivity.churchMemeberList.size(); i++) {
			list.add(SplashActivity.churchMemeberList.get(i).get(1));
		}

		Log.e("MY CHURCH LIST ", "" + list);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSelectChurchMembership.setAdapter(dataAdapter);
		spSelectChurchMembership.setSelection(1);
	}

	public void addListenerOnSpinnerItemSelection() {
		spSelectChurchMembership
				.setOnItemSelectedListener(new ChurchMemeberShipSpinner(
						RegisterActivity.this));
	}

	public void addListenerOnMaleFeamleSpinnerItemSelection() {
		spMaleFemale.setOnItemSelectedListener(new MaleFemaleSpinner(
				RegisterActivity.this));
	}

	@Override
	public void getSpinnerId(int pos) {
		Log.e("Spinner selected position", "" + pos);
		churchMemeberPos = pos;
	}

	@Override
	public void getMaleFemaleSpinnerId(int pos) {

		if (pos == 0)
			maleFemalePos = "M";
		else
			maleFemalePos = "F";

		Log.e("Spinner MAle Femael position", "" + maleFemalePos);

	}

	private void startRegistering() {
		new RegisterAsync(RegisterActivity.this, mProgressDialog, database,
				editAppPin.getText().toString().trim(), editFName.getText()
						.toString().trim(), editSName.getText().toString()
						.trim(), editEmail.getText().toString().trim(),
				editPass.getText().toString().trim(), editAddress1.getText()
						.toString().trim(), editAddress2.getText().toString()
						.trim(), editStreet.getText().toString().trim(),
				editTown.getText().toString().trim(), editCity.getText()
						.toString().trim(), editDistrictCounty.getText()
						.toString().trim(), editState.getText().toString()
						.trim(), editCountry.getText().toString().trim(),
				editPostcode.getText().toString().trim(), editMobile.getText()
						.toString().trim(), maleFemalePos.trim(),
				churchMemeberPos,
				editHomeTelephone.getText().toString().trim(), url,RegisterActivity.this,message).execute("");
	}
	
	View.OnClickListener l = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int top = editPass.getTop();
			scrollView1.scrollTo(0,top);
		}
	};

	@Override
	public void showMessage(String message) {
		if (message.equals("Member already exists. Use forgot password link to get your password.")) {
			showOKAleartForAlreadyRegistered("Message", "Member already exists. Use forgot password link to get your password.");
		} else {
			errorText.setVisibility(View.INVISIBLE);
			showOKAleartNewlyRegisterdMember("Congratulation", "Congratulation! Registration is successful. Please check your email for further details. This App will close now.");
		}
		btnPrevious_StepThree.setEnabled(false);
		btnSubmit_StepThree.setEnabled(false);
	}
	

}
