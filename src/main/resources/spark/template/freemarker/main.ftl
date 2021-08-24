<!DOCTYPE html>
<html lang="en">
<head>
	<title>Recursive Find</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">


	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">


	<!--===============================================================================================-->
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<!--===============================================================================================-->
</head>

<style>
	mark {

		background-color: #ffd893;
		/*
		green:
		background-color: #b6ffb9;
		orange:
		background-color: #ffd893;
		yellow:
		background-color: #f7ff00;
		 */
		color: black;
	}
</style>



<body>

<!-- Actual search box -->
<div class = "search">
	<div class="input-group-lg form-group has-search input-mysize">
		<span class="fa fa-search fa-lg form-control-feedback"></span>
		<input id="search-box" name="searchInput" type="text" class="form-control" placeholder="Search">
	</div>
</div>


<!-- table stuff -->

<div class="limiter">
	<div class="container-table100">
		<div class="wrap-table100">
			<div class="table100 ver1 m-b-110">
				<div class="table100-head">
					<table>
						<thead>
						<tr class="row100 head">
							<th class="cell100 column1">Link</th>
							<th class="cell100 column2">Matches</th>
							<th class="cell100 column3">Peek</th>
						</tr>
						</thead>
					</table>
				</div>

				<div class="table100-body js-pscroll">
					<table id="data-table-body">
						<tbody>
						</tbody>
					</table>
				</div>
			</div>


		</div>
	</div>
</div>

<!--===============================================================================================-->
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="vendor/jquery/jquery-3.1.1.js"></script>


<!-- My script-->
<!-- 1:54 done after doing for like 20 mins-->
<!-- 2:52 highlight done from 2:32-->
<script type="text/javascript">
	const tableBody = document.querySelector("#data-table-body > tbody");

	//word to highlight, will make a get request for this
	let word = "${searchText}";
	//global, case insensitive
	let replace = new RegExp(word, "gi");

