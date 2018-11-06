package it.unibo.pfrs;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class mbotConnTcp {
	private static String hostName= "localhost";
	private static int port = 8999;
	private static String sep=";";
 	protected static Socket clientSocket ;
	protected static PrintWriter outToServer;
	protected static Scanner inFromServer;
	
	private static QActor curActor;
	
	private static boolean turning = false;
	
	public mbotConnTcp() {
//		try {
//			initClientConn();
//		} catch (Exception e) {
// 			e.printStackTrace();
//		}
	}
	
	public static void initClientConn(QActor actor) throws Exception {
		 initClientConn(actor, hostName, port);
	}
	
	public static void initClientConn(QActor actor, String host, int port) throws Exception {
		clientSocket = new Socket(host, port);
		outToServer  = new PrintWriter(clientSocket.getOutputStream());
		inFromServer = new Scanner(clientSocket.getInputStream());
		
		curActor = actor;
		
		startReceivingDataFromServer();
	}

	public static void sendCmd(String msg, String duration) throws Exception {
		if( outToServer == null ) return;
		String jsonString = "{ 'type': '" + msg + "', 'arg': " + duration + " }";
		if(msg == "alarm")
			jsonString = "{ 'type': '" + msg + "' }";
		JSONObject jsonObject = new JSONObject(jsonString);
		msg = sep+jsonObject.toString()+sep;
		System.out.println("sending msg=" + msg);
		outToServer.println(msg);
		outToServer.flush();
	}

	protected void println(String msg) {
		System.out.println(msg);
	}

 	
	public static void mbotForward(QActor actor) {
 		try {  sendCmd("moveForward", "-1"); } catch (Exception e) {e.printStackTrace();}
 		turning = false;
	}
	public static void mbotBackward(QActor actor) {
		try { sendCmd("moveBackward", "-1"); } catch (Exception e) {e.printStackTrace();}
		turning = false;
	}
	public static void mbotLeft(QActor actor) {
		try { sendCmd("alarm", "-1"); sendCmd("turnLeft", "800"); } catch (Exception e) {e.printStackTrace();}
		turning = true;
	}
	public static void mbotRight(QActor actor) {
		try { sendCmd("alarm", "-1"); sendCmd("turnRight", "800"); } catch (Exception e) {e.printStackTrace();}
		turning = true;
	}
	public static void mbotStop(QActor actor) {
		try { sendCmd("alarm", "-1"); } catch (Exception e) {e.printStackTrace();}
		turning = false;
	}
  	
	
	protected static void startReceivingDataFromServer() {
		new Thread(() -> {
			try {
				if(curActor == null) return;
				curActor.println("mbotConnTcp startReceivingDataFromServer STARTS");
				inFromServer.useDelimiter(";+");
				while(inFromServer.hasNext()) {
					String eventStr = inFromServer.next();
					if(eventStr.trim().length() != 0) {
						if(eventStr.contains("webpage-ready"))
							eventStr = eventStr.replace("{}", "\"\"");	//not beautiful, but this is the only message that is not a well formed json string
						//curActor.println("string: " + eventStr);
						JSONObject event = new JSONObject(eventStr);
						//curActor.println("from json: " + event.toString());
						if(event.getString("type").equals("collision") && !turning) {
							//emit the event obstacleDetected : obstacleDetected(true)
							//System.out.println(curActor.getPrologEngine().getTheory().toString());
							QActorUtils.raiseEvent(curActor, "pfrs_mbot", "obstacleDetected", "obstacleDetected(true)");
							System.out.println("+++EVENTO COLLISION+++");
							mbotStop(curActor);
							//Added to avoid the state obstacleDetected remains
							Thread.sleep(200);
							QActorUtils.raiseEvent(curActor, "pfrs_mbot", "obstacleDetected", "obstacleDetected(false)");
						}
						else if(event.getString("type").equals("sonar-activated")) {
							//emit the event sonarDetected : sonarDetected(name(NAME), somethingDetected(true), distance(VALUE))
							QActorUtils.raiseEvent(curActor, "pfrs_mbot", "sonarDetected", "sonarDetected(name(" + event.getJSONObject("arg").getString("sonarName") + "), somethingDetected(true), distance(" + Math.abs(event.getJSONObject("arg").getInt("distance")) + "))");
						}
					}
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
			
		}).start();
	}
	
	
	
	
	//Just for testing
	public static void main(String[] args)   {
		try {
			initClientConn(null);
			System.out.println("STARTING ... ");
			mbotForward(null);
			Thread.sleep(1000);
			mbotBackward(null);
			Thread.sleep(1000);
			mbotLeft(null) ;
			Thread.sleep(1000);
			mbotForward(null);
			Thread.sleep(1000);
			mbotRight(null);
			Thread.sleep(1000);
			mbotForward(null);
			Thread.sleep(1000);
			mbotStop(null);
			System.out.println("END");
		} catch (Exception e) {
 			e.printStackTrace();
		}
}	
	
 }
