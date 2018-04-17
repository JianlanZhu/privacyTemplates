function loadSmeNav() {
    loadHeader();
    var navBar = getElement('navSME');
    navBar.innerHTML =
        '<div id="navDiv">' +
        '<ul class="nav navbar-nav">\n' +
        '  <li id="smeHome"><a href="/socialMedia/smeHome" >' +
        '\<span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home - View Requests</a></li>\n' +
        '</ul>' +
        '</div>';
}