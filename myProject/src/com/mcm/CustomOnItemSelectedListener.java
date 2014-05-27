package com.mcm;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
 
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
 
	ArrayList<HashMap<String,String>> churchList;
	Context context;
	public CustomOnItemSelectedListener(Context context,ArrayList<HashMap<String,String>> churchList)
	{
		this.context = context;
		this.churchList = churchList;
	}
	
  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
	Toast.makeText(parent.getContext(), 
		"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
		Toast.LENGTH_SHORT).show();
	
	      int myPosition = parent.getSelectedItemPosition() ;
		  HashMap<String,String> s = churchList.get(myPosition);
		 String  clientIdSelected = s.get(GetChurchListFromServerApi.TAG_ID);
	  //tag_id is ur clientID. finally we get
		//clientId when we select from dropdown..
		
 	Log.w("clientIdSelected: ",clientIdSelected);	  
  }
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
 
}
