function loadLEONav() {
    loadHeader();
    var navBar = getElement('navLEO');

    navBar.innerHTML =
        '<div id="navDiv">' +
        '<ul class="nav navbar-nav">\n' +
        '  <li id="leoHome"><a href="./leohome.mustache" >' +
        '\<span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home - View Requests</a></li>\n' +
        '  <li><a href="./RequestForm.mustache">' +
        '\<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Create Request</a></li>\n' +
        '</ul>' +
        '</div>';

    $('#leoHome').background = 'blue';
}