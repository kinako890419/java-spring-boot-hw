CREATE DATABASE IF NOT EXISTS csp;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    account VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL
);
CREATE TABLE Task (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    task_content VARCHAR(255) NOT NULL,
    task_status BOOLEAN NOT NULL,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);
CREATE TABLE Reply ( -- Memo: Reply 預計只能刪不能編輯，所以只有建立時間，無更新時間
    reply_id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT,
    reply_content VARCHAR(255) NOT NULL,
    create_time DATETIME NOT NULL,
    FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE
);
