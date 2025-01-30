package com.cathaybk.csp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CspService {

	/**
     * 範例程式
     * @param demoMap
     * @return
     */
    public Map<String, Object> demoCode(Map<String, String> demoMap) {
        /* 1. 將前端傳入值取出：使用前端傳入物件的key值，從Map中取得對應value，例如：*/
        String id = demoMap.get("id");
        String keyword = demoMap.get("keyword");
        System.err.println("id--->" + id);
        System.err.println("keyword--->" + keyword);

        /* 2. 業務邏輯：檢核、題目要求邏輯實作、
        try (關鍵字：JDBC、try-with-resource) {
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
         */

        /* 3. 把要回傳給前端的值包裝成Map後return，例如： */
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("success", true);
        rtnMap.put("returnMessage", "驗證成功");
        rtnMap.put("metro_fee", 100);
        rtnMap.put("pokerA", new ArrayList<>());
        return rtnMap;
    }

    /**
     * 畫面一：[建立帳戶]功能
     * @param map
     * @return
     */
	public Map<String, Object> createAccount(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 畫面二：[登入]功能
     * @param map
     * @return
     */
	public Map<String, Object> login(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * 畫面三：[待辦事項][新增待辦事項]功能
     * @param map
     * @return
     */
	public Map<String, Object> addTodoItem(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 畫面三：[待辦事項][載入清單]功能
     * @param map
     * @return
     */
	public Map<String, Object> queryAll(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 畫面三：[待辦事項][修改內容]功能
     * @param map
     * @return
     */
	public Map<String, Object> editTodoItem(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 畫面三：[待辦事項][新增筆記]功能
     * @param map
     * @return
     */
	public Map<String, Object> addReply(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 畫面三：[待辦事項][更新任務狀態]功能
     * @param map
     * @return
     */
	public Map<String, Object> editTodoItemStatus(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
    
   
}
