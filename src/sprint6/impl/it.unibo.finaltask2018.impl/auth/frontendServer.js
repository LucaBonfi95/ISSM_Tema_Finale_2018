
var appl		= require('./app'); 
var http		= require('http');
var io;

var qaUtils		= require('./jsCode/TcpClientToQaNode');
var mqttUtils	= require('./jsCode/MqttUtils');

var serverPort = 8080;

var createServer = function (port) {
	console.log("process.env.PORT=" + process.env.PORT + " port=" + port);
	if (process.env.PORT)
		port = process.env.PORT;
	else if (port === undefined)
		port = resourceModel.customFields.port;
  
  
	server = http.createServer(appl);   
	io = require('socket.io').listen(server); 
	 
	server.on('listening', onListening);
	server.on('error', onError);
	server.listen(port);
  
 
	io.on('connection', function(socket) {
		socket.on('message', receivedMessage);
	});
  
};
 
function receivedMessage(m) {
	if(m.startsWith("msg")) {
		var res = m.split("#");
		console.log("Event name: " + res[1] + " - Event payload: " + res[2]);
		qaUtils.emitEvent(res[1], res[2]);
	}
}


function onListening() {
	var addr = server.address();
	var bind = typeof addr === 'string'
		? 'pipe ' + addr
	    : 'port ' + addr.port;
	console.log('Listening on ' + bind);
}

function onError(error) {
	if (error.syscall !== 'listen')
		throw error;
	var bind = typeof port === 'string'
		? 'Pipe ' + port
		: 'Port ' + port;
		// handle specific listen errors with friendly messages;
		switch (error.code) {
			case 'EACCES':
				console.error(bind + ' requires elevated privileges');
				process.exit(1);
				break;
		    case 'EADDRINUSE':
		    	console.error(bind + ' is already in use');
		    	process.exit(1);
		    	break;
		    default:
		    	throw error;
		}
}

//Handle CRTL-C;
process.on('SIGINT', function () {
	console.log('frontendServer Bye, bye!');
	process.exit();
});

process.on('exit', function(code){
	console.log("Exiting code= " + code );
});
process.on('uncaughtException', function (err) {
 	console.error('mqtt got uncaught exception:', err.message);
  	process.exit(1);		//MANDATORY!!!;
});

module.exports.updateClient = function (msg) { 
    console.log(msg);
    io.sockets.send(msg);
};

createServer(serverPort);
