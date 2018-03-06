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
//If sepecific error needs to be hidden then, the alert id must be passed
function hideError(itemId) {
    if(itemId === undefined) {
        itemId = '.alert';
    }
    $(itemId).hide();
}
