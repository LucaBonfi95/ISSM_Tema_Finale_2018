package it.unibo.myPlannerIntegrator;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import it.unibo.exploremap.program.aiutil;
import it.unibo.qactors.akka.QActor;

public class myPlanner {

	public static List<Action> lastMove = new ArrayList<Action>();
	
	public static void init(QActor actor) {
		try {
			aiutil.initAI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void getMove(QActor actor) {
		try {
			if (lastMove.isEmpty())
				lastMove = aiutil.doPlan();
			if(lastMove == null) {				
				actor.addRule("move(n)");
			} else {
				// move(X) --> X: n = none, a = left, d = right, w = forward)
				actor.addRule("move(" + lastMove.get(0).toString() + ")");
				System.out.println("\n--------------------------\nThe next move is: " + lastMove);
				aiutil.showMap();
				System.out.println("\n--------------------------");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setMoveResult(QActor actor, String result) {
		try {
			if(result.equalsIgnoreCase("good")) {
				aiutil.doMove(lastMove.get(0).toString());
				lastMove.remove(0);
			}
			else if(result.equalsIgnoreCase("bad")) {
				switch (aiutil.initialState.getDirection()) {
				case RIGHT:
					aiutil.doMove("obstacleOnRight");
					break;
				case LEFT:
					aiutil.doMove("obstacleOnLeft");
					break;
				case UP:
					aiutil.doMove("obstacleOnUp");
					break;
				case DOWN:
					aiutil.doMove("obstacleOnDown");
					break;
				}
				lastMove.clear();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
