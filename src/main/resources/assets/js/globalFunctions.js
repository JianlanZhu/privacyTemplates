//Global functions that will be used by all js files

function loadHeader(getFolderLevelToAssets) {
    var header = getElement('header');
    header.innerHTML =
        '<div class="row" style="height:10px;"></div>' +
        '<div class="container-fluid">\n' +
        '  <div class="row">\n' +
        '    <div class="col-sm-2">\n' +
        '      <img src="/assets/images/PEPLogo.JPG" class="img-responsive" width="304" height="236">' +
        '    </div>' +
        '    <div class="col-sm-8">\n' +
        '      <h1 class="text-primary text-center">PEP for LESM</h1>' +
        '    </div>' +
        '  </div>' +
        '  <div class="row" style="height:50px;>\n' +
        '    </div>' +
        '</div>' +
        '<div class="col-xs-12" style="height:40px;"></div>';
}

//Returns true if data is an empty string, else false
function isStringEmpty(data) {
    if (data.value.trim() === "") {
        return true;
    }
    else {
        return false;
    }
}

function getElement(elementId) {
    return document.getElementById(elementId);
}

//Shows the alert corresponding to element with id: orgitemId
//Appends error message to the alert
function showAlert(orgitemId, errmsg) {
    itemId = orgitemId + "Error";
    item = document.getElementById(itemId);
    item.innerHTML = errmsg;
    $('#' + itemId).show();
}

//Hides all errors.
//If specific error needs to be hidden then, the alert id must be passed
function hideError(itemId) {
    if (itemId === undefined) {
        itemId = '.alert';
    }
    $(itemId).hide();
}

//Generates all the form data and returns the JSON object
//The input is the form element
//Currently only text type data has been parsed.
function generateFormData(form) {
    var formData = {};
    try{
    for (var i = 0; i < form.elements.length; i++) {
        //Temporarily adding this until everything is setup in server!!!

        var element = form.elements[i];
        if (element.type === "text" || element.type === "email" || element.type === "number" || element.type == "select-one") {
            //alert(form.elements[i].id + " " + form.elements[i].value);
            formData[element.id] = element.value;
        }else if (element.type === "checkbox") {
            formData[element.id] = element.checked;
        }
        else if (element.type === "select-multiple") {
            var value = element.chosen;
            if (value === undefined)
                value = "";
            formData[element.id] = value;
        }
        else {
            alert(element.type);
        }
        }
        alert(JSON.stringify(formData));
    }
    catch(e){
        alert(e);
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
    XHR.open("POST", url);
    XHR.setRequestHeader("content-type", "multipart/form-data");
    XHR.onreadystatechange = function () {
        if (XHR.readyState == 4) {
            if (XHR.status == 200) {
                onResponse(XHR.responseText);
            } else {
                //ToDo: correct this
                onResponse("error");
            }
        }
    };
    XHR.send(JSON.stringify(data));
}