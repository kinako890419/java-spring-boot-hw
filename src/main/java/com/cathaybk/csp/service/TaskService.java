package com.cathaybk.csp.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cathaybk.csp.model.Task;
import com.cathaybk.csp.model.User;
import com.cathaybk.csp.model.Reply;
import com.cathaybk.csp.repository.TaskRepository;
import com.cathaybk.csp.repository.UserRepository;
import com.cathaybk.csp.repository.ReplyRepository;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // @Autowired
    // private ReplyService replyService;

    /**
     * 畫面三：[待辦事項][新增待辦事項]功能
     * @param map
     * @return
     */
    public Map<String, Object> addTodoItem(Map<String, String> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            String userAccount = map.get("user");
            String taskContent = map.get("taskContent");

            User user = userRepository.findByAccount(userAccount);
            if (user == null) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Add Tasks] User does not exist.");
                return response;
            }

            // 新增 Task
            Task newTask = new Task(user.getUserId(), taskContent, false, new Date(), new Date());
            taskRepository.insertTask(newTask);

            response.put("returnCode", "0000");
            logger.info("[Tasks][Add Tasks] Task added successfully.");
        } catch (Exception e) {
            logger.error("[Tasks][Add Tasks] Failed to add tasks.", e);
            response.put("returnCode", "9999");
        }
        return response;
    }

    /**
     * 畫面三：[待辦事項][載入清單]功能
     * @param map
     * @return
     */
    public Map<String, Object> queryAll(Map<String, String> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            String account = map.get("Account");

            User user = userRepository.findByAccount(account);
            if (user == null) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][View Tasks] User does not exist.");
                return response;
            }

            // 根據 User ID 取得 Task List
            List<Task> tasks = taskRepository.findByUserId(user.getUserId());
            List<Map<String, Object>> taskList = new ArrayList<>();

            for (Task task : tasks) {
                Map<String, Object> taskMap = new HashMap<>();
                taskMap.put("taskId", task.getTaskId());
                taskMap.put("taskContent", task.getTaskContent());
                taskMap.put("taskStatus", task.getTaskStatus() ? "Done" : "Todo");
                taskMap.put("updateTime", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(task.getUpdateTime()));

                // 取得 Task 底下的 Reply List
                List<Reply> replies = replyRepository.findByTaskId(task.getTaskId());
                List<Map<String, Object>> replyList = new ArrayList<>();
                for (Reply reply : replies) {
                    Map<String, Object> replyMap = new HashMap<>();
                    replyMap.put("replyId", reply.getReplyId());
                    replyMap.put("replyContent", reply.getReplyContent());
                    replyMap.put("updateTime", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(reply.getCreateTime()));
                    replyList.add(replyMap);
                }

                taskMap.put("replyCount", replies.size());
                taskMap.put("reply", replyList);

                taskList.add(taskMap);
            }

            response.put("returnCode", "0000");
            response.put("task", taskList);

            logger.info("[Tasks][View Tasks] Task list loaded successfully.");

        } catch (Exception e) {
            logger.error("[Tasks][View Tasks] Failed to load task lists.", e);
            response.put("returnCode", "9999");
        }
        return response;
    }
    /**
     * 畫面三：[待辦事項][修改內容]功能
     * @param map
     * @return
     */
    public Map<String, Object> editTodoItem(Map<String, String> map) {
        // 修改 Task 內容
        Map<String, Object> response = new HashMap<>();
        try {
            String taskIdStr = map.get("taskId");
            String taskContent = map.get("taskContent");

            Integer taskId;
            try {
                taskId = Integer.parseInt(taskIdStr);
            } catch (NumberFormatException e) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Update Tasks] Wrong format of task id", e);
                return response;
            }

            Task task = taskRepository.findByTaskId(taskId).orElse(null);
            if (task == null) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Update Tasks] Task id does not exist.");
                return response;
            }

            task.setTaskContent(taskContent);
            task.setUpdateTime(new Date());
            taskRepository.updateTaskContent(task);

            response.put("returnCode", "0000");
        } catch (Exception e) {
            logger.error("[Tasks][Update Tasks] Error occured.", e);
            response.put("returnCode", "9999");
        }
        return response;
    }

    /**
     * 畫面三：[待辦事項][更新任務狀態]功能
     * @param map
     * @return
     */
    public Map<String, Object> editTodoItemStatus(Map<String, String> map) {
        // Todo or Done
        Map<String, Object> response = new HashMap<>();
        try {
            String taskIdStr = map.get("taskId");
            String taskStatusStr = map.get("taskStatus");

            Integer taskId;
            try {
                taskId = Integer.parseInt(taskIdStr);
            } catch (NumberFormatException e) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Update Tasks Status] Wrong task id format.", e);
                return response;
            }

            // 確認 task 狀態 (Todo or Done)
            boolean taskStatus = "Done".equalsIgnoreCase(taskStatusStr);

            // 更新狀態和 update time
            Date updateTime = new Date();

            Task task = taskRepository.findByTaskId(taskId).orElse(null);
            if (task == null) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Update Tasks Status] Task id does not exist.");
                return response;
            }

            task.setTaskStatus(taskStatus);
            task.setUpdateTime(updateTime);
            taskRepository.updateTaskStatus(task);

            response.put("returnCode", "0000");
        } catch (Exception e) {
            logger.error("[Tasks][Update Tasks Status] Error editing todo item (task) status", e);
            response.put("returnCode", "9999");
        }
        return response;
    }

    /**
     * 新增功能：[待辦事項][刪除待辦事項]功能
     * @param map
     * @return
     */
    public Map<String, Object> deleteTask(Map<String, String> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            String taskIdStr = map.get("taskId");

            Integer taskId;
            try {
                taskId = Integer.parseInt(taskIdStr);
            } catch (NumberFormatException e) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Delete Task] Wrong format of task id", e);
                return response;
            }

            Task task = taskRepository.findByTaskId(taskId).orElse(null);
            if (task == null) {
                response.put("returnCode", "9999");
                logger.warn("[Tasks][Delete Task] Task id does not exist.");
                return response;
            }

            // 刪除 Task
            // 先刪除所有關聯的 Reply
            replyRepository.deleteAllReplyByTaskId(taskId);
            // 再刪除 Task
            taskRepository.deleteTask(taskId);

            response.put("returnCode", "0000");
            logger.info("[Tasks][Delete Task] Task and associated replies deleted successfully.");
        } catch (Exception e) {
            logger.error("[Tasks][Delete Task] Error occurred.", e);
            response.put("returnCode", "9999");
        }
        return response;
    }
}