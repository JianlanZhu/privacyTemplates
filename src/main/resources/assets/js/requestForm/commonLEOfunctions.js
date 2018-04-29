//function that adds a navigation bar
function loadLEONav() {
    loadHeader();
    var navBar = getElement('navLEO');
    navBar.innerHTML =
        '<div id="navDiv">' +
        '<ul class="nav navbar-nav">\n' +
        '  <li id="leoHome"><a href="/leoHome" >' +
        '\<span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home - View Requests</a></li>\n' +
        '  <li><a href="/request/requestForm">' +
        '\<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Create Request</a></li>\n' +
        '</ul>' +
        '</div>';

    $('#leoHome').background = 'blue';
}

function closeCase() {
    postData(window.location.href+'?newStatus=CLOSED', null, "", closeSuccess, 'PUT', closeError);
}

function closeSuccess() {
    alert("Case has been successfully closed");
    window.location.assign('http://localhost:8080/leoHome')
}


function closeError() {
    alert("There was an error. The case could not be closed");
    window.location.assign('http://localhost:8080/leoHome')
}