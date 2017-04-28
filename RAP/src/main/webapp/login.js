		var loggedin = false;
		function userAuth(email, pass, callback){
			var query = {};
			query['user'] = email;
			query['pass'] = pass;
           $.ajax({
				url:"/auth",
				type:'POST',
				data: JSON.stringify(query),
				dataType: 'text',
				success:  function(data) {
					callback(data);
				 },
				error: function(ts) {
					alert(ts.responseText);
				 }
				});
		}

        function loginPopUp(){
			swal({
				title: "Please Login",
				text: "Email: <input id='email' type='text'><br>Password:<input id='password' type='password'><br><a href='signup.html' style='color:#59C1F1' onclick='window.location.href='signup.html''>Dont Have An Account?</a><br>",
				html: true,
				showCancelButton:true,
				cancelButtonText: "Cancel",
				confirmButtonText: "Login",
				closeOnConfirm: false,
				showLoaderOnConfirm: true,
				allowOutsideClick: true,
				animation: true,
			},
			function(){
				var pass = document.getElementById('password').value;
				var email = document.getElementById('email').value;
                if(pass === "" || email === "") {
                    swal.showInputError("Please Enter Email and Password");
                    console.log("1");
                }
				userAuth(email, pass, function(data) {
					console.log(data);
					console.log(data.length);
		            if(data === "null"){
                        console.log("3");
						setTimeout(function(){ swal({ title:"Login Error!", type: "error", timer: 1250, showConfirmButton: false }); }, 2500);
					}
					else {
						console.log("2");
						loggedin = true;
						setTimeout(function(){ swal({ title:"Login Complete!", type: "success", timer: 1250, showConfirmButton: false }); }, 2000);
					}
				});
			});
		}
