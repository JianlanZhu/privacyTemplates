function login(){
    var uName = getElement("uname");
    var pwd = getElement("psw");
    var url = "/login";
    inputData = {};
    inputData.username = uName.value;
    inputData.password = pwd.value;
    postData(url, inputData, "application/json", showResponseFromServer);
}

//Temporarily adding this function for prototype 1
// The function purpose will be modified later.
function showResponseFromServer(response) {
    window.location.assign(response);
}