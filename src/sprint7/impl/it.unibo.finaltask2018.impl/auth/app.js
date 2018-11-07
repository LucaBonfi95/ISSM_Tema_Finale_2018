var express     	= require('express');
var path         	= require('path');
var favicon      	= require('serve-favicon');
var logger       	= require('morgan');	
var cookieParser 	= require('cookie-parser');
var bodyParser   	= require('body-parser');
var fs           	= require('fs');
var session         = require("express-session");
var flash			= require("connect-flash"); 
var mongoose		= require("mongoose");
var passport		= require("passport");


var serverWithSocket= require('./frontendServer');
var setUpPassport	= require("./setuppassport");
var routes			= require("./routes");


var mongodburl = "localhost:27017/users";

var app = express();

try{
	mongoose.connect("mongodb://" + mongodburl);
}catch( e ){
	console.log("SORRY mongoose ... " + e) ;
}

setUpPassport();

//app.set("port", process.env.PORT || 8080);

// view engine setup;
app.set('views', path.join(__dirname, 'views'));	 
app.set('view engine', 'ejs');

app.use(express.static(path.join(__dirname, 'jsCode')))

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

 

app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());


app.use(session({
	secret: "LUp$Dg?,I#i&owP3=9su+OB%`JgL4muLF5YJ~{;t",
	resave: true,
	saveUninitialized: true
}));


app.use(flash());
app.use(passport.initialize());
app.use(passport.session());

app.use(routes);





// catch 404 and forward to error handler;
app.use(function(req, res, next) {
	var err = new Error('Not Found');
	err.status = 404;
	next(err);
});

// error handler;
app.use(function(err, req, res, next) {
	// set locals, only providing error in development
	res.locals.message = err.message;
	res.locals.error = req.app.get('env') === 'development' ? err : {};

	// render the error page;
	res.status(err.status || 500);
	res.render('error');
});

module.exports = app;