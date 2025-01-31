window.onload = function () {
    // 共用API方法，請勿更動
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

    // 檢查使用者是否已登入
    var accountLogin = localStorage.getItem('userName');
    if (!accountLogin) {
        // 若使用者尚未登入，顯示 alert 並跳轉至 login.html
        alert('尚未登入，請先登入。');
        window.location.href = 'login';
        return;
    }

    console.log(localStorage.getItem('userName'));

    userName.innerText = `Hi, ${accountLogin}`;

    var createTodoItem = function (todoItem, displayId) {
        // console.log(todoItem);

        let todoItemElement = document.createElement('div');
        todoItemElement.classList.add('todoItem');

        let table = document.createElement('table');
        table.classList.add('table');

        let thead = document.createElement('thead');
        let tr = document.createElement('tr');
        let taskId = document.createElement('th');
        // taskId.textContent = `[${todoItem.taskId}]`;
        taskId.textContent = `[${displayId.toString().padStart(4, '0')}]`;
        let taskStatus = document.createElement('th');
        taskStatus.textContent = todoItem.taskStatus;
        let updateTime = document.createElement('th');
        updateTime.textContent = todoItem.updateTime.replace('T', ' ');
        let replyCount = document.createElement('th');
        replyCount.textContent = `共計 ${todoItem.replyCount} 則筆記`;

        let taskIdAndReplyCount = document.createElement('th');
        taskIdAndReplyCount.innerHTML = `[${displayId.toString().padStart(4, '0')}] <span style="margin-left: 10px;">共計 ${todoItem.replyCount} 則筆記</span>`;


        let dropdownTh = document.createElement('th');
        let dropdown = document.createElement('div');
        dropdown.classList.add('dropdown');

        let btnDropdown = document.createElement('button');
        btnDropdown.classList.add('btn', 'btn-warning', 'dropdown-toggle');
        btnDropdown.setAttribute('type', 'button');
        btnDropdown.setAttribute('id', `${todoItem.taskId}dropdownMenuButton`);
        btnDropdown.setAttribute('data-bs-toggle', 'dropdown');
        btnDropdown.setAttribute('aria-expanded', 'false');
        btnDropdown.innerText = '...';

        let dropdownMenu = document.createElement('ul');
        dropdownMenu.classList.add('dropdown-menu');
        dropdownMenu.setAttribute('aria-labelledby', `${todoItem.taskId}dropdownMenuButton`);

        let editLi = createDropItem('修改內容', todoItem, true);

        // 檢查任務狀態 - 如果taskStatus是"Done"，已關閉任務，所以「修改內容」按鈕不能按
        if (todoItem.taskStatus === 'Done') {
            editLi.firstChild.disabled = true;
        } else {
            editLi.addEventListener('click', () => {
                // setModalContent('修改任務內容', `任務序號：${todoItem.taskId}`, todoItem.taskContent, '修改');
                setModalContent('修改任務內容', `任務序號：${displayId}`, todoItem.taskContent, '修改');
                btnCu.onclick = function () {
                    if (!modalTskContent.value) {
                        alert('請輸入內容');
                        return;
                    }

                    const param = {
                        taskId: todoItem.taskId,
                        taskContent: modalTskContent.value
                    }
                    editTodoItem(param);
                }
            });
        }

        let addLi = createDropItem('新增筆記', todoItem, true);

        // 檢查任務狀態 - 如果taskStatus是"Done"，已關閉任務，所以「新增筆記」按鈕不能按
        if (todoItem.taskStatus === 'Done') {
            addLi.firstChild.disabled = true;
        } else {
            addLi.addEventListener('click', () => {
                setModalContent('新增筆記', `任務序號：${displayId}`, '', '新增');
                btnCu.onclick = function () {

                    if (!modalTskContent.value) {
                        alert('請輸入內容');
                        return;
                    }

                    // const param = {
                    //    taskId: todoItem.taskId,
                    //    taskContent: modalTskContent.value
                    // }

                    // 修改 param 中的 key 為 replyContent
                    const param = {
                        taskId: todoItem.taskId,
                        replyContent: modalTskContent.value  // 新增筆記 reply content
                    };

                    addReply(param);
                }
            });
        }

        let todoListArea = document.getElementById('todoListArea');

        todoListArea.appendChild(todoItemElement);
        todoItemElement.appendChild(table);
        table.appendChild(thead);
        thead.appendChild(tr);
        // tr.appendChild(taskId);
        // tr.appendChild(replyCount);
        tr.appendChild(taskIdAndReplyCount);
        tr.appendChild(taskStatus);
        tr.appendChild(updateTime);
        tr.appendChild(dropdownTh);
        dropdownTh.appendChild(dropdown);
        dropdown.appendChild(btnDropdown);
        dropdown.append(dropdownMenu);
        dropdownMenu.appendChild(editLi);
        dropdownMenu.appendChild(addLi);

        let statusLi = createDropItem(todoItem.taskStatus === 'Done' ? '重新開啟' : '完成任務', todoItem, false);
        let newTaskStatus = todoItem.taskStatus === 'Done' ? 'Todo' : 'Done';
        dropdownMenu.appendChild(statusLi);
        statusLi.addEventListener('click', () => {
            const param = {
                taskId: todoItem.taskId,
                taskStatus: newTaskStatus
            }
            editTodoItemStatus(param);
        });

        let tbody = document.createElement('tbody');

        let trTxtContent = document.createElement('tr');
        let tdTxtContent = document.createElement('td');
        tdTxtContent.colSpan = '5';

        let h5 = document.createElement('h5');
        h5.innerHTML = `<b>${todoItem.taskContent}</b>`;
        tdTxtContent.appendChild(h5);

        trTxtContent.appendChild(tdTxtContent);
        tbody.appendChild(trTxtContent);

        // 修改 Reply 呈現方式
        todoItem.reply.forEach(element => {
            let tr = document.createElement('tr');
            tr.style.backgroundColor = '#f8f9fa';
            
            // col 1: Reply內容（粗體）
            let tdContent = document.createElement('td');
            tdContent.style.width = '45%';
            tdContent.innerHTML = `<b>${element.replyContent}</b>`;

            // col 2: 空白欄位
            let tdEmpty = document.createElement('td');
            
            // col 3: 時間（斜體）
            let tdTime = document.createElement('td');
            tdTime.innerHTML = `<i>${element.updateTime}</i>`;
            tdTime.style.color = '#666';
            
            // col 4: 刪除按鈕
            let tdAction = document.createElement('td');
            let deleteBtn = document.createElement('button');
            deleteBtn.className = 'btn btn-sm btn-outline-danger';
            deleteBtn.innerText = '刪除';
            deleteBtn.disabled = true;
            tdAction.appendChild(deleteBtn);
            
            tr.appendChild(tdContent);
            tr.appendChild(tdEmpty);
            tr.appendChild(tdTime);
            tr.appendChild(tdAction);
            tbody.appendChild(tr);
        });

        table.appendChild(tbody);

        if (todoItem.taskStatus === 'Done') {
            thead.style.backgroundColor = '#e1ebf2';
            thead.style.color = 'black';
            tbody.style.color = '#d0d5d9';
        }
    }

    var createDropItem = function (btnText, todoItem, needModal) {
        let item = document.createElement('li');
        let btnReopen = document.createElement('button');
        btnReopen.classList.add('dropdown-item');

        if (needModal) {
            btnReopen.setAttribute('data-bs-toggle', 'modal');
            btnReopen.setAttribute('data-bs-target', '#cuModal');
        }

        btnReopen.setAttribute('type', 'button')
        btnReopen.textContent = btnText;
        item.appendChild(btnReopen);
        return item;
    }

    function setModalContent(modalLabelText, taskIdLabel, taskContent = "", buttonText) {
        cuModalLabel.innerText = modalLabelText;
        modalTskId.innerText = taskIdLabel;
        modalTskContent.value = taskContent;
        btnCu.innerText = buttonText;

        // Add length validation message
        let validationMsg = document.createElement('small');
        validationMsg.className = 'text-muted';
        validationMsg.innerHTML = `（最多${MAX_INPUT_LENGTH}字）`;
        modalTskId.appendChild(validationMsg);
    }

    var addTodoItem = function (param) {
        callApi('http://localhost:8080/csp/addTodoItem', param).then(function (response) {
            if (response.returnCode !== '0000') {
                alert('新增失敗');
                return;
            }

            todoListArea.innerText = '';
            tskConInput.value = '';
            queryAll();
        }).catch((error) => {
            console.error('Error:', error);
        });
    }

    var queryAll = function () {
        const param = {
            Account: accountLogin
        };

        // 清空 todoListArea，避免顯示靜態範本內容
        document.getElementById('todoListArea').innerHTML = '';

        callApi('http://localhost:8080/csp/queryAll', param).then(function (response) {
            if (response.returnCode !== '0000') {
                alert('查詢失敗');
                return;
            }

            // 按照順序顯示task id，而非使用task table 當中的id
            let displayId = response.task.length;

            response.task.forEach((element, index) => {
                createTodoItem(element, displayId - index);
            });
        }).catch((error) => {
            console.error('Error:', error);
        });
    }

    var editTodoItem = function (param) {
        callApi('http://localhost:8080/csp/editTodoItem', param).then(function (response) {
            if (response.returnCode !== '0000') {
                alert('修改失敗');
                return;
            }

            closeModal();
            todoListArea.innerText = '';
            queryAll();
        }).catch((error) => {
            console.error('Error:', error);
        });
    }

    var addReply = function (param) {
        callApi('http://localhost:8080/csp/addReply', param).then(function (response) {
            if (response.returnCode !== '0000') {
                alert('新增失敗');
                return;
            }

            closeModal();
            todoListArea.innerText = '';
            queryAll();
        }).catch((error) => {
            console.error('Error:', error);
        });
    }

    var editTodoItemStatus = function (param) {
        callApi('http://localhost:8080/csp/editTodoItemStatus', param).then(function (response) {
            if (response.returnCode !== '0000') {
                alert('修改失敗');
                return;
            }

            todoListArea.innerText = '';
            queryAll();
        }).catch((error) => {
            console.error('Error:', error);
        });
    }

    const MAX_INPUT_LENGTH = 150;

    // Add maxlength attribute to input element when page loads
    document.getElementById('tskConInput').setAttribute('maxlength', MAX_INPUT_LENGTH);
    document.getElementById('modalTskContent').setAttribute('maxlength', MAX_INPUT_LENGTH);

    document.getElementById('btnCreateTodolist').addEventListener('click', () => {

        if (!tskConInput.value) {
            alert('請輸入內容');
            return;
        }
        if (tskConInput.value.length > MAX_INPUT_LENGTH) {
            alert(`內容長度不得超過${MAX_INPUT_LENGTH}字`);
            return;
        }

        const param = {
            user: accountLogin,
            taskContent: tskConInput.value
        };
        addTodoItem(param);
    });

    var closeModal = function () {
        var cuModal = bootstrap.Modal.getInstance(document.getElementById('cuModal'));
        cuModal.hide();
    }

    // 用戶登出
    document.getElementById('logoutButton').addEventListener('click', function () {
        callApi('http://localhost:8080/csp/logout', {}).then(response => {
            if (response.returnCode === '0000') {
                localStorage.removeItem('userName');
                window.location.replace('login');
            } else {
                alert("登出失敗");
            }
        }).catch(error => console.error('錯誤：', error));
    });

    queryAll();
}