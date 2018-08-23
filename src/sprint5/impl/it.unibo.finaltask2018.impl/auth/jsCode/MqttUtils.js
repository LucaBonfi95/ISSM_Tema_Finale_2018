var mqtt   		= require('mqtt');
var resUtils	= require('./ChangeModel');

var topic = "qa2frontend";

var counter = 1;

var client = mqtt.connect('tcp://localhost:1883');
console.log('mqtt client has connected successfully');

client.on('connect', function () {
	client.subscribe(topic);
	console.log('mqtt client has subscribed successfully');
});


client.on('message', function (topic, message){
	console.log("mqtt RECEIVES: "+ message.toString());
	resUtils.changeModel(message.toString());
});

function publish(msg){
	client.publish(topic, msg);
}

function emitEvent(name, payload) {
	publish("msg(" + name + ",event,frontend,none," + payload + "," + (counter++) + ")");
}

module.exports.emitEvent = emitEvent;


//setTimeout(function() { console.log("ending...") }, 60000);

//setTimeout(function() { emitEvent("start", "start(user)") }, 5000);