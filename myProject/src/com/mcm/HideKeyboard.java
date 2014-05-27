package com.mcm;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;

public class HideKeyboard implements OnTouchListener {
    Context context;
	public HideKeyboard(Context context)
	{
		this.context = context;
	} //end constructor..
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) 
	{
		//get object of InputMetodManager.
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
		//hide kbd
		imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
		//imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		return false;
	}

}
