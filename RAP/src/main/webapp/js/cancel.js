

		function cancelAuth(bookId, callback){
			var query = {};
			query['qType'] = "cancelBooking";
			query['bookId'] = bookId;
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

        function cancel(){
				var bookId = document.getElementById('bookId').value;
				cancelAuth(bookId, function(data) {
					console.log(data);
					console.log(data.length);
		            if(data === "null"){
						setTimeout(function(){ swal({ title:"Booking Cancelling Error!", type: "error", timer: 1250, showConfirmButton: false }); }, 2500);
					}
					else {
						setTimeout(function(){ swal({ title:"Booking Cancelling Complete!", type: "success", timer: 1250, showConfirmButton: false }); }, 2000);
					}
				});
		}
