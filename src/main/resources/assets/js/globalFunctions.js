//Global functions that will be used by all js files
function logout(){
    var url = "/logout";
    postData(url, null, "", function (url){
        window.location.assign(url);
    });
}

//Function that adding a header
function loadHeader(showLogout) {
    if(showLogout === undefined)
        showLogout = true;
    var header = getElement('header');

    var str =
        '<div class="container-fluid">\n' +
        '  <div class="row">\n' +
        '    <div class="col-sm-2">\n' +
        '      <img src="/assets/images/PEPLogo.JPG" class="img-responsive" width="304" height="236">' +
        '    </div>' +
        '    <div class="col-sm-10">\n';
    if(showLogout) {
        str +=
            '      <div class="row">\n' +
            '       <button type="button" class="btn btn-info btn-space btn-sm pull-right" onclick=logout()>\n' +
            '         <span class="glyphicon glyphicon-log-out"></span> Log out\n' +
            '      </button>' +
            '      </div>';
    }
    str +=
        '      <div class="row">' +
        '      <h1 class="text-primary col-sm-offset-3">PEP for LESM</h1>' +
        '      </div>' +
        '    </div>' +
        '  </div>'
        '</div>';
    header.innerHTML = str;
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

//a function returns the component of the mustache by the given ID
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
        }
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
function postData(url, data, requestHeader, onResponse) {
    var XHR = new XMLHttpRequest();
    XHR.open("POST", url);
    if(requestHeader !== "")
        XHR.setRequestHeader("content-type", requestHeader);
    XHR.onreadystatechange = function () {
        if (XHR.readyState == 4) {
            if (XHR.status == 200 || XHR.status == 204) {
                onResponse(XHR.responseText);
            } else {
                //ToDo: correct this
                alert("some error occurred");
            }
        }
    };
    XHR.send(JSON.stringify(data));
}

