// Write JavaScript here 
function validate(){
  if(validateCaseDetails()) {
    if(validateBasicInfo()) {
      alert("successfully validated");
      return true;
    }
  }
}

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

function isStringEmpty(data) {
  if (data.value.trim() === "") {
    return true;
  }
  else {
    return false;
  }
}

function showAlert(orgitemId, errmsg) {
  itemId = orgitemId + "Error";
  item = document.getElementById(itemId);
  item.innerHTML = errmsg;
  $('#'+itemId).show();
}

function hideError(itemId) {
  if(itemId === undefined) {
    itemId = '.alert';
  }
  $(itemId).hide();
}
