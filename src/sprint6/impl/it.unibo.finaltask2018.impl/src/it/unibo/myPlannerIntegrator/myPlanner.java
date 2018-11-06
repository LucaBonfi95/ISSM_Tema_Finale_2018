package it.unibo.myPlannerIntegrator;

import java.util.Iterator;
import java.util.List;

import it.unibo.exploremap.program.aiutil;
import aima.core.agent.Action;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class myPlanner {

	public static List<Action> lastMove;
	
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
			List<Action> actions = aiutil.doPlan();
			lastMove = actions;
			if(actions == null) {				
				actor.addRule("move(n,n)");
			}
			else if(actions.size() == 1) {
				//move(X, Y)
				//X --> a is right
				//		d is left
				//		n is none
				//Y --> w is forward
				//		s is backward
				actor.addRule("move(n," + actions.get(0).toString() + ")");
			}
			else if(actions.size() == 2) {
				String m1 = actions.get(0).toString();
				String m2 = actions.get(1).toString();
				if(m1.equalsIgnoreCase("a") || m1.equalsIgnoreCase("d"))
					actor.addRule("move(" + m1 +"," + m2 + ")");
				//else
					//actor.addRule("move(" + m2 +"," + m1 + ")");
			}
			
			System.out.println("\n--------------------------\nThe next move is: " + actions);
			aiutil.showMap();
			System.out.println("\n--------------------------");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setMoveResult(QActor actor, String result) {
		try {
			if(result.equalsIgnoreCase("good"))
				executeMoves(lastMove);
			else if(result.equalsIgnoreCase("bad")) {

				if(lastMove.size() == 2)
					aiutil.doMove(lastMove.get(0).toString());
				
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
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected static void executeMoves(List<Action> actions) throws Exception {
		Iterator<Action> iter = actions.iterator();
		while( iter.hasNext() ) {
			aiutil.doMove(iter.next().toString());
		}
	}
	
}
