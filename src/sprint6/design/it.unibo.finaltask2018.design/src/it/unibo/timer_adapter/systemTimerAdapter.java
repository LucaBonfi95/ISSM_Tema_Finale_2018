package it.unibo.timer_adapter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import it.unibo.qactors.akka.QActor;

public class systemTimerAdapter {

	public static void updateTime(QActor actor) {
		
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.systemDefault());
		int hours = zdt.getHour();
		int minutes = zdt.getMinute();
		int seconds = zdt.getSecond();
		
		actor.emit("updateTime", "updateTime(timer, currentTime("+hours+", "+minutes+", "+seconds+"))");
		
	}
	
}
