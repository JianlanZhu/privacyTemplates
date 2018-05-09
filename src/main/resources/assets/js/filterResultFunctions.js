// This file contains all the functions used in by the FilterResult.html
//04/13/2018, Skye

//define the variables globally
//the iframe which shows the table of conversations
//var ifm = document.getElementById("myFrame");
//sender's input field
//var fromName = document.getElementById("sender");
//receiver's input field
//var toName = document.getElementById("receiver");
//date's input field
//var date = document.getElementById("date");
//keyword's input field
//var keyword = document.getElementById("keyword");

//adjust the height of iFrame
//not automatic by now
function changeHeight() {
    //ifm.height=document.documentElement.clientHeight + 200;
    ifm.height = document.documentElement.scrollHeight + 120;
}

//validation: 1) at list one of the filter is filed if the button is clicked
//2) pass the seperated validation
function validFilters(id) {
    // alert("beginning of function" + id);
    //get all the values in these fields
    var fromName = document.getElementById("participant");
    var sender = fromName.value;
    //if all the fields are empty, return a warning
    if (sender.length == 0 && receiver.length == 0 && day.length == 0 && key.length == 0) {
        alert("Enter at least one filter!");
        return false;
    }
    // console.log(id);
    window.location.href = "/request/" + id + "?participant=" + sender;


    //else check the validity of each field
    //validDate();
    //validText();
}

function filterMessagesBySender(requestId, conversationId) {
    var senderName = document.getElementById("senderName").value;
    window.location.href = "/request/" + requestId + "/conversations/" + conversationId + "?senderName=" + senderName;
}

//validation: the date should be 1) within the initial filter
///2) within the current data
function validDate() {
}

//validation: From and To should both start from a letter
function validText() {
    return false
}

