		var booked = false;
		function bookAuth(user, billing, start, end, dog, callback){
			var query = {};
			query['qType'] = "addBooking";
			query['start'] = start;
			query['end'] = end;
			query['dog'] = dog;
			query['user'] = user;
			//query['bill'] = billing;
			console.log(query['start']);
			console.log(query['dog']);
           $.ajax({
				url:"/book",
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

        function book(){
				var user = document.getElementById('user').value;
				var billing = "bill"; //document.getElementById('billing').value;
				//var date = document.getElementById('date').value;
				var start = document.getElementById('start').value;
				var end = document.getElementById('end').value;
				var dog = document.getElementById('dog').value;
				bookAuth(user, billing, start, end, dog, function(data) {
					console.log(data);
					console.log(data.length);
		            if(data === "null"){
                        console.log("3");
						setTimeout(function(){ swal({ title:"Booking Error!", type: "error", timer: 1250, showConfirmButton: false }); }, 2500);
					}
					else {
						console.log("2");
						booked = true;
						setTimeout(function(){ swal({ title:"Booking Complete!", type: "success", timer: 1250, showConfirmButton: false }); }, 2000);
					}
				});
		}
