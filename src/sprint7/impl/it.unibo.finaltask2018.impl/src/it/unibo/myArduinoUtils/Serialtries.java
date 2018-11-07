package it.unibo.myArduinoUtils;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Serialtries {
	
	static SerialPort port;
	static String buffer = "";
	
	public static void main(String[] args) throws SerialPortException, InterruptedException {
		// for arduino  The default is 8 data bits, no parity, one stop bit. 
		String portName = "COM6";
		int baudrate = 115200;
		port = new SerialPort(portName);
		port.openPort();
		Thread.sleep(100);
		port.setParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		
		Thread.sleep(3000);
		
		port.writeBytes("w".getBytes());
		System.out.println("cmd send");
//		String data = port.readString();
//		System.out.println();
		
//		System.out.println(readLine());
		String data = readLine();
		System.out.println(data);
		
		while(true)
			System.out.println(readLine());

	}
	
	//It's blocking
	public static synchronized String readLine() throws SerialPortException {
		String result = "";
		while(true) {
			String s = port.readString();
			if (s != null)
				buffer += s;
			
			if(buffer.contains("\r\n")) {
				result = buffer.substring(0, buffer.indexOf("\r"));
				buffer = buffer.substring(buffer.indexOf("\r") + 2);
				return result;
			}
		}
		
	}

}
