package com.mcm;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/*
 * this class is responsible to make server call and get the response, response will be in Json format:
 * Remeber we get here json response, we are not gona parse it here.
 * so to parse we use another class named: GetContacts as AsynTask ... in this class we gonna call 
 * HttpCallHandler's method: makeHttpCall() to get json string.
 * GetContacts class is a inner class comes under MainActivity class.
 */
public class HttpCallHandler
{

	//declare response string:
	static String response = "";  //this would be the final response json string will be returned back...
	//declare get and post constants for get/post methods:
	public static final int GET =1;
	public static final int POST =2;
	
	//create a def. cons:
	HttpCallHandler()
	{}
	
	/*
	 * below method is responsible to make http call and get the response.
	 * url: url of server. to make request
	 * method: get/post http request method.
	 * params: http request params (optional) url?....
	 * 
	 */
	public String makeHttpCall(String url,int method)
	 {
		return this.makeHttpCall(url, method,null);
	 }//end makeHttpCall()..
	
	public String makeHttpCall(String url,int method, List <NameValuePair> params)
	{
		
		 //1. httpClient:
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//2. HttpEntity:
		HttpEntity httpEntity = null;
		//3. HttpResponse
		HttpResponse httpResponse = null;
	try{	
		//4. Checking HttpRequest method type:
		// what to do if method is POST:
		if(method == POST)
		{
		   //create HttpPost obj:
			HttpPost httpPost = new HttpPost(url);
			//checking whether has passed any params with url??
			if(params != null)
			{
				
		        httpPost.setEntity(new UrlEncodedFormEntity(params) );
			}//end if..
			//finally make call to server and get the httpResponse....
			httpResponse = httpClient.execute(httpPost);
				
			
		}//end if..
		  //wat to do if method is GET.
		else if(method == GET)
		{
		  //here first check any params has been passed with url:
			if(params != null)
			{
			  String paramString = URLEncodedUtils.format(params, "utf-8");
			  											//(parameters,encoding)
			  //then include params with url:
			  url += "?" + paramString;
			
			}//end if..                                
			
			//create HttpGet object:
			HttpGet httpGet = new HttpGet(url);
			//finally make call to server and get the httpResponse:
			httpResponse = httpClient.execute(httpGet);
			
			
		}//end elseif..
		
		//5. after checking GET/POST method get httpEntity object thru httpResponse:
				httpEntity = httpResponse.getEntity();
				//6. now get a json String thru httpEntity:
				response = EntityUtils.toString(httpEntity);
		
		
		}//end try bloc..
			catch (Exception e)
		    {
			  e.printStackTrace();
		    }//end catch block..........
	
			//7. send this response back to function call final step. good bye
	System.out.println("response: "+ response);		 
	  return response;
		
	}//end makeHttpCall().............................................
	
	
	
	

} //end class HttpCallHandler.
