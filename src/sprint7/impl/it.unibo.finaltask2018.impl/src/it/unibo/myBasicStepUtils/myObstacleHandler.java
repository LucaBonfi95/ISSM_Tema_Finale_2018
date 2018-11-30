package it.unibo.myBasicStepUtils;

import it.unibo.qactors.akka.QActor;

public class myObstacleHandler {

	private static long start = 0;
	private static long delay = 0;
	
	public static void init(QActor actor) {	}
	
	public static void start(QActor actor) {
		start = System.currentTimeMillis();
		
//		in qactor:
//		javaRun it.unibo.myBasicStepUtils.myObstacleHandler.start();
		
	}
	
	private static void takeDelay() {
		delay = (System.currentTimeMillis() - start) % 300;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void stopObstacle(QActor actor) {
		takeDelay();
		if(delay <= 5)
			return;
		actor.addRule("resetBasicStep(" + delay + ")");
		
//		in qactor:
//		javaRun it.unibo.myBasicStepUtils.myObstacleHandler.stopObstacle();
//		[ ?? resetBasicStep(DELAY) ] {
//			emit robotMovement : robotMovement(movingBackward);
//			delay DELAY;
//			emit robotMovement : robotMovement(stopped)
//		}
		
	}
	
	public static void stopObstacleAndReset(QActor actor) {
		takeDelay();
		if(delay <= 5)
			return;
		actor.emit("robotMovement", "robotMovement(movingBackward)");
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actor.emit("robotMovement", "robotMovement(stopped)");
		
//		in qactor:
//		javaRun it.unibo.myBasicStepUtils.myObstacleHandler.stopObstacleAndReset();
		
	}
}
