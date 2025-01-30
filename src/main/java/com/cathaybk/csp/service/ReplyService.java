package com.cathaybk.csp.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cathaybk.csp.model.Reply;
import com.cathaybk.csp.repository.ReplyRepository;
import com.cathaybk.csp.model.Task;
import com.cathaybk.csp.repository.TaskRepository;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class ReplyService {

    private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * 畫面三：[待辦事項][新增筆記]功能
     * @param map
     * @return
     */
    public Map<String, Object> addReply(Map<String, String> map) {
        // 新增Task底下的reply
        Map<String, Object> response = new HashMap<>();
        try {
            String taskIdStr = map.get("taskId");
            String replyContent = map.get("replyContent");

            Integer taskId;
            try {
                taskId = Integer.parseInt(taskIdStr);
            } catch (NumberFormatException e) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Create reply] Wrong format of taskId.");
                return response;
            }

            Task task = taskRepository.findByTaskId(taskId).orElse(null);
            if (task == null || task.getTaskStatus()) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Create reply] taskId does not exist.");
                return response;
            }

            Reply newReply = new Reply(taskId, replyContent, new Date());
            // 更新待辦事項的時間
            task.setUpdateTime(new Date());
            taskRepository.updateTaskContent(task);
            replyRepository.insertReply(newReply);

            response.put("returnCode", "0000");
        } catch (Exception e) {
            logger.error("[Tasks][Create reply] Error occured.", e);
            response.put("returnCode", "9999");
        }
        return response;
    }

    /**
     * 畫面三：[待辦事項][載入筆記]功能
     * @param taskId
     * @return
     */
    public List<Map<String, Object>> findRepliesByTaskId(int taskId) {
        // 從原本的 CspService 拆分
        // 根據 taskId 查詢所有的 reply

        List<Reply> replies = replyRepository.findByTaskId(taskId);
        List<Map<String, Object>> replyList = new ArrayList<>();

        for (Reply reply : replies) {
            Map<String, Object> replyMap = new HashMap<>();
            replyMap.put("replyId", reply.getReplyId());
            replyMap.put("replyContent", reply.getReplyContent());
            replyMap.put("updateTime", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(reply.getCreateTime()));
            replyList.add(replyMap);
        }

        logger.info("[Tasks][View reply] Reply list loaded successfully.");

        return replyList;
    }
}