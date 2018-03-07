//All functions related to request form

//Function validates all the form elements
function validate(){
  if(validateCaseDetails()) {
    if(validateBasicInfo()) {
	  var form = document.getElementById("ReqForm");
	  var url = "/api/request/generate";
	  var inputData = generateFormData(form);
	  postData(url, inputData, showResponseFromServer);
      return true;
    }
  }
}

//Validates all elements in case details tab
function validateCaseDetails() {
  var element = "caseNumber";
  var caseId = document.getElementById(element);
  if (isStringEmpty(caseId)) {
    $('[href="#caseDet"]').tab('show');
    showAlert(element, "Case ID cannot be empty");
    return false;
  }
  return true;
}

//Validates all elements in basic info tab
function validateBasicInfo() {
  var element = "basicInfo";
  var baseInfo = document.getElementById(element);
  if(isStringEmpty(document.getElementById("suspectUserName")) &&
    isStringEmpty(document.getElementById("inputProfileLink"))){
    $('[href="#basicInfo"]').tab('show');
    showAlert(element, "Enter at lease one identifier");
    return false;
  }
  return true;
}

//Temporarily adding this function for prototype 1
// The function purpose will be modified later.
function showResponseFromServer(response){
	alert("Response from server: "+response);
}