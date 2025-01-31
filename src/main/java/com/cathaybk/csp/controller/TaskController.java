package com.cathaybk.csp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import com.cathaybk.csp.service.TaskService;

/**
 * Controller class for managing task-related (待辦事項) operations.
 */
@Controller
@CrossOrigin({ "*" })
@RequestMapping("/csp")
public class TaskController {

    @Autowired
    TaskService taskService;

    /**
     * Displays the todo list page (todoList.html).
     *
     * @return the name of the todo list view
     */
    @GetMapping("/todoList")
    public String queryAllPage() {
        return "todoList"; // This will look for todoList.html in the templates folder
    }

    /**
     * Adds a new todo item.
     *
     * @param map a map containing todo item details
     * @return a ResponseEntity containing the result of the add operation
     */
    @PostMapping(value = "/addTodoItem")
    public ResponseEntity<Map<String, Object>> addTodoItem(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(taskService.addTodoItem(map), HttpStatus.OK);
    }

    /**
     * Queries all todo items.
     *
     * @param map a map containing query details
     * @return a ResponseEntity containing the result of the query operation
     */
    @PostMapping(value = "/queryAll")
    public ResponseEntity<Map<String, Object>> queryAll(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(taskService.queryAll(map), HttpStatus.OK);
    }

    /**
     * Edits an existing todo item (編輯內容).
     *
     * @param map a map containing updated todo item details
     * @return a ResponseEntity containing the result of the edit operation
     */
    @PostMapping(value = "/editTodoItem")
    public ResponseEntity<Map<String, Object>> editTodoItem(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(taskService.editTodoItem(map), HttpStatus.OK);
    }

    /**
     * Edits the status of an existing todo item (重新開啟/關閉待辦事項).
     *
     * @param map a map containing updated todo item status
     * @return a ResponseEntity containing the result of the status edit operation
     */
    @PostMapping(value = "/editTodoItemStatus")
    public ResponseEntity<Map<String, Object>> editTodoItemStatus(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(taskService.editTodoItemStatus(map), HttpStatus.OK);
    }

    /**
     * Deletes an existing todo item.
     *
     * @param map a map containing the ID of the todo item to be deleted
     * @return a ResponseEntity containing the result of the delete operation
     */
    @PostMapping(value = "/deleteTask")
    public ResponseEntity<Map<String, Object>> deleteTask(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(taskService.deleteTask(map), HttpStatus.OK);
}
}