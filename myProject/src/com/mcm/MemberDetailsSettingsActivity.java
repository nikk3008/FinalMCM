package com.mcm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MemberDetailsSettingsActivity extends Activity implements OnClickListener
	 {
	  //declare views:
	   Button mds_btnStep1,mds_btnStep2,mds_btnStep3;
	   LinearLayout mds_layoutStep1,mds_layoutStep2,mds_layoutStep3;
	  
	   //step1 views:
	   Button mds_btnStep1Next;
	   //step 2 views:
	   Button mds_btnStep2Previous,mds_btnStep2Next;
	   
	   //step 3 views::
	   Button mds_btnStep3Previous,mds_btnStep3SaveAndReturnToHome;
	   @Override
	protected void onCreate(Bundle savedInstanceState) 
	   {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_details_settings_activity_layout);
		setAllViews();
		//Step1 : by default Step1 button is enabled and first layout will be enabled:
		settingColors(mds_btnStep1,mds_btnStep2,mds_btnStep3);
		 mds_layoutStep1.setVisibility(View.VISIBLE); //enable step1 layout.
		 mds_layoutStep2.setVisibility(View.GONE); //enable step1 layout.
		 mds_layoutStep3.setVisibility(View.GONE); //enable step1 layout.
		
		
	   }//end onCreate()
	   
	   //bgcolor for button when selected: #4181EE
	   
	   
	   @Override //when u click any button:
		public void onClick(View v) 
		    {
			switch(v.getId())
	    	{
		    	case R.id.mds_btnStep1 :
		    	case R.id.mds_btnStep2Previous:
		    		   //Log.d("mds_btnStep1","clicked");
		    		   settingColors(mds_btnStep1,mds_btnStep2,mds_btnStep3);
		    		   mds_layoutStep1.setVisibility(View.VISIBLE);
		    		   mds_layoutStep2.setVisibility(View.GONE);
		    		   mds_layoutStep3.setVisibility(View.GONE);
		    		   
		    		   break;
		    	case R.id.mds_btnStep2 :
		    	case R.id.mds_btnStep1Next :
		    	case R.id.mds_btnStep3Previous:
			 		   //Log.d("mds_btnStep2","clicked");
			 		   settingColors(mds_btnStep2,mds_btnStep1,mds_btnStep3);
			 		   mds_layoutStep2.setVisibility(View.VISIBLE);
		    		   mds_layoutStep1.setVisibility(View.GONE);
		    		   mds_layoutStep3.setVisibility(View.GONE);
		    		   
			 		   break;
		    	case R.id.mds_btnStep3 :
		    	case R.id.mds_btnStep2Next:
			 		   //Log.d("mds_btnStep3","clicked");
			 		   settingColors(mds_btnStep3,mds_btnStep2,mds_btnStep1);
			 		   mds_layoutStep3.setVisibility(View.VISIBLE);
		    		   mds_layoutStep1.setVisibility(View.GONE);
		    		   mds_layoutStep2.setVisibility(View.GONE);
		    		   
			 		   break;
		    	case R.id.mds_btnStep3SaveAndReturnToHome :	   
		    		  //do saving work to webservice post work::
		    		 //go back to register/signin activity:
		    		//finish(); //kill back btn tensions...
		    		Intent in = new Intent(MemberDetailsSettingsActivity.this,RegisterOrSignInActivity.class);
		    		startActivity(in);
		    		break;
			    
	    	}//end switch..	 
				
			}//end onClick()
	   
	   
	   void settingColors(Button b1,Button b2,Button b3)
	    {
	    	  //set textcolor to white:
	    	b1.setTextColor(Color.parseColor("#ffffff"));  //text color to white
	    	b1.setBackgroundColor(Color.parseColor("#4181EE"));  //#4181EE some light blue  color background
		       
			   //and set others back to red:
	    	b2.setTextColor(Color.parseColor("#223C7D")); //text color some what dark blue..
	    	b2.setBackgroundColor(Color.parseColor("#00000000"));  //transparent color background
			   
	    	b3.setTextColor(Color.parseColor("#223C7D"));  //text color some what dark blue..
	    	b3.setBackgroundColor(Color.parseColor("#00000000"));  //transparent color background
	    	
	    	
	    }//end resetColor
	   public void setAllViews()
	   {
		   mds_btnStep1 = (Button)findViewById(R.id.mds_btnStep1);
		   mds_btnStep2 = (Button)findViewById(R.id.mds_btnStep2);
		   mds_btnStep3 = (Button)findViewById(R.id.mds_btnStep3);
		   mds_layoutStep1 = (LinearLayout)findViewById(R.id.mds_layoutStep1);
		   mds_layoutStep2 = (LinearLayout)findViewById(R.id.mds_layoutStep2);
		   mds_layoutStep3 = (LinearLayout)findViewById(R.id.mds_layoutStep3);
		   
		   //below code to hide keyboard on touch ...
		   //get Id of TopMost Layout and set onTouchListener() to it. to hide keyboard on outside click.
	          //mds_layoutStep1.setOnTouchListener(new HideKeyboard(this));
		    mds_layoutStep1.setOnTouchListener(new HideKeyboard(this));
		    mds_layoutStep2.setOnTouchListener(new HideKeyboard(this));
		    
		    
		   //from step1 layout:
		   mds_btnStep1Next = (Button)findViewById(R.id.mds_btnStep1Next);
		   
		   //from step2 layout:
		   mds_btnStep2Previous = (Button)findViewById(R.id.mds_btnStep2Previous);
		   mds_btnStep2Next = (Button)findViewById(R.id.mds_btnStep2Next);
		   
		   
		   //from step3 layout:
		   mds_btnStep3Previous = (Button)findViewById(R.id.mds_btnStep3Previous);
		   mds_btnStep3SaveAndReturnToHome = (Button)findViewById(R.id.mds_btnStep3SaveAndReturnToHome);
		   
		   //set listeners:
		   mds_btnStep1.setOnClickListener(this);
		   mds_btnStep2.setOnClickListener(this);
		   mds_btnStep3.setOnClickListener(this);
		   //step1
		   mds_btnStep1Next.setOnClickListener(this);
		   
		   //step2
		   mds_btnStep2Previous.setOnClickListener(this);
		   mds_btnStep2Next.setOnClickListener(this);
		   
		   //step3
		   mds_btnStep3Previous.setOnClickListener(this);
		   mds_btnStep3SaveAndReturnToHome.setOnClickListener(this);
		   
		   
		   
	   }//end setAllViews

	
	}//end class MemberDetailsSettingsActivity
