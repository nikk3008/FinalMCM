package com.mcm.listener;
import com.mcm.R;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class LoginClickListner implements OnClickListener {

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
	
		case R.id.la_btnLogin:
			onLogInBtnClk(view);
			break;
			
		case R.id.la_forfotpaswword:
			onforgotPasswordClk(view);
			break;
			
		default:
			break;
		}
	}
	public abstract void onLogInBtnClk(View view);
	public abstract void onforgotPasswordClk(View view);
}
