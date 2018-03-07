//Global functions that will be used by all js files

//Returns true if data is an empty string, else false
function isStringEmpty(data) {
    if (data.value.trim() === "") {
        return true;
    }
    else {
        return false;
    }
}

//Shows the alert corresponding to element with id: orgitemId
//Appends error message to the alert
function showAlert(orgitemId, errmsg) {
    itemId = orgitemId + "Error";
    item = document.getElementById(itemId);
    item.innerHTML = errmsg;
    $('#'+itemId).show();
}

//Hides all errors.
//If specific error needs to be hidden then, the alert id must be passed
function hideError(itemId) {
    if(itemId === undefined) {
        itemId = '.alert';
    }
    $(itemId).hide();
}

//Generates all the form data and returns the JSON object
//The input is the form element
//Currently only text type data has been parsed.
//ToDo: Checkbox and multi-select must be parsed correctly
function generateFormData(form) {
  var formData = {};
  for(var i=0; i<form.elements.length; i++){
	  //Temporarily adding this until everything is setup in server!!!
	  if(i==3)
		  break;
	  var element = form.elements[i];
		if(element.type === "text" || element.type === "email" || element.type === "number") {
			formData[form.elements[i].id] = form.elements[i].value;
		}
  }
  return formData;
}

//Send Post request 
//url: the url which should be hit
//data: data to be posted
//onResponse: the function that must be executed when server responds
//Return the response text
function postData(url, data, onResponse) {
	var XHR = new XMLHttpRequest();
	XHR.open("POST",url);
	XHR.setRequestHeader("content-type", "application/json");
	XHR.onreadystatechange = function () {
               if(XHR.readyState == 4){
                   if(XHR.status == 200){
                       onResponse(XHR.responseText);
                   } else {
					   //ToDo: correct this
					   onResponse("error");
				   }
               }
           };
	XHR.send(JSON.stringify(data));
}
