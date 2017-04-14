var express = require('express');
var app = express();
var fs = require('fs');
var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var bodyParser = require('body-parser');

app.use(bodyParser());
var path = require('path');

//app.use for css and js files
app.use(express.static(__dirname + '/public'));

//mongodb URI
var url = 'mongodb://localhost:27017/testPet';

//get data from mongoDB
app.get('/testPet', function(req, res){
	MongoClient.connect(url, function(err, db){
		assert.equal(null, err);
		console.log("Connected");
		var collection = db.collection('petData');
		var data = req.body;
		console.log(data);
		collection.find({name: 'sony'}, function(err, data){
			if(err) { res.send(err.message); }
			else{ res.send(data); }
		});
	});
});



//posting to mongoDB
app.post('/place', function(req, res){
	MongoClient.connect(url, function(err, db){
		assert.equal(null, err);
		console.log("Connected");
		var collection = db.collection('petData');
		var data = req.body;
		console.log(data);
		collection.insert(data);
		db.close;
	});
});

//get HTML files and set page
app.get('/', function (req, res){
	fs.readFile('html/index.html', function(err, text){
		res.setHeader('Context-Type', 'text/html');
		res.end(text);
	});
	return;
});

//server on ____ or localhost:3000
var server = app.listen(process.env.PORT || 3000, "localhost", function(){
	var host = server.address().address;
	var port = server.address().port;

	console.log('Example app listening at http://%s:%s', host, port);
});
