//This file provides all the functions used in privateMessage.html
//Skye, 04/15

function addRow() {
    // Find a <table> element with id="myMessageTable":
    var table = document.getElementById("myMessageTable");

// Create an empty <tr> element and add it to the 1st position of the table:
    var row = table.insertRow(1);
    var row2 = table.insertRow(2)

// Insert new cells (<td> elements) at the 1st position of the "new" <tr> element:
    var cell1 = row.insertCell(0);
    var cell2 = row2.insertCell(0)


// Add some text to the new cells:
    cell1.innerHTML = "NEW CELL1";
    cell2.innerHTML = "NEW CELL2";
}