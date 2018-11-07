package it.unibo.myArduinoUtils;

import jssc.SerialPort;
import jssc.SerialPortException;

public class ArduinoSerial {
	private SerialPort _port;
	private String _buffer = "";
	
	private String _portName;
	private int _baudRate;
	
	public ArduinoSerial(String portName, int baudRate) {
		_portName = portName;
		_baudRate = baudRate;
	}
	
	public ArduinoSerial(String portName, String baudRate) {
		_portName = portName;
		_baudRate = Integer.parseInt(baudRate);
	}
	
	public void init() throws SerialPortException {
		_port = new SerialPort(_portName);
		_port.openPort();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_port.setParams(_baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	}
	
	public void writeString(String s) throws SerialPortException { 
		if(!_port.isOpened())
			throw new SerialPortException(_portName, "writeString", "port is not open");
		_port.writeBytes(s.getBytes());
	}
	
	public void writeLine(String s) throws SerialPortException {
		writeString(s + "\r\n");
	}
	
	//It's blocking
	public synchronized String readLine() throws SerialPortException {
		String result = "";
		while(true) {
			String s = _port.readString();
			if (s != null)
				_buffer += s;
			
			if(_buffer.contains("\r\n")) {
				result = _buffer.substring(0, _buffer.indexOf("\r"));
				_buffer = _buffer.substring(_buffer.indexOf("\r") + 2);
				return result;
			}
		}
		
	}
	
}
