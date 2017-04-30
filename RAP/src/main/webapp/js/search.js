function searchAuth(searchType, searchStart, searchEnd, dog, callback){
			var query = {};
			console.log(searchStart);
			console.log(searchEnd);
			query['qType'] = searchType;
			query['start'] = searchStart;
			query['end'] = searchEnd;
			query['dog'] = dog;
			console.log(query['start']);
			console.log(query['dog']);
           $.ajax({
				url:"/search",
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

        function search(){
        		console.log("d");
        		var searchType = "byTime";
				var searchStart = Date.parse(document.getElementById('searchStart').value).toString("yyyy-MM-dd h:mm");
				var searchEnd = Date.parse(document.getElementById('searchEnd').value).toString("yyyy-MM-dd h:mm");
				var dog = document.getElementById('dog').value;
				console.log(dog);
				searchAuth(searchType, searchStart, searchEnd, dog, function(data) {
					console.log(data);
					console.log(data.length);
		            if(data === "null"){
						setTimeout(function(){ swal({ title:"Search Error!", type: "error", timer: 1250, showConfirmButton: false }); }, 2500);
					}
					else {
						//console.log(data);
						setTimeout(function(){ swal({ title:"Search Complete!", type: "success", timer: 1250, showConfirmButton: false }); }, 2000);
						$("#search-results").append(data);
					}
				});
		}
