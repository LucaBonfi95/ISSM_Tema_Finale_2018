package it.unibo.temperature_sensor_adapter;

import it.unibo.qactors.akka.QActor;

public class webTemperatureSensorAdapter {
	
	public static void updateTemperature(QActor actor) {
		
		// This method is a mock
		// It is supposed to get the current temperature value from a website
		
		actor.emit("updateTemperature", "updateTemperature(temp, 16)");
		
	}
	
	
	
}
