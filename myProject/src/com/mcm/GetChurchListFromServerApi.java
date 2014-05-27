package com.mcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;



public class GetChurchListFromServerApi
{
	ProgressDialog pDialog;
	Context context;
	String url;
	ArrayList<HashMap<String,String>> list;
	 public GetChurchListFromServerApi(Context context,String url,ArrayList<HashMap<String,String> > list)
	 {
		this.context = context;
		this.url = url;
		this.list=  list;
		
		POST(url);
	 }//end constructor...
	
	String result = "";
	HttpResponse httpResponse = null;
	
	public void POST(String url) //get data.. list. from server
	  {
	    InputStream inputStream = null;
	   
	    try {

	       // 1. create HttpClient
	        HttpClient httpclient = new DefaultHttpClient();

	       	//2. create HttpGet object:
			HttpGet httpGet = new HttpGet(url);
			//3. finally make call to server and get the httpResponse:
			httpResponse = httpclient.execute(httpGet);
			
	        //4. Execute POST request to the given URL
	        HttpResponse httpResponse = httpclient.execute(httpGet);
	  Log.e("RESPONSE MESSAGE", "" + httpResponse.getStatusLine().getStatusCode());
	        // 5. receive response as inputStream
	        inputStream = httpResponse.getEntity().getContent();

	        // 6. convert inputstream to string
	        if(inputStream != null)
	            result = convertInputStreamToString(inputStream);
	        else
	            result = "Did not work!";

	       } catch (Exception e) 
			     {
			        e.printStackTrace();
			     }//end catch..
	    Log.e("result: ",result);
	    // 7. return result
	   // return result;
	    parseJsonToArrayList();
	    
	}//end of POST().........................
	public static final String TAG_ID = "Id";
	public static final String TAG_NAME = "Name";
	
	
	 
	 //below method to parse the response json::
	public void parseJsonToArrayList()
	{
		
		 
		try
         {
			 //this jsonstring does not have json array name::
         	//JSONObject jsonObj = new JSONObject(result.toString());
         	JSONArray jArray = new JSONArray(result.toString().trim());
         	//first clean up memberList();
         	list.clear();
         	/*
         	// now get the jsonArray node: info::
         	JSONArray info = jsonObj.getJSONArray("");
         	
         	// get length of info Array:
         	int length = info.length();
         	*/
         	//loop thru all contacts:
         	 for(int i=0;i<jArray.length();i++)
         	 {
         		 // Get jsonobject index:
         		 JSONObject c 			= 	 jArray.getJSONObject(i);
         		 // Now get all info at that jsonobject index:
         		 String id 				=	 c.getString(TAG_ID)		;
         		 String name 			=	 c.getString(TAG_NAME)		;
         	   		 //temp hashmap object  for single topic:
         		 HashMap<String,String> singleTopic = new HashMap<String,String>();
         		 //now adding each child node to HashMap Key=>value:
         		 //but we are interested in quite a few values.. not all.
         		 singleTopic.put(TAG_ID, id);
         		 singleTopic.put(TAG_NAME, name);
         	 		
				//adding contact to List arraylist:
         		list.add(singleTopic);
         		//now to get values from list
         		//HashMap<String,String> s = MemberList.get(0);
         		//s.get(TAG_ID);
         		//s.get(TAG_NAME);
         		
         			
         	 }//end for..........
             Log.e("drop down list","" + list);	//update well the list
         	
         }//end try block...............
          catch(Exception e)
          {
         	e.printStackTrace(); 
         	 
          }//end catch block
     	
     	
		
	}//end parseJsonToArrayList()...
	
	
	private String convertInputStreamToString(InputStream inputStream) throws IOException
	 {
	    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	    String line = "";
	    String result = "";
	    while((line = bufferedReader.readLine()) != null)
	        result += line;

	    inputStream.close();
	    return result;

	 } //end of convertInputStreamToString()....
	
}//end class GetMemberListFromServerApi
