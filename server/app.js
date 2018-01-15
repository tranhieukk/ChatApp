var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);
app.get("/",function(req,res){
	res.sendFile(__dirname + "/index.html");
});
var usernameArr = [];

var ketqua;

//connect database
var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "himochat"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Mysql was Connected!");
});

io.sockets.on('connection',function(socket){
		console.log("Co nguoi connect");


		//--Dang ky tai khoan
		socket.on('client_Dang_ky_thanhvien',function(data){
			data = JSON.parse(data);
		 	var query="SELECT  Count(ID) as SL    FROM users where Username='"+data.username+"'";
		  	console.log(query);
		    con.query(query, function (err, result, fields) {
			    if (err) throw err;
			    if(result[0].SL >0){
					ketqua=false;
					
				}
				else{
						
					var query="INSERT INTO `users`(`Username`, `Password`) VALUES ('"+data.username+"','"+data.password+"')";
					 console.log(query);
					var query2="INSERT INTO `profile`( `Username`) VALUES ('"+data.username+"')";
					 console.log(query2);
			  		con.query(query, function (err, result, fields) {
			  		if (err) throw err;
			  			con.query(query2, function (err, result, fields) {
			  			if (err) throw err;
			  			ketqua=true;
			 			console.log(ketqua);
						});
					
					socket.emit('ketquaDK',{noidung:ketqua});

					});
			  		};
			});
		});//--Dang ky tai khoan

		//Dang nhap
		socket.on('client-gui-username',function(data){
			data = JSON.parse(data);
		 	var query="SELECT ID  , Username  FROM users where Username='"+data.username+"' and "+"Password ='"+data.password+"'";
		  
		    con.query(query, function (err, result, fields) {
		   		 if (err) throw err;
		   		 if(result[0].ID != null){
					ketqua=true;
					socket.un = result[0].ID ;

				
					}
				  else
				 {
					ketqua=false;
				 }
				socket.emit('ketquaDN',{noidung:ketqua});

			});
		});//Dangnhap

		//Lay danh sach ban be
		socket.on('client_xin_thong_tin_friendlist',function(data){
			data = JSON.parse(data);
			var query="SELECT * FROM `profile` WHERE  profile.Username IN";
		        query+="( select"; 
		        query+="(SELECT friend_relationship.user as Friend from friend_relationship WHERE  friend_relationship.relation ='"+data.username+"')"; 
		 		query+="Union";
		 		query+="(SELECT friend_relationship.relation  as Friend from friend_relationship WHERE friend_relationship.user ='"+data.username+"') )";
			     con.query(query, function (err, result, fields) {
		   		 if (err) throw err;
		   		 console.log(result);	   		
		   		 socket.emit('server_return_friendlist', {danhsach: result});
				});
		});//Lay danh sach ban be

		//Lay du lieu khoi tao
		socket.on('client_xin_thong_tin_KhoiTao',function(data){
			data = JSON.parse(data);
			var query ="SELECT count(ID) as SoLuong FROM `messages` WHERE messages.to='"+data.username+"' and messages.status=0";
				
			     con.query(query, function (err, result, fields) {
		   		 if (err) throw err;
		   		 console.log(result);	   		
		   		 socket.emit('server_return_num_of_new_message', {Number: result});
				});
		});//Lay du lieu khoi tao

		//Lay du lieu tin nhan
		socket.on('client_xin_thong_tin_tin_nhan_voi_friend',function(data){
			data = JSON.parse(data);
			console.log(data.username);
			console.log(data.friend);

			var query ="SELECT * FROM `messages`  WHERE (messages.to='"+data.username+"' or messages.to='"+data.friend+"') and  (messages.from='"+data.username+"' or messages.from='"+data.friend+"') ";
				 console.log(query);
			     con.query(query, function (err, result, fields) {
		   		 if (err) throw err;
		   		 console.log(result);	   		
		   		 socket.emit('server_return_listmesage', {listmesage: result});
				});
		});//Lay du lieu tin nhan

		 //Lay du lieu tin nhan moi
		socket.on('client_xin_thong_tin_tin_nhan_voi_friend',function(data){
			data = JSON.parse(data);
			console.log(data.username);
			console.log(data.friend);
			var query ="SELECT * FROM `messages`  WHERE  (messages.to='"+data.username+"' or messages.to='"+data.friend+"') and  (messages.from='"+data.username+"' or messages.from='"+data.friend+"') ";
			con.query(query, function (err, result, fields) {
		   		 if (err) throw err;
		   		 socket.emit('server_return_listmesage', {listmesage: result});
		   		});
		});//Lay du lieu tin nhan moi





});

		
		
			

	

		

		
			

		
		 
		    
		    
		    
		    
		 	
		    //Tim kiem ban be

		    //Gui loi moi ket ban

		   
