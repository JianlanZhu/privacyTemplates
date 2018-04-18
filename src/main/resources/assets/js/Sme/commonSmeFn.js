//function that adds a navigation bar
//the bar contains: 1) the portal header 2) the SME header
function loadSmeNav() {
    loadHeader();
    var navBar = getElement('navSME');
    navBar.innerHTML =
        '<div id="navDiv">' +
        '<ul class="nav navbar-nav">\n' +
        '  <li id="smeHome"><a href="/smeHome" >' +
        '\<span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home - View Requests</a></li>\n' +
        '</ul>' +
        '</div>';
}