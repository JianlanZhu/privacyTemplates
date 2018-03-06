//Function validates all the form elements
function validate(){
  if(validateCaseDetails()) {
    if(validateBasicInfo()) {
      alert("successfully validated");
      return true;
    }
  }
}

//Validates all elements in case details tab
function validateCaseDetails() {
  var element = "inputCaseID";
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
  if(isStringEmpty(document.getElementById("inputUsrName")) &&
    isStringEmpty(document.getElementById("inputProfileLink"))){
    $('[href="#basicInfo"]').tab('show');
    showAlert(element, "Enter at lease one identifier");
    return false;
  }
  return true;
}