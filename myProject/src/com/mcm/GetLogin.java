package com.mcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetLogin {
	ProgressDialog pDialog;
	Context context;
	
	String url;
	int responseCodeFromServer;
	public GetLogin(Context context,String url,ProgressDialog pDialog) 
	{
		this.context = context;
		this.url = url;
		this.pDialog = pDialog;
		new LongOperationss().execute("");
		
	}//preparePost()...
	
	
	public String POST(String url) {
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpGet httpPost = new HttpGet(url);
			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);
			Log.e("RESPONSE MESSAGE", ""
					+ httpResponse.getStatusLine().getStatusCode());
			responseCodeFromServer = httpResponse.getStatusLine().getStatusCode();
			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 11. return result

		Log.e("my Return Value", "" + result);
		return result;
	}

	
	private String convertInputStreamToString(InputStream inputStream) throws IOException{
	    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	    String line = "";
	    String result = "";
	    while((line = bufferedReader.readLine()) != null)
	        result += line;

	    inputStream.close();
	    return result;

	 } 
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
	
	private class LongOperationss extends AsyncTask<String, Void, Void>
	{
		boolean internetFlag = true;
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
			  // POST("http://mcmwebapi.victoriatechnologies.com/api/Member");
			   POST(url);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void args)
		{
			super.onPostExecute(args);
			if(internetFlag == false)
				Toast.makeText(context, "No Internet Connection! Please connect to internet then try again.", Toast.LENGTH_LONG).show();
			Log.w("onPostExecute() starts ","called up");
			
			if (responseCodeFromServer == 200 || responseCodeFromServer == 201) {
				context.startActivity(new Intent(context,MenuActivity.class));
			}
			else{
				Toast.makeText(context, "Invalid Credential", Toast.LENGTH_LONG).show();
			}
		    closeDialog();
		    Log.w("responseCodeFromServer post ends: :",""+responseCodeFromServer);
		  //  StaticVars.serverRes =  httpResponse.getStatusLine().getStatusCode();
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
		
	}//end inner class LongOperation.
}

