package com.mcm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements 
OnClickListener, OnItemSelectedListener,Constants
	 {

	 String prepareSubject ="";
	 String prepareMessage ="";
	 String emailListForApproval = "";
	 String senderMail= "apptest9887@gmail.com"; //must be any gmail id,
	 String senderP = "AllIsWell";     //must be gmail id, password.
	
	
	ProgressDialog pDialog;Context context;
	//top view buttons:
	Button ra_btnStep1,
	ra_btnStep2;
	
	//declare views of Step1 :
	EditText ra_editAppPin,ra_editFName,ra_editSName,ra_editEmail,ra_editEmailConfirm,ra_editPass,ra_editConfirmPass;
	Spinner ra_spSelectChurchMembership;
	Spinner ra_spMaleFemale;
	Button ra_btnNextStep2;
	//declare views of Step2 :
	EditText ra_editMobile,ra_editHomeTelephone,ra_editAddress1,ra_editAddress2;
	EditText ra_editStreet,ra_editTown,ra_editCity,ra_editDistrictCounty,ra_editState;
	EditText ra_editPostcode,ra_editCountry;
	Button ra_btnStep2Previous,ra_btnSubmit;
	
	TextView ra_tvFormMsg;
	
	String errMsg;
	ScrollView ra_scrollView1,ra_scrollView2;
//	ArrayList< HashMap< String,String >  > memberList;
//	int responseCodeFromServer;
	   @Override
	protected void onCreate(Bundle savedInstanceState) 
	   {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity_layout);
		context = RegisterActivity.this;
		pDialog = new ProgressDialog(context);
		
		
		
//		memberList=  (   (MCMApp)this.getApplication() ).getMemberList();
//		responseCodeFromServer=  (   (MCMApp)context.getApplicationContext() ).getResponseCodeFromServer();
		//set all views refs:
    	init();
    	
	 	
    	//Step1 : by default Step1 button is enabled and first layout will be enabled:
    	settingColors(ra_btnStep1,ra_btnStep2);
    	ra_scrollView1.setVisibility(View.VISIBLE);
    	ra_scrollView2.setVisibility(View.GONE);
    	
    	
    	
		
	   }//end onCreate()
	
	  
	/********************validation code starts here ********************************/
	
	
	public void postToServer()
	{
	  PostToServerApi ptsaObject = new PostToServerApi();
	  int responseCodeFromServer=0; //will be response from server.
	  String url="http://mcmwebapi.victoriatechnologies.com/api/Member";
	  int dataLength = 18; //means how many fields. datafromapp/serverdata
		//pass one string array:
	  String dataFromApp[] = new String[18];
	  dataFromApp[0] = ra_editAppPin.getText().toString();
	  dataFromApp[1] = ra_editFName.getText().toString();
	  dataFromApp[2] = ra_editSName.getText().toString();
	  dataFromApp[3] = ra_editPass.getText().toString();
	  dataFromApp[4] = ra_editEmail.getText().toString();
	  //new fields::
	  dataFromApp[5] = ra_editAddress1.getText().toString();
	  dataFromApp[6] =  ra_editAddress2.getText().toString();   
	  dataFromApp[7] = ra_editStreet.getText().toString();
	  
	  dataFromApp[8] = ra_editTown.getText().toString();
	  dataFromApp[9] = ra_editCity.getText().toString();
	  dataFromApp[10] = ra_editDistrictCounty.getText().toString();
	  dataFromApp[11] = ra_editState.getText().toString();
	  
	  dataFromApp[12] = ra_editCountry.getText().toString();
	  dataFromApp[13] = ra_editPostcode.getText().toString();
	  dataFromApp[14] = ra_editMobile.getText().toString();
	  
	  dataFromApp[15] =  MaleFemale ;      //ra_lvMaleFemale.getText().toString();
	  dataFromApp[16] =  ""+spMemberShipIndex   ; //ra_lvSelectChurchMembership.getText().toString();
	  dataFromApp[17] =  ra_editHomeTelephone.getText().toString();
	  
	  //server data for register user::
	  String serverData[] = new String[18];
	  serverData[0] = "ClientId"  ; //must be as is, based on server api..
	  serverData[1] = "FirstName" ;
	  serverData[2] = "SurName"   ;
	  serverData[3] = "Password"  ;
	  serverData[4] = "EmailId"   ;
	  //new fields ::
	  serverData[5]  = "Address1"   ;
	  serverData[6]  = "Address2"   ;
	  serverData[7]  = "Street"     ;
	  //
	  serverData[8]  = "Town"       ;
	  serverData[9]  = "City"       ;
	  serverData[10] = "County"    ;
	  //
	  serverData[11] = "State"   ;  //new field
	  serverData[12] = "Country"   ;
	  serverData[13] = "PostCode"  ;
	  serverData[14] = "Mobile"    ;
	  //
	  serverData[15] = "Sex"        ;
	  serverData[16] = "ChurchMembershipId"  ;
	  serverData[17] = "HomeTelephone"       ;
	  
	 // Log.w("datafromapp: ",dataFromApp[0]);
	  //now call preparePost() PostToServerApi class:
	   ptsaObject.preparePost(this,dataFromApp,serverData,url,dataLength); //Posting to server... 
	  //now do here some checks for responseCodeFromServer.
	 
		
		
	}
	private class LongEmailSendOperation extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try {
				try {
					GMailSender sender = new GMailSender(senderMail,
							senderP);
					sender.sendMail(prepareSubject, prepareMessage,senderMail
							,emailListForApproval);
					Log.e("email","is going..");
			    } catch(Exception e) 
			    {
				   Log.e("SendMail", e.getMessage(), e);
			    }//end catch()...
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	//to handle  postToServer()
	private class PostToServerOperation extends AsyncTask<String, Void, Void>
	{
		boolean internetFlag = true;
		 ArrayList< HashMap< String,String >  > list;
		 
		
		 String appRegStatus = "";
		 
		 String clientMemberId = ""; //new
		 String clientPassword =  ""; //new
		 String clientId  = "";
		 String clientEmail = "";
		 String clientFirstname = "" ;
		 String clientSurname = "";
		 String clientFullName = "";
		 
		 String clientSex = "";
		 String hisHer = "";  //will based on clientSex..
		 ArrayList<String> emailsArrayList = new ArrayList<String>();
		
		 StringTokenizer st = null;
		 
		@Override
		protected void onPreExecute()
		{
	  		super.onPreExecute();
	  		Log.w("onPreExecute()","called up");
		    showDialog(); //dont show in splash.
  		 
		}//end onPreExecute()...
		@Override
		protected Void doInBackground(String... params) {
			//Step 1: check for internet  connection:
			if(isInternetConnected() == false)
			{
				internetFlag = false; //NO Internet!! can't Post to server	
				
			}//end if
			else
			{	//Yes you have Internet connection. Happily proceed.
				internetFlag = true;
				 postToServer();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void args)
		{
			super.onPostExecute(args);
			closeDialog(); 
			Log.w("StaticVars.serverRes(int): ", String.valueOf(StaticVars.serverResponseCode) );
			
			 if(internetFlag == false)
			  { 
				
				Toast.makeText(context, "No Internet Connection! Please connect to internet then try again.", Toast.LENGTH_LONG).show();
			  Log.w("onPostExecute() starts ","called up");
			  return;
		      
			  }//end if...
		     /*
		      * now get serverResultString (json), parse it and fetch
		      * list of emails from EmailListForApproval JsonTag
		      * and fetch status from AppRegistrationStatus JsonTag.
		      * 
		      */
			 String jsonString = StaticVars.serverResultString;
			/////////////
			 list =new ArrayList< HashMap< String,String >  >();
			 
			 try
	         {
					JSONObject jObj       =  new JSONObject(jsonString.toString());
		         	emailListForApproval  =	 jObj.getString( TAG_EMAIL_LIST_FOR_APPROVAL );
	         		appRegStatus	      =	 jObj.getString( TAG_APP_REGISTRATION_STATUS );
	         		clientSex             =  jObj.getString(TAG_CLIENT_SEX);
	         		//
         		  clientMemberId   =  jObj.getString(TAG_MEMBER_ID);
         		  clientId         =  jObj.getString(TAG_CLIENT_ID);
         		  clientEmail      =  jObj.getString(TAG_CLIENT_EMAIL);
         		  clientFirstname  =  jObj.getString(TAG_CLIENT_FIRST_NAME);
         		  clientSurname    =  jObj.getString(TAG_CLIENT_SURNAME);
         		  clientPassword   =  jObj.getString(TAG_CLIENT_PASSWORD);
	              ra_tvFormMsg.setText(appRegStatus); //show status to client.on form
		         	  
		         	   
		         	   
		         	   
	         }//end try ..........................
			   catch(Exception e)
			   {
				   e.printStackTrace();
			   }//end catch..
			 
			 
			// Log.w("emailsArraylist:",emailsArrayList.toString()); //shows []
			 
			  //now do email work if code is 201:
			  if( StaticVars.serverResponseCode == 201 && appRegStatus.equals("You are registered successfully")  )
			   {
				  //send email to recent registered user, admin thats john.
				  // String[] recipients = new String[2];
				  // recipients[0] = "jbunian@yahoo.com"; //admin email.
				  // recipients[1] = ra_editEmail.getText().toString(); //recent user email
				   
				  //Step1 now for database : inserting row to member table:
	         	    insertRowInTable();
	         	   //Step 3: prepare Email contents;;
	         	    prepareEmailContents();
	         	  //Step2 : send emails to admins & new client.
				           /// sendEmail(emailsArrayList,prepareSubject,prepareMessage); //using intent..
	         	   //sendEmail(emailListForApproval,prepareSubject,prepareMessage); 
				   new LongEmailSendOperation().execute();
	         	    //all done.
				   //reset staticvars to 5000; some random value.
				    StaticVars.serverResponseCode=5000;
			   }//end if.
			   else
			   {
				  Log.w("unable to send email.","becoz user already exists.."); 
				  Log.w("responsefromserver(int): ", String.valueOf(StaticVars.serverResponseCode) );
				  //reset StaticVars.serverRes to some 5000 random value.
				  StaticVars.serverResponseCode=5000;
			   }//end else.
		  //  Log.w("responseCodeFromServer post ends: :",""+responseCodeFromServer);
		}
		void showDialog()
    	{
	  		pDialog.setMessage("Please Wait...");
	  		pDialog.setCancelable(false);
	  		 //show dialog:
	  		pDialog.show();
    	}//end showDialog()
    	
    	void closeDialog()
    	{
    		  //dismiss pDialog
			if(pDialog.isShowing())
			{
			  pDialog.dismiss();
			}//end if..
    	}//end closeDialog()
    	
    	public void insertRowInTable()
    	{
    		  //dbhelper:
 			  DBHelper dbHelper;
 			  ContentValues cv  = new ContentValues();
	 
			 // Clear it first:
			 cv.clear();
			 // insert column values to cv object first, don't put created_on it will have default value:
			 cv.put(MEMBER_ID    , clientMemberId);
			 cv.put(CLIENT_ID    , clientId );
			 cv.put(FIRST_NAME   , clientFirstname);
			 //
			 cv.put(SUR_NAME     , clientSurname);
			 cv.put(EMAIL_ID     , clientEmail);
			 cv.put(PASSWORD     , clientPassword);
            
            dbHelper = new DBHelper(context);
            dbHelper.openDb();
      //    dbHelper.insertRow(String[] colValues);
            dbHelper.insertRow(cv);
            //finally close db:
            dbHelper.closeDb();
	    //Done, we have inserted the row in member table..done
      //Next reopen the database and fetch all rows, to 
      //check whether row is inserted or not..
            dbHelper.openDb();
            Cursor c = dbHelper.showRecord();
           
            while(c.moveToNext())
            {
Log.e("show recs: ", c.getInt(0)+" "+ c.getInt(1) +" "+ c.getInt(2) +" "+ c.getString(3) +" "+ c.getString(4) +" "+ c.getString(5)+ " "+c.getString(6) );	  
            }//end while..
         dbHelper.closeDb();
        
    	 }//end insertRowInTable()................
		
    	  public void prepareEmailContents()
    	  {
    			//now use String tokenizer to get all emails in string array:
	         	// st = new StringTokenizer(emailListForApproval,";");
	         	 Log.w("emaillist4approval:",emailListForApproval); //contains ; sep. strings.
	         	emailListForApproval =	emailListForApproval.replaceAll(";", ","); //we got , separated emails.
	         	
	         	/*  
	         	Log.e("no. of tokens: ",st.countTokens()+""); // means 4 emails it hav
	         	
	         	 //now fetch tokens from st:
	         	   while(st.hasMoreElements())
	         	   {
	         		  //populating all emails to emailArrayList. will be admin emails.
	         		  emailsArrayList.add(st.nextToken())  ;
	         	   }//end while..
	         	   */
	         	//after adding all admin emails. i would like to add
	         	// new client's email:
	         	  //emailsArrayList.add(clientEmail); //now it hav all admins&client email.
	         	emailListForApproval = emailListForApproval+","+clientEmail ; //its ready..
	         	Log.w("emaillist4approval:",emailListForApproval); //contains , sep. strings.	 
	         	
	         	clientFullName = clientFirstname+ " "+ clientSurname;
	         	  //now set hisHer based on clientsex:
	         	  if(clientSex.equals("M") )
	         	   hisHer = "his";
	         	  else
	         		hisHer = "her";
	         	 
	         	 //now create subject string for email:
	         	  prepareSubject = "Client Id:"+clientId+ " is successfully registered with MyChurchMate App." ;
	         	  prepareMessage = "Greetings. \n The new client with id:"+clientId+ " is successfully registered with" +
	         	  " MyChurchMate App.\n Please quote "+ hisHer +" details below: \n" +
	         	  "\t Client Fullname: " + clientFullName + ".\n" +
	         	  "\t Client id: " + clientId + ".\n \t Client E-mail: " +clientEmail + ".\n" +
	         	  " \t This request is waiting for Admin Approval. \n Thanks & Regards \n MyChurchMate Team.";
	         	 Log.w("subject: ",prepareSubject);
	         	 Log.w("message: ",prepareMessage);
    		  
    	  }//end prepareEmailContents()..
    	
    	
	}//end inner class PostToServerOperation


	  public void init()
	   {
		   ra_scrollView1 = (ScrollView)findViewById(R.id.scrollView1);
		   ra_scrollView2 = (ScrollView)findViewById(R.id.scrollView2);
		  
	    
	    	ra_btnStep1  = (Button)findViewById(R.id.ra_Step1);
	    	ra_btnStep2  = (Button)findViewById(R.id.ra_Step2);
	    	ra_btnStep1.setOnClickListener(this);
	    	ra_btnStep2.setOnClickListener(this);
	    	
            //Step 1 views ::
	       ra_editAppPin = (EditText)findViewById(R.id.ra_editAppPin);
		   ra_editFName  = (EditText)findViewById(R.id.ra_editFName);
		   ra_editSName  = (EditText)findViewById(R.id.ra_editSName);
		   ra_editEmail  = (EditText)findViewById(R.id.ra_editEmail);
		   ra_editEmailConfirm = (EditText)findViewById(R.id.ra_editEmailConfirm); 
		   ra_editPass   = (EditText)findViewById(R.id.ra_editPass);
		   ra_editConfirmPass = (EditText)findViewById(R.id.ra_editConfirmPass);
		   //added new in step1:
		   ra_spSelectChurchMembership = (Spinner)findViewById(R.id.ra_spSelectChurchMembership);
		   ra_spMaleFemale             = (Spinner)findViewById(R.id.ra_spMaleFemale);
		   ra_btnNextStep2 = (Button)findViewById(R.id.ra_btnNextStep2);
		   
		   //listeners for step1
//		   ra_btnNextStep2.setOnClickListener(this);
		   
		   //updating male/female list:
		   updateMaleFemaleSpinner();
		   //same way update membership spinner
//		   updateMemberShipSpinner();
		   
		   //Step 2 views::
		   ra_editMobile        = (EditText)findViewById(R.id.ra_editMobile);
		   ra_editHomeTelephone = (EditText)findViewById(R.id.ra_editHomeTelephone);
		   ra_editAddress1      = (EditText)findViewById(R.id.ra_editAddress1);
		   ra_editAddress2     	= (EditText)findViewById(R.id.ra_editAddress2);
		   ra_editStreet       	= (EditText)findViewById(R.id.ra_editStreet);
		   ra_editTown         	= (EditText)findViewById(R.id.ra_editTown);
		   ra_editCity         	= (EditText)findViewById(R.id.ra_editCity);
		   ra_editDistrictCounty  = (EditText)findViewById(R.id.ra_editDistrictCounty);
		   ra_editState         =   (EditText)findViewById(R.id.ra_editState);
		   ra_editPostcode      = (EditText)findViewById(R.id.ra_editPostcode);
		   ra_editCountry       = (EditText)findViewById(R.id.ra_editCountry);
//		   ra_btnStep2Previous	= (Button)findViewById(R.id.ra_btnStep2Previous);	    
//		   ra_btnSubmit 		= (Button)findViewById(R.id.ra_btnSubmit);
//		   // listeners for step2:
//		   ra_btnStep2Previous.setOnClickListener(this);
//		   ra_btnSubmit.setOnClickListener(this);
	  
		  //common err msg for step1,step2..
		   ra_tvFormMsg = (TextView)findViewById(R.id.ra_tvFormMsg);
	 
	 }// end setAllViews()...............

      public void updateMaleFemaleSpinner()
      {
    	  String[] sex = new String[2]; //for male/female Spinner.
    	 // sex[0] = "Select Male/Female";
    	  sex[0] = "Male";
    	  sex[1] = "Female"; //for spinner/list of male/female.
    	  
    	  ArrayAdapter<String> dataAdapter = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sex);
    	  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  		//finally set adapter to ur spinner
	  	 	ra_spMaleFemale.setAdapter(dataAdapter);
	  	    //register it with listener::
	  	 	ra_spMaleFemale.setOnItemSelectedListener(this);
	  	 	
      }//end updateMaleFemaleSpinner()............
     
//      public void updateMemberShipSpinner() //will get from server json..
//      {
//    	 // GetMemberListFromServerApi obj = new GetMemberListFromServerApi(this,memberList);
//    	  
//    	  //memberList = obj.getMemberShip();  
//    	  Log.e("memberlistsize: ",""+memberList.size());
//    	//now to get values from MemberList, get only name,and put in array
//    	  //string, becoz arrayadapter wont take ur arraylist directly..
//    	  String nameList[] = new String[memberList.size()];
//    	  for(int i=0;i<memberList.size();i++)
//    	  {
//    		  HashMap<String,String> s = memberList.get(i);
//    	   	 //s.get(GetMemberListFromServerApi.TAG_ID);
//    		  nameList[i] = s.get(GetMemberListFromServerApi.TAG_NAME);  
//    		  Log.e("namelist: ",nameList[i]);
//    	  }//end for...
//   		
//    	//next create array adapter:
//  		ArrayAdapter<String> dataAdapter = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,nameList);
//  	 	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//  		//finally set adapter to ur spinner
//  	 	ra_spSelectChurchMembership.setAdapter(dataAdapter);
//  	    
//  		//register it with listener::
//  	 	ra_spSelectChurchMembership.setOnItemSelectedListener(this);
//  		//Log.d("ok:",ra_spSelectChurchMembership.getSelectedItem().toString());
//    	  
//    	  
//    	      	  
//    	  
//      }//end updateMemberShipSpinner.
      
    String MaleFemale =null;
    String memberShipSelected = null;
    int spMemberShipIndex;
    @Override //thats for spinner thru OnItemSelectedListener 
  	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
  	{
  		switch(parent.getId())
  		{
  		
  		case R.id.ra_spMaleFemale : 
  			
  			       if( ra_spMaleFemale.getSelectedItem().toString().equals("Male") )
  			         {
  			    	   MaleFemale = "M"; //as per Api rule
  			    	   Log.w("malefemale:",MaleFemale);
  			    	 
  			         }//end if.
  			       else if( ra_spMaleFemale.getSelectedItem().toString().equals("Female")    )
	  			       {
	  			    	 MaleFemale = "F"; //as per Api rule
	  			    	 Log.w("malefemale:",MaleFemale);
	  			    	 
	  			       }//end else if.
  			        break;
  			        
  		case R.id.ra_spSelectChurchMembership :
  			memberShipSelected = ra_spSelectChurchMembership.getSelectedItem().toString();
  			  spMemberShipIndex = ra_spSelectChurchMembership.getSelectedItemPosition() ;
  			Log.w("memberShipSelected:",memberShipSelected); 
  			Log.e("spMemberShipIndex:",""+spMemberShipIndex); 
		        break;
  			        
  		 default:
  			     // Log.w("malefemale:","inside switch default");
  		
  		}//end switch..
   
  		
  	} //end of onItemSelected()
 
      
	@Override //take care about all buttons event ::
	public void onClick(View v) 
	    {
		  switch(v.getId())
		  {
			  case R.id.ra_btnNextStep2: 
//			  case R.id.ra_btnStep2: 
//				  if(isStep1ValidationDone() == true)
//				  {
//					  ra_tvFormMsg.setText(""); //clean up any error msg.
//					  ra_tvFormMsg.setTextColor(Color.WHITE); 
//					  settingColors(ra_btnStep2,ra_btnStep1); //for very top layout Step1,Step2.
//					  ra_scrollView1.setVisibility(View.GONE);
//					  ra_scrollView2.setVisibility(View.VISIBLE);
//				  }//end if.
				  
			  break;
//			  case R.id.ra_btnStep2Previous:
//			  case R.id.ra_btnStep1:
//				  settingColors(ra_btnStep1,ra_btnStep2); //for very top layout Step1,Step2.
//				  ra_scrollView1.setVisibility(View.VISIBLE);
//				  ra_scrollView2.setVisibility(View.GONE);
//				  
//			  break;
//			  case R.id.ra_btnSubmit:
//				      if(isStep2ValidationDone() == true && isStep1ValidationDone() == true )
//				      {
//				    	  //if all fields validated in step1 & step2 fields then only post data to server.
//				    	  ra_tvFormMsg.setText(""); //clean up any error msg.
//						  ra_tvFormMsg.setTextColor(Color.WHITE); 
//				    	  new  PostToServerOperation().execute();
//				      }
//				 
//			  break;
		  
		  }//end switch
		  
		
		
	    }//end onClick()...
	 
	   void settingColors(Button b1,Button b2)
	    {
	    	  //set textcolor to white: like it is enabled
	    	b1.setTextColor(Color.parseColor("#ffffff"));  //text color to white
	    	b1.setBackgroundColor(Color.parseColor("#4181EE"));  //#4181EE some light blue  color background
		       
			   //and set others back to red: like it is disabled.
	    	b2.setTextColor(Color.parseColor("#223C7D")); //text color some what dark blue..
	    	b2.setBackgroundColor(Color.parseColor("#00000000"));  //transparent color background
			   
	    	
	    	
	    }//end resetColor


	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
		{
			//leave it...
			
		}//end onNothingSelected
	
	//function to check network connectivity::
		public boolean  isInternetConnected()
		{
			ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
			NetworkInfo nwInfo = conMgr.getActiveNetworkInfo();
			if(nwInfo != null && nwInfo.isConnected())
			    return true; //Yes you  have Internet connection
			else 
				return false; // You don't have Internet Connection.
			
		} //end isConnected()...
		
		
/************** Below comes all validation code ****************************/		
		//checks Step1 validation. will be set on Next button.
		public boolean isStep1ValidationDone()
			{
//			   ra_tvFormMsg.setTextColor(Color.RED); //if any error.
				if( ra_editAppPin.getText().toString().trim().equals("") )
					setErrMsg("App PIN");
				else if( ra_editFName.getText().toString().trim().equals("") ) 
					setErrMsg("First Name");
				else if( ra_editSName.getText().toString().trim().equals("") ) 
					setErrMsg("SurName");
				else if( ra_editEmail.getText().toString().trim().equals("") ) 
				    
					setErrMsg("Email");
				else if(checkEmail() == false)
					ra_tvFormMsg.setText("Please give valid email.");	 
				 
		       else if( ra_editEmailConfirm.getText().toString().trim().equals("") ) 
				    	setErrMsg("Confirm Email");
				
		       else if( checkConfirmEmail() == false ) 
		    	   ra_tvFormMsg.setText("Email and Confirm Email must be equal.");
				
				//in password cased i m not trimming spaces from start/end of word, user may keep spaces
				  //as password..
				else if( ra_editPass.getText().toString().equals("") ) 
					setErrMsg("Password");
				else if( ra_editConfirmPass.getText().toString().equals("") ) 
					setErrMsg("Confirm Password");
				else if( checkConfirmPassword() == false ) 
					ra_tvFormMsg.setText("Password and Confirm Password must be equal.");
				else
					return true; // OK validated.
				
				return false; // NOT validated.

			}//end isStep1ValidationDone
	
		//checks Step2 validation. will be set on Submit button.
				public boolean isStep2ValidationDone()
				{
					ra_tvFormMsg.setTextColor(Color.RED); //if any error.
					//Step 1: now check for mobile field -->
					  if( ra_editMobile.getText().toString().trim().equals(""))
						 {
						  setErrMsg("Mobile Number");
						    
						 }//end else if. //here check for each field too.
				//Step 2: now check for address1 field -->
					 else if( ra_editAddress1.getText().toString().trim().equals(""))
						 {
						 setErrMsg("Address 1");
						    
						 }//end else if. 
				  
				//Step 3: now check for Street field -->
					 else if( ra_editStreet.getText().toString().trim().equals(""))
						 {
						 setErrMsg("Street");
						     
						 }//end else if. 
				//Step 4: now check for  Town field -->
					 else if( ra_editTown.getText().toString().trim().equals(""))
						 {
						 setErrMsg("Town"); 
						    
						 }//end else if. 
				
				//Step 5: now check for  District/County field -->
					 else if( ra_editDistrictCounty.getText().toString().trim().equals(""))
						 {
						 setErrMsg("District/County");
						   
						 }//end else if. 
//			   //Step 6: now check for  State field -->
//					 else if( ra_editState.getText().toString().trim().equals(""))
//						 {
//						 setErrMsg("State");
//						   
//						 }//end else if.  
				//Step 7: now check for  Postcode field -->
					 else if( ra_editPostcode.getText().toString().trim().equals(""))
						 {
						 setErrMsg("Postcode");
						    
						 }//end else if. 
				//Step 8: now check for  Country field -->
					 else if( ra_editCountry.getText().toString().trim().equals(""))
						 {
						 setErrMsg("Country");
						    
						 }//end else if.
				  //Step last one.: u hav gone thru all validations. happy to go no issues...	
					  else 
					    {
						  return true; //all input by user is validated.
					    }
					return false;  //user has missed some field(s).
					
				}//end isStep2ValidationDone()
				public boolean checkEmail()
				{
					//first clear old err msg:
					//ra_tvError.setText(""); 
					  Pattern EMAIL_PATTERN = Pattern
							 .compile("[a-zA-Z0-9+._%-+]{1,100}" + "@"
							 + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
							 + "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}"+
							              ")+");	
					  if(! EMAIL_PATTERN.matcher(ra_editEmail.getText().toString()).matches())
					  {
						  
						  return false; // NOT OK
					  }//end if.
					      return true; // OK
					
				}//end checkEmail
				
				
				  public boolean checkConfirmPassword()
				    {
				    	//first clear any old error msg:
				  	//	  ra_tvError.setText("");
				    	 //check password and confirm password are equal or not::
				    	 if( !ra_editPass.getText().toString().equals( ra_editConfirmPass.getText().toString()) )
				    	 {
				    		 return false; // NOT OK
				    		
				    	 }
				    	return true; //OK
				    }//end checkConfirmPassword
			public boolean checkConfirmEmail()
				    {
				    	//first clear any old error msg:
				  	//	  ra_tvError.setText("");
				    	 //check password and confirm password are equal or not::
				    	 if( !ra_editEmail.getText().toString().equals( ra_editEmailConfirm.getText().toString()) )
				    	 {
				    		 return false; // NOT OK
				    		
				    	 }
				    	return true; //OK
				    }//end checkConfirmEmail
			 
			  public void setErrMsg(String msg)
				  {
					  ra_tvFormMsg.setText("Please fill "+msg+ " field."); 
				  }//end setErrMsg
				 
	   
	}//end class RegisterOrSignInActivity
