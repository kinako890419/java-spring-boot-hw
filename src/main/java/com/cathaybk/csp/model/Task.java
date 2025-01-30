package com.cathaybk.csp.model;

import java.util.Date;

/**
 * Represents a Task entity.
 */
public class Task {
    private Integer taskId;
    private Integer userId;
    private String taskContent;
    private Boolean taskStatus;
    private Date createTime;
    private Date updateTime;

    // Constructor
    public Task() {
    }
    public Task(Integer userId, String taskContent, Boolean taskStatus, Date createTime, Date updateTime) {
        this.userId = userId;
        this.taskContent = taskContent;
        this.taskStatus = taskStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getters and setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Boolean getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}