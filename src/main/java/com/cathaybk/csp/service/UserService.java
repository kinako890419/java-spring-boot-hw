package com.cathaybk.csp.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cathaybk.csp.model.User;
import com.cathaybk.csp.repository.UserRepository;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * 畫面一：[建立帳戶]功能
     * @param map
     * @return
     */
    public Map<String, Object> createAccount(Map<String, String> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 獲取用戶從前端輸入的帳號與密碼
            String account = map.get("Account");
            String password = map.get("Password");

//            // 檢查帳號密碼格式
//            if (!account.matches("[a-zA-Z0-9]{8}") || !password.matches("[a-zA-Z0-9]{8}")) {
//                response.put("returnCode", "0001");
//                logger.warn("[Create Account] Wrong format of account or password");
//                return response;
//            }

            // 檢查帳號是否存在
            if (userRepository.existsByAccount(account)) {
                response.put("returnCode", "0002");
                logger.warn("[Create Account] Warning : Account already exists");
                return response;
            }

            // 建立新帳戶 (新的 User)
            User newUser = new User(account, password);
            userRepository.insertUser(newUser);

            logger.info("[建立帳戶] 新用戶註冊成功: " + account);

            response.put("returnCode", "0000");
        } catch (Exception e) {
            logger.error("[Create Account] Fail to Create Account", e);
            response.put("returnCode", "9999");
        }
        return response;
    }

    /**
     * 畫面二：[登入]功能
     * @param map
     * @return
     */
    public Map<String, Object> login(Map<String, String> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 獲取前端傳來的帳號與密碼
            String account = map.get("Account");
            String password = map.get("Password");

            // 驗證帳號密碼
            User user = userRepository.findByAccount(account);
            if (user != null && user.getPassword().equals(password)) {
                response.put("returnCode", "0000");
                response.put("user", account);
            } else {
                response.put("returnCode", "9999");
                logger.warn("[User Login] Wrong account or password");
            }
        } catch (Exception e) {
            logger.error("[User Login] Error occured while user login", e);
            response.put("returnCode", "9999");
        }
        return response;
    }

    /**
     * [登出]功能
     * @param map
     * @return
     */
    public Map<String, Object> logout(Map<String, String> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("returnCode", "0000");
            logger.info("=====User Logged Out=====");
        } catch (Exception e) {
            logger.error("[Logout] Error occured while user logout.", e);
            response.put("returnCode", "9999");
        }
        return response;
    }
}