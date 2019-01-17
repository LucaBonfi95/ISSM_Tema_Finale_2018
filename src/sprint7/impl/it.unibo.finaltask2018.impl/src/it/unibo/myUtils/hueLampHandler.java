package it.unibo.myUtils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import it.unibo.qactors.akka.QActor;

public class hueLampHandler {

	private static String _username;
	private static String _lampId;
	private static String _bridgeIp;
	private static String _url;
	
	private static HttpClient httpClient;
	
	public static void init(QActor actor, String bridgeIp, String username, String lampId) {
		_bridgeIp = bridgeIp;
		_username = username;
		_lampId = lampId;
		
		_url = _bridgeIp + "/api/" + _username + "/lights/" + _lampId + "/state";
		httpClient = HttpClients.createDefault();
	}
	
	
	public static void turnOn(QActor actor) {
		turn(true);
	}
	
	public static void turnOff(QActor actor) {
		turn(false);
	}
	
	private static void turn(Boolean state) {
		JSONObject lightState = new JSONObject();
		lightState.accumulate("on", state);
	
		HttpPut req = new HttpPut(_url);
		req.setEntity(new StringEntity(lightState.toString(), ContentType.APPLICATION_JSON));
				
		try {
			HttpEntity resp = httpClient.execute(req).getEntity();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
