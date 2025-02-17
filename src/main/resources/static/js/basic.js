let host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();
    if (auth === '') {
        $('#login-text').show();
        $('#signup-text').show();
    } else {
        $('#logout-text').show();
        $('#mypage-text').show();
        $('.postbox').show();
    }
})

function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + "/page/user/login";
}

function getToken() {
    let auth = Cookies.get('Authorization');
    if (auth === undefined || auth == null) {
        return '';
    }
    return auth;
}