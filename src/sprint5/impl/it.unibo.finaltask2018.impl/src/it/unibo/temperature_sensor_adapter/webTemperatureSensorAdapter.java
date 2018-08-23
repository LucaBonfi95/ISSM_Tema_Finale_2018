package it.unibo.temperature_sensor_adapter;

import it.unibo.qactors.akka.QActor;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.core.OWM.Unit;
import net.aksingh.owmjapis.model.CurrentWeather;

public class webTemperatureSensorAdapter {
	
	static OWM tempSensor;
	
	// openweathermap.org
	// username: iss2018project
	// email: b6752122@nwytg.net
	// pwd: iss2018project
	
	/*
	 * In order to have the library working, add the following lines to the
	 * gradle of ctxRealRobotAdapter and then re-execute the file
	 * 
	 		// library for OpenWeatherMap API
 			compile 'net.aksingh:owm-japis:2.5.2.2'
 	 *
	 */
	static String key = "85588aaf51496e44a6aa559b253e734e";
	
	static int bolognaId = 3181928;
	static double oldTemp = 0; 
	
	static boolean needInt = true;
	
	public static void init(QActor actor) {
		tempSensor = new OWM(key);
		tempSensor.setUnit(Unit.METRIC);
	}
	
	public static void init(QActor actor, String intResult) {
		init(actor);
		if(intResult.equalsIgnoreCase("false"))
			needInt = false;
		if(intResult.equalsIgnoreCase("true"))
			needInt = true;
	}
	
	
	public static void updateTemperature(QActor actor) {
		
		// This method is a mock
		// It is supposed to get the current temperature value from a website
		
		//actor.emit("updateTemperature", "updateTemperature(temp, 16)");
		
		
		
		try {
//			CurrentWeather temp = tempSensor.currentWeatherByCoords(44.494281, 11.346690);
			CurrentWeather temp = tempSensor.currentWeatherByCityId(bolognaId);
			if(temp.hasMainData() && temp.getMainData().hasTemp()) {
				double curTemp = temp.getMainData().getTemp();
				//if(curTemp != oldTemp) {
					oldTemp = curTemp;
					if(needInt)
						actor.emit("updateTemperature", "updateTemperature(temp, " + Math.round(curTemp) + ")");
					else
						actor.emit("updateTemperature", "updateTemperature(temp, " + curTemp+ ")");
				//}
			}			
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		init(null, "true");
//		updateTemperature(null);
//		Thread.sleep(1000);
//		updateTemperature(null);
//	}
	
	
	
}
