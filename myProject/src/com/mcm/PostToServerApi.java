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

import android.content.Context;
import android.util.Log;

public class PostToServerApi {

	Context context;
	String dataFromApp[];
	// server data for register user::
	String serverData[];
	// to get length of serverData[]..
	int dataLength;
	String url;
	int responseCodeFromServer;

	public void preparePost(Context context, String[] dataFromApp,
			String[] serverData, String url, int dataLength) {
		this.context = context;
		this.dataFromApp = dataFromApp;
		this.serverData = serverData;
		this.url = url;
		this.dataLength = dataLength;
		responseCodeFromServer = ((MCMApp) context.getApplicationContext())
				.getResponseCodeFromServer();

		dataFromApp = new String[dataLength];
		serverData = new String[dataLength];

		// //dataLength = dataFromApp.length; //means how many values..
		// pDialog = new ProgressDialog(context);
		// new LongOperation().execute("");
		POST(url);
		// return responseCodeFromServer;
	}// preparePost()...

	public String POST(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			Log.w("datalength: ", dataLength + "");
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";

			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			// accumulate values to jsonObject:
			for (int i = 0; i < dataLength; i++) {
				jsonObject.accumulate(serverData[i], dataFromApp[i]);
				// Log.w("api: ","serverdata:"+serverData[i]+" datafromapp:"+dataFromApp[i]);
			}// end for..
				// jsonObject.accumulate("ClientId", dataFromApp[0] );

			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string usin
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			// httpPost.setHeader("Host", "Localhost:52385");
			// httpPost.setHeader("Content-Length", "282");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);
			Log.e("RESPONSE MESSAGE", ""
					+ httpResponse.getStatusLine().getStatusCode());

			// below is response from server 201 OK, else some issues -->
			// responseCodeFromServer =
			// httpResponse.getStatusLine().getStatusCode();
			StaticVars.serverResponseCode = httpResponse.getStatusLine()
					.getStatusCode();
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
		StaticVars.serverResultString = result;
		Log.w("result from server : ", StaticVars.serverResultString);

		// 11. return result
		return result;
	}// end POST()....................

	private String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	} // end convertInputStreamToString()...

}
