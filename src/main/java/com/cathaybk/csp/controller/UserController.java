package com.cathaybk.csp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import com.cathaybk.csp.service.UserService;

/**
 * Controller class for managing user-related operations.
 */
@Controller
@CrossOrigin({ "*" })
@RequestMapping("/csp")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Displays the login page.
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This will look for login.html in the templates folder
    }

    /**
     * Displays the create account page.
     *
     * @return the name of the create account view
     */
    @GetMapping("/createAccount")
    public String createAccountPage() {
        return "createAccount"; // This will look for createAccount.html in the templates folder
    }

    /**
     * 使用者建立帳戶
     *
     * @param map a map containing account details
     * @return a ResponseEntity containing the result of the account creation
     */
    @PostMapping(value = "/createAccount")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(userService.createAccount(map), HttpStatus.OK);
    }

    /**
     * 使用者登入
     *
     * @param map a map containing login details
     * @return a ResponseEntity containing the result of the login operation
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(userService.login(map), HttpStatus.OK);
    }

    /**
     * 使用者登出
     *
     * @param map a map containing logout details
     * @return a ResponseEntity containing the result of the logout operation
     */
    @PostMapping(value = "/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(userService.logout(map), HttpStatus.OK);
    }
}