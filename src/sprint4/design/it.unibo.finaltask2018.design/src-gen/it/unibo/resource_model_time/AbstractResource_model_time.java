/* Generated by AN DISI Unibo */ 
package it.unibo.resource_model_time;
import it.unibo.qactors.PlanRepeat;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.StateExecMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.StateFun;
import java.util.Stack;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.ActorTimedAction;
public abstract class AbstractResource_model_time extends QActor { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	protected String planFilePath    = null;
	protected String terminationEvId = "default";
	protected String parg="";
	protected boolean bres=false;
	protected IActorAction action;
	 
	
		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}
		public AbstractResource_model_time(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/resource_model_time/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/resource_model_time/plans.txt";
	  	}
		@Override
		protected void doJob() throws Exception {
			String name  = getName().replace("_ctrl", "");
			mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
			initStateTable(); 
	 		initSensorSystem();
	 		history.push(stateTab.get( "init" ));
	  	 	autoSendStateExecMsg();
	  		//QActorContext.terminateQActorSystem(this);//todo
		} 	
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/    
	    //genAkkaMshHandleStructure
	    protected void initStateTable(){  	
	    	stateTab.put("handleToutBuiltIn",handleToutBuiltIn);
	    	stateTab.put("init",init);
	    	stateTab.put("waitForInputs",waitForInputs);
	    	stateTab.put("handleUpdateTime",handleUpdateTime);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "resource_model_time tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("init",-1);
	    	String myselfName = "init";  
	    	temporaryStr = "\"resource_model STARTED\"";
	    	println( temporaryStr );  
	    	parg = "consult(\"resourceModel.pl\")";
	    	//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    	solveGoal( parg ); //sept2017
	    	//switchTo waitForInputs
	        switchToPlanAsNextState(pr, myselfName, "resource_model_time_"+myselfName, 
	              "waitForInputs",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForInputs = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForInputs",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForInputs";  
	    	//bbb
	     msgTransition( pr,myselfName,"resource_model_time_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleUpdateTime") }, 
	          new String[]{"true","E","updateTime" },
	          1000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForInputs){  
	    	 println( getName() + " plan=waitForInputs WARNING:" + e_waitForInputs.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForInputs
	    
	    StateFun handleUpdateTime = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleUpdateTime",-1);
	    	String myselfName = "handleUpdateTime";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("updateTime(timer,STATE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("updateTime") && 
	    		pengine.unify(curT, Term.createTerm("updateTime(NAME,CURRENT_TIME)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="changeModelItem(timer,updateTime(STATE))";
	    			/* PHead */
	    			parg =  updateVars( Term.createTerm("updateTime(NAME,CURRENT_TIME)"), 
	    			                    Term.createTerm("updateTime(timer,STATE)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    				if( parg != null ) {
	    				    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    					if( aar.getInterrupted() ){
	    						curPlanInExec   = "handleUpdateTime";
	    						if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    						if( ! aar.getGoon() ) return ;
	    					} 			
	    					if( aar.getResult().equals("failure")){
	    						if( ! aar.getGoon() ) return ;
	    					}else if( ! aar.getGoon() ) return ;
	    				}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"resource_model_time_"+myselfName,false,true);
	    }catch(Exception e_handleUpdateTime){  
	    	 println( getName() + " plan=handleUpdateTime WARNING:" + e_handleUpdateTime.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleUpdateTime
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
