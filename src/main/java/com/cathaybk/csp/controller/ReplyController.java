package com.cathaybk.csp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cathaybk.csp.service.ReplyService;

/**
 * Controller class for managing reply-related (待辦事項底下的筆記) operations.
 */
@RestController
@CrossOrigin({ "*" })
@RequestMapping("/csp")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    /**
     * Adds a new reply (新增筆記).
     *
     * @param map a map containing reply details
     * @return a ResponseEntity containing the result of the add operation
     */
    @PostMapping(value = "/addReply")
    public ResponseEntity<Map<String, Object>> addReply(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(replyService.addReply(map), HttpStatus.OK);
    }

    /**
     * Deletes a reply.
     *
     * @param map a map containing the ID of the reply to be deleted
     * @return a ResponseEntity containing the result of the delete operation
     */
    @PostMapping(value = "/deleteReply")
    public ResponseEntity<Map<String, Object>> deleteReply(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(replyService.deleteReply(map), HttpStatus.OK);
    }
}