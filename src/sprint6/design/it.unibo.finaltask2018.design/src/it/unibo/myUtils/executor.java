package it.unibo.myUtils;

import java.io.IOException;

import it.unibo.qactors.akka.QActor;

public class executor {
	
	public static void execBash(QActor actor, String file) {
		try {
			Runtime.getRuntime().exec(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
