window.onload = function () {
    // 共用API方法
    var callApi = function callApi(url, param) {
        return fetch(url, {
            method: 'post',
            body: JSON.stringify(param),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        });
    }

    // 共用驗證方法
    var validateAccount = function(account, password) {
        const reg = /^[A-Za-z0-9]{8}$/;
        if (!reg.test(account) || !reg.test(password)) {
            alert('帳號或密碼不符合8個英數字母的條件');
            return false;
        }
        return true;
    }

    // 按鈕
    var btnToLogin = document.getElementById('btnToLogin');
    var btnToCreateAcct = document.getElementById('btnToCreateAcct');
    var btnLogin = document.getElementById('btnLogin');
    var btnCreateAcct = document.getElementById('btnCreateAcct');

    // 頁面跳轉
    if (btnToLogin) {
        btnToLogin.addEventListener('click', function () {
            window.location.href = 'login';
        });
    }

    if (btnToCreateAcct) {
        btnToCreateAcct.addEventListener('click', function () {
            window.location.href = 'createAccount';
        });
    }

    // 登入
    if (btnLogin) {
        btnLogin.addEventListener('click', function () {
            const account = document.getElementById('account').value;
            const password = document.getElementById('password').value;

            if (!validateAccount(account, password)) return;

            const param = {
                Account: account,
                Password: password
            };
            login(param);
        });
    }

    // 建立帳戶
    if (btnCreateAcct) {
        btnCreateAcct.addEventListener('click', function () {
            const account = document.getElementById('account').value;
            const password = document.getElementById('password').value;

            if (!validateAccount(account, password)) return;

            const param = {
                Account: account,
                Password: password
            };
            createAccount(param);
        });
    }

    // API
    var login = function (param) {
        callApi('http://localhost:8080/csp/login', param).then(function (response) {
            if (response.returnCode !== '0000') {
                alert('登入失敗');
                return;
            }
            localStorage.setItem('userName', response.user);
            window.location.href = 'todoList';
        }).catch((error) => {
            console.error('Error:', error);
        });
    }

    var createAccount = function (param) {
        callApi('http://localhost:8080/csp/createAccount', param).then(function (response) {
            switch(response.returnCode) {
                case "0001":
                    alert('帳號或密碼不符合8個英數字母的條件');
                    break;
                case "0002":
                    alert('帳號重複');
                    break;
                case "9999":
                    alert('建立失敗');
                    break;
                default:
                    alert('帳號建立成功');
                    window.location.href = 'login';
            }
        }).catch((error) => {
            console.error('Error:', error);
        });
    }
}