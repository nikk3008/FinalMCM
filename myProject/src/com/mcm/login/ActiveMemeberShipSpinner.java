package com.mcm.login;

import com.mcm.registration.InterfaceSPinnerId;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class ActiveMemeberShipSpinner implements OnItemSelectedListener{
	InterfaceSPinnerId sPinnerId;
	Spinner spinner;
	Context context;
	public ActiveMemeberShipSpinner(Context context,InterfaceSPinnerId sPinnerId, Spinner spinner)
	{
		this.context = context;
		this.sPinnerId = sPinnerId;
		this.spinner = spinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Log.e("SPINNER COUNT", ""+ spinner.getAdapter().getCount());
		sPinnerId.getSpinnerId(parent.getSelectedItemPosition());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
