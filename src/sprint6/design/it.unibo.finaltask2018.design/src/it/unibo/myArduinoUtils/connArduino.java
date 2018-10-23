package it.unibo.myArduinoUtils;

import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;
import jssc.SerialPortException;

public class connArduino {

	private static QActor curActor;
	private static ArduinoSerial serial;
	
	public static void initRasp(QActor actor, String rate)   {
		curActor = actor;
		init("/dev/ttyUSB0", rate);
	}
	public static void initPc(QActor actor, String port, String rate)   {
		curActor = actor;
		init(port, rate);
	}
	
	
	private static void init(String port, String rate) {
		serial = new ArduinoSerial(port, rate);
		try {
			serial.init();
		} catch (SerialPortException e) { myprintln(e.getMessage()); }
		startObserverDataFromArduino();
	}
	
	private static void startObserverDataFromArduino() {
		new Thread(() -> {
			try {
				myprintln("connArduino startObserverDataFromArduino STARTED"  );
				while(true) {
					try {
						String dataArduino = serial.readLine();						
						
						if(dataArduino != null && !dataArduino.trim().equalsIgnoreCase("") && dataArduino.startsWith("msg")) {
							myprintln("connArduino received:" + dataArduino + " -> " + dataArduino.split("-")[1] + " - " + dataArduino.split("-")[2]);
							QActorUtils.raiseEvent(curActor, "real_mbot", dataArduino.split("-")[1], dataArduino.split("-")[2]);
						}
					} catch (Exception e) { myprintln("connArduino ERROR:" + e.getMessage()); }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		).start();
	}
	
	private static void myprintln(String s) {
		if(curActor == null)
			System.out.println(s);
		else
			curActor.println(s);
	}
	
	public static void sendToArduino(QActor actor, String cmd) {
		try {
			serial.writeString(cmd);
		} catch (SerialPortException e) { myprintln(e.getMessage()); }
	}
	
	public static void mbotForward(QActor actor) {
 		sendToArduino(actor, "w");
	}
	public static void mbotBackward(QActor actor) {
		sendToArduino(actor, "s");
	}
	public static void mbotLeft(QActor actor) {
		sendToArduino(actor, "a");
	}
	public static void mbotRight( QActor actor ) {
		sendToArduino(actor, "d");
	}
	public static void mbotStop(QActor actor) {
		sendToArduino(actor, "h");
	}
	public static void mbotLinefollow(QActor actor) {
		sendToArduino(actor, "f");
	}
	
	public static void main(String[] args) {
		initPc(null, "COM6", "115200");
		
		try {
			Thread.sleep(3000);
			mbotForward(null);
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
