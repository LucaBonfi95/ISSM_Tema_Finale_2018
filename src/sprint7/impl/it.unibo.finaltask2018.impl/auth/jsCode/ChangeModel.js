var resources	= require('../models/resources');

var server 		= require('../frontendServer');

module.exports.changeModel = function(event) {
	//event="msg(MSGID       , MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM)"
	//       msg(modelChanged, event  ,       , none    ,        ,       )        
	
	//if(event.match("^msg(modelChanged,*\sevent,*\s+.,*\snone,*\s+.)")) {
	if(event.startsWith("msg(modelChanged")) {
		var changedResource = parse(event);
		modifyResource(changedResource);
		
	}
}


function countOccurrence(target, char) {
	//return (target.match(new RegExp(char, "g")) || []).length;
    return target.split(char).length - 1;
}

function parse(event) {
    var payload = event;
	payload = payload.substring(payload.indexOf(",") + 1);
	payload = payload.substring(payload.indexOf(",") + 1);
	payload = payload.substring(payload.indexOf(",") + 1);
	payload = payload.substring(payload.indexOf(",") + 1, payload.lastIndexOf(",")).trim();
	
	var name = payload;
	name = name.substring(name.indexOf("(") + 1);
    name = name.substring(name.indexOf("(") + 1);
	name = name.substring(name.indexOf("(") + 1, name.indexOf(")"));

	var state = payload;
	state = state.substring(state.indexOf("(") + 1);
    state = state.substring(state.indexOf("(") + 1);
    state = state.substring(state.indexOf("(") + 1);
    state = state.substring(state.indexOf("(") + 1, state.lastIndexOf(")") + 1 + countOccurrence(state, "(") - countOccurrence(state, ")"));
    
    return {name: name, state: state};
}

function modifyResource(res) {
	switch(res.name) {
	case "robot":
		var values = res.state.split(",").map(myValueOf);
		resources.robot.movement = values[0];
		resources.robot.obstacleDetected = values[1];
		//send via socket.io "robot.movement#" + values[0]
		//send via socket.io "robot.obstacleDetected#" + values[1]
		server.updateClient("robot.movement#" + values[0]);
		server.updateClient("robot.obstacleDetected#" + values[1]);
		break;
	case "temp":
		var value = myValueOf(res.state);
		resources.temp.temperature = value;
		//send via socket.io "temp.temperature#" + value
		server.updateClient("temp.temperature#" + value);
		break;
	case "led":
		resources.led.state = res.state;
		//send via socket.io "led.state#" + res.state
		server.updateClient("led.state#" + res.state);
		break;
	case "sonar1":
		var values = res.state.split(",").map(myValueOf);
		resources[res.name].somethingDetected = values[0];
		resources[res.name].distance = values[1];
		//send via socket.io res.name + ".somethingDetected#" + values[0]
		//send via socket.io res.name + ".distance#" + values[1]
		server.updateClient(res.name + ".somethingDetected#" + values[0]);
		server.updateClient(res.name + ".distance#" + values[1]);
		break;
	case "sonar2":
		var values = res.state.split(",").map(myValueOf);
		resources[res.name].somethingDetected = values[0];
		resources[res.name].distance = values[1];
		//send via socket.io res.name + ".somethingDetected#" + values[0]
		//send via socket.io res.name + ".distance#" + values[1]
		server.updateClient(res.name + ".somethingDetected#" + values[0]);
		server.updateClient(res.name + ".distance#" + values[1]);
		break;
	}	
}

function myValueOf(el) {
	return el.substring(el.indexOf("(") + 1, el.indexOf(")"));
}