/*
	//makes get request
	function loadData () {
		const request = new XMLHttpRequest();
		request.open("get", "testdata.json");
		request.onload = () => {
			//parse this file as json
			try {
				const json = JSON.parse(request.responseText);
				populateData(json);
			} catch (e) {
				console.warn("Could not load the data");
			}
		};

		request.send();
	}
 */

	//formats the data into table
	function populateData (jsonArr, searchTerm) {

		//clear out table
		while (tableBody.firstChild) {
			tableBody.removeChild(tableBody.firstChild);
		}

		//TODO: check if json is empty and output a no results page

		//regular expression to be used for highlighting
		replace = new RegExp(searchTerm, "gi");

		//populate the table
		var rowIndex = 0
		jsonArr.forEach((row) => {
			const tr = document.createElement("tr");
			tr.className = "row100 body";

			for (i = 1; i < Object.keys(row).length + 1; i++) {
				const td = document.createElement("td");
				td.className = "cell100 column" + i;
				td.innerHTML = row[i - 1];
				//make links clickable
				if (i === 1) {
					td.innerHTML = "<a href=\"" + "\">" + row[i-1] + "</a>" +
					"<div class=\"box\">" +
     				"<iframe src=\"" +
						row[i-1] + "\"" + rowIndex +
						" width = \"800px\" height = \"500px\"></iframe>"
  				"</div> "
				}

				if (i === 2) {
					td.innerHTML = 1 + " / " + row[i-1]  + "<br>" +
					"<button " + "onclick=\"prevMatch(this, " +
					0 + ", " + rowIndex + ", " + replace + ","+row[i-1]+");\" " +
					"class=\"previous\"> < </button>" +
					"<button " + "onclick=\"nextMatch(this, " +
					0 + ", " + rowIndex + "," + replace + ","+row[i-1]+");\" " +
					"class=\"next\"> > </button>"
				}

				if (i === 3) {
					//highlight all instances of the word
					//td.innerHTML = row[i-1].replace(replace, "<mark>" + word + "</mark>");
					//keep correct case of the search term in the text, ie. "Labs"
					td.innerHTML = row[i-1].replace(replace, function (str) {return "<mark>" + str + "</mark>"});
				}

				tr.appendChild(td);

			}

			tableBody.appendChild(tr);
			rowIndex = rowIndex + 1;
		});

	}


	//get the previous match
	function prevMatch (button, matchIndex, rowIndex, replace, totalMatches) {
		const row = $(button).parents("tr");
		const col2 = $("td.cell100.column2")[rowIndex];
		const col3 = $("td.cell100.column3")[rowIndex];


		const postParameters = {matchIndex: matchIndex-1, rowIndex: rowIndex};
		$.post("/iterateresults", postParameters, function(out) {
			const result = JSON.parse(out);
			const newMatchIndex = result.newMatchIndex;
			const newPeekText = result.newPeekText;
			col2.innerHTML = newMatchIndex+1 + " / " + totalMatches  + "<br>" +
			"<button " + "onclick=\"prevMatch(this, " +
			newMatchIndex + ", " + rowIndex + ", " + replace + ","+totalMatches+");\" " +
			"class=\"previous\"> < </button>" +
			"<button " + "onclick=\"nextMatch(this, " +
			newMatchIndex + ", " + rowIndex + ","+replace+","+totalMatches+");\" " +
			"class=\"next\"> > </button>"
			col3.innerHTML = newPeekText.replace(replace, function (str) {return "<mark>" + str + "</mark>"});
		});

	}

	//get the next match
	function nextMatch (button, matchIndex, rowIndex, replace, totalMatches)  {
		const row = $(button).parents("tr");
		const col2 = $("td.cell100.column2")[rowIndex];
		const col3 = $("td.cell100.column3")[rowIndex];

		const postParameters = {matchIndex: matchIndex+1, rowIndex: rowIndex};
		$.post("/iterateresults", postParameters, function(out) {
			const result = JSON.parse(out);
			const newMatchIndex = result.newMatchIndex;
			const newPeekText = result.newPeekText;
			col2.innerHTML = newMatchIndex+1 + " / " + totalMatches  + "<br>" +
			"<button " + "onclick=\"prevMatch(this, " +
			newMatchIndex + ", " + rowIndex + ", " + replace + ","+totalMatches+");\" " +
			"class=\"previous\"> < </button>" +
			"<button " + "onclick=\"nextMatch(this, " +
			newMatchIndex + ", " + rowIndex + ", " + replace + ","+totalMatches+");\" " +
			"class=\"next\"> > </button>"
			col3.innerHTML = newPeekText.replace(replace, function (str) {return "<mark>" + str + "</mark>"});
		});
	}








	//highlight word in a block of text
	function highlight (word, content) {
		var replace = new RegExp(word,"g");
		content.replace(replace, "<mark>" + word + "</mark>")
	}

	//call populateData on page load
	document.addEventListener("DOMContentLoaded", () => { populateData(${results}, word); });

	//fill in word into search box
	document.getElementById('search-box').value = word;
	//auto-focus search
	document.getElementById('search-box').focus();


	//dynamic search
	$(document).ready(() => {
		const input = $("#search-box");
		console.log("input ready");

		input.keyup(event => {
			console.log("key up");
			//clear table
			while (tableBody.firstChild) {
				tableBody.removeChild(tableBody.firstChild);
			}

			const postParameters = {searchInput: input.val()};

			$.post("/dynamicresults", postParameters, function(out) {
				populateData(JSON.parse(out), input.val());
			});
		});
	});


	//TODO: make page for no term found


</script>


<!--===============================================================================================-->
<script src="vendor/bootstrap/js/popper.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<script>
	$('.js-pscroll').each(function(){
		var ps = new PerfectScrollbar(this);

		$(window).on('resize', function(){
			ps.update();
		})
	});


</script>
<!--===============================================================================================-->
<script src="js/main.js"></script>

</body>
</html>
