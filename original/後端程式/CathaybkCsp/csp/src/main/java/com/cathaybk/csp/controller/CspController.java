package com.cathaybk.csp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cathaybk.csp.service.CspService;

@RestController
@CrossOrigin({ "*" })
@RequestMapping("/csp")
public class CspController {

	@Autowired
	CspService cspService;

	/**
	 * 範例程式
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/demoCode")
	public ResponseEntity<Map<String, Object>> demoCode(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.demoCode(map), HttpStatus.OK);
	}

	/**
	 * 畫面一：[建立帳戶]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/createAccount")
	public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.createAccount(map), HttpStatus.OK);
	}
	
	/**
	 * 畫面二：[登入]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.login(map), HttpStatus.OK);
	}
	
	/**
	 * 畫面三：[待辦事項][新增待辦事項]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/addTodoItem")
	public ResponseEntity<Map<String, Object>> addTodoItem(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.addTodoItem(map), HttpStatus.OK);
	}
	
	/**
	 * 畫面三：[待辦事項][載入清單]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/queryAll")
	public ResponseEntity<Map<String, Object>> queryAll(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.queryAll(map), HttpStatus.OK);
	}
	
	/**
	 * 畫面三：[待辦事項][修改內容]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/editTodoItem")
	public ResponseEntity<Map<String, Object>> editTodoItem(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.editTodoItem(map), HttpStatus.OK);
	}
	
	
	/**
	 * 畫面三：[待辦事項][新增筆記]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/addReply")
	public ResponseEntity<Map<String, Object>> addReply(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.addReply(map), HttpStatus.OK);
	}
	
	/**
	 * 畫面三：[待辦事項][更新任務狀態]功能
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/editTodoItemStatus")
	public ResponseEntity<Map<String, Object>> editTodoItemStatus(@RequestBody Map<String, String> map) {
		return new ResponseEntity<>(cspService.editTodoItemStatus(map), HttpStatus.OK);
	}

	

}