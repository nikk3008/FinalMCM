package com.mcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class OldPostToServerApi {
	ProgressDialog pDialog;
	Context context;
	String dataFromApp[]; 
	//server data for register user::
	String serverData[] ;
	//to get length of serverData[]..
	int dataLength;
	String url;
	int responseCodeFromServer;
	public void preparePost(Context context,String[] dataFromApp,
			                           String[] serverData, String url,int dataLength) 
	{
		this.context = context;
		this.dataFromApp = dataFromApp;
		this.serverData = serverData;
		this.url = url;
		this.dataLength = dataLength;
	responseCodeFromServer=  (   (MCMApp)context.getApplicationContext() ).getResponseCodeFromServer();

		dataFromApp= new String[dataLength];
		serverData= new  String[dataLength];
		
		////dataLength = dataFromApp.length; //means how many values..
		pDialog = new ProgressDialog(context);
	    new LongOperation().execute("");
	   // return responseCodeFromServer;
	}//preparePost()...
	
	
	public String POST(String url){
	    InputStream inputStream = null;
	    String result = "";
	    try {
     Log.w("datalength: ",dataLength+"");
	        // 1. create HttpClient
	        HttpClient httpclient = new DefaultHttpClient();

	        // 2. make POST request to the given URL
	        HttpPost httpPost = new HttpPost(url);

	        String json = "";

	        // 3. build jsonObject
	        JSONObject jsonObject = new JSONObject();
	        //accumulate values to jsonObject:
	        for(int i=0;i<dataLength;i++)
		        {
	        	 jsonObject.accumulate(serverData[i],  dataFromApp[i]   );	
	        	// Log.w("api: ","serverdata:"+serverData[i]+" datafromapp:"+dataFromApp[i]);
		        }//end for..
	        //jsonObject.accumulate("ClientId",  dataFromApp[0]   );
	       
	        // 4. convert JSONObject to JSON to String
	        json = jsonObject.toString();

	        // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
	        // ObjectMapper mapper = new ObjectMapper();
	        // json = mapper.writeValueAsString(person); 

	        // 5. set json to StringEntity
	        StringEntity se = new StringEntity(json);

	        // 6. set httpPost Entity
	        httpPost.setEntity(se);

	        // 7. Set some headers to inform server about the type of the content   
	        httpPost.setHeader("Accept", "application/json");
	        httpPost.setHeader("Content-type", "application/json");
	      //  httpPost.setHeader("Host", "Localhost:52385"); 
	       // httpPost.setHeader("Content-Length", "282");
	        
	        // 8. Execute POST request to the given URL
	        HttpResponse httpResponse = httpclient.execute(httpPost);
	 //  Log.e("RESPONSE MESSAGE", "" + httpResponse.getStatusLine().getStatusCode());
	  
	   //below is response from server 201 OK, else some issues -->
	   responseCodeFromServer =  httpResponse.getStatusLine().getStatusCode();  
	       // 9. receive response as inputStream
	        inputStream = httpResponse.getEntity().getContent();

	        // 10. convert inputstream to string
	        if(inputStream != null)
	            result = convertInputStreamToString(inputStream);
	        else
	            result = "Did not work!";

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        Log.w("result from server : ",result);
	    // 11. return result
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
	
	private class LongOperation extends AsyncTask<String, Void, Void>
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
		    closeDialog();
		    Log.w("responseCodeFromServer post ends: :",""+responseCodeFromServer);
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

