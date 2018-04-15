//All functions related to request form

//Global variable pertaining to reuestForm
var caseNo = "caseNumber";
var suspectUsrName = "suspectUserName";
var profileLink = "inputProfileLink";
var fName = "inputFirstName";
var lName = "inputLastName";
var emailID = "inputEmailAddress";
var phNo = "inputPhoneNo";

//Function validates all the form elements
function validate(){
  if(validateCaseDetails()) {
    if(validateBasicInfo()) {
	  var form = getElement("RegForm");
	  var url = "/request/requestForm";
	  var inputData = generateFormData(form);
	  postData(url, inputData, showResponseFromServer);
      return true;
    }
  }
}

//Validates all elements in case details tab
function validateCaseDetails() {
  var element = caseNo;
  var caseId = getElement(element);
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
  var baseInfo = getElement(element);
  if(isStringEmpty(getElement(suspectUsrName)) &&
    isStringEmpty(getElement(profileLink)) &&
      (isStringEmpty(getElement(fName)) || isStringEmpty(getElement(lName))) &&
      isStringEmpty(getElement(emailID)) &&
      isStringEmpty(getElement(phNo))
  ) {
    $('[href="#basicInfo"]').tab('show');
    showAlert(element, "Enter at lease one identifier");
    return false;
  }

    var startDate = $("#dtPickerStartDate").datepicker('getFormattedDate');
    var endDate = $("#dtPickerEndDate").datepicker('getFormattedDate');
    if (startDate == "" || endDate == "") {
        $('[href="#basicInfo"]').tab('show');
        showAlert("basicTime", "Enter start time and end time");
        return false;
    }
    return true;
}


//Temporarily adding this function for prototype 1
// The function purpose will be modified later.
function showResponseFromServer(response){
	alert("Response from server: "+response);
}