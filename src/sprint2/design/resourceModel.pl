/*
===============================================================
resourceModel.pl
===============================================================
*/

/*
 * Resources are modelled like:
 * resource(name(NAME), STATE)
 */

resource(name(robot), state(movement(stopped), obstacleDetected(false))).
/* 
 * for the robot movement can be:
 * - stopped
 * - movingForward
 * - movingBackward
 * - turningLeft
 * - turningRight
 */
 
resource(name(temp), state(temperature(0))). 
resource(name(timer), state(currentTime(hours(0), minutes(0), seconds(0)))).

resource(name(led), state(off)).

resource(name(sonar1), state(somethingDetected(false), distance(0))).
resource(name(sonar2), state(somethingDetected(false), distance(0))).

resource(name(temperatureIsOk), state(false)).
resource(name(timeIsOk), state(false)).

getResource(NAME, STATE) :-
		resource(name(NAME), STATE).

changeModelItem(robot, movement(VALUE)) :-
		resource(name(robot), state(movement(_), obstacleDetected(X))),
		commonChangeModelItem(name(robot), state(movement(VALUE), obstacleDetected(X))).
	


changeModelItem(robot, obstacleDetected(VALUE)) :-
		resource(name(robot), state(movement(X), obstacleDetected(_))),
		commonChangeModelItem(name(robot), state(movement(X), obstacleDetected(VALUE))).


changeModelItem(NAME, turnLed(VALUE)) :-
 		commonChangeModelItem(name(NAME), state(VALUE)).
		
changeModelItem(temp, updateTemperature(VALUE)) :-
 		commonChangeModelItem(name(temp), state(temperature(VALUE))).
		
changeModelItem(timer, updateTime(currentTime(hours(HOURS), minutes(MINUTES), seconds(SECONDS)))) :-
 		commonChangeModelItem(name(timer), state(currentTime(hours(HOURS), minutes(MINUTES), seconds(SECONDS)))).
		
changeModelItem(temperatureIsOk, STATE) :- 		
		commonChangeModelItem(name(temperatureIsOk), state(STATE)).
 		

changeModelItem(timeIsOk, STATE) :-
 		commonChangeModelItem(name(timeIsOk), state(STATE)).
 		

commonChangeModelItem(NAME, STATE) :- 
		(
			retract(resource(NAME, _));
			true
		),
 		assert(resource(NAME, STATE)),
		!,
		emitevent(modelChanged, modelChanged(resource(NAME, STATE))),
		(
			changedModelAction(resource(NAME, STATE)) 	
		 	; true								
		).


eval( ge, X, X ) :- !. 
eval( ge, X, V ) :- eval( gt, X , V ) .


emitevent( EVID, EVCONTENT ) :- 
	actorobj( Actor ), 
	Actor <- emit( EVID, EVCONTENT ).


%%%  initialize
initResourceTheory :- output("initializing the initResourceTheory ...").
:- initialization(initResourceTheory).


