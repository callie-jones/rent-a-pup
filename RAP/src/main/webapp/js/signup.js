
function addUser(email, pass, last, first, callback){
			var query = {};
			query['user'] = email;
			query['pass'] = pass;
			query['age'] = 21;
			query['first'] = first;
			query['last'] = last; 
           $.ajax({
				url:"/newrenter",
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



function signUp(){
			swal({
				title: "Sign Up For Free!",
				text: "First Name: <input id='firstName' type='text'>Last Name: <input id='lastName' type='text'>Email: <input id='email' type='text'>Password:<input id='password' type='password'><br><a href='#' style='color:#59C1F1' onclick='signUp();'>Dont Have An Account?</a><br>",
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
				var first = document.getElementById('firstName').value;
				var last = document.getElementById('lastName').value;
				//var age = document.getElementById('age').value;
                if(pass === "" || email === "" || first === "" || last === "") {
                    swal.showInputError("Please Enter all Fields");
                    console.log("1");
                }
                else {
					addUser(email, pass, first, last, function(data) {
						console.log(data);
						console.log(data.length);
				        if(data === "null"){
		                    console.log("3");
							setTimeout(function(){ swal({ title:"Sign-Up Error!", type: "error", timer: 1250, showConfirmButton: false }); }, 2500);
						}
						else {
							console.log("2");
							setTimeout(function(){ swal({ title:"Sign-Up Complete!", type: "success", timer: 1250, showConfirmButton: false }); }, 2000);
						}
					});
				}
			});
		}
