package com.cathaybk.csp.model;

import java.util.Date;

/**
 * Represents a Reply entity.
 */
public class Reply {
    private Integer replyId;
    private Integer taskId;
    private String replyContent;
    private Date createTime;

    // Constructor
    public Reply() {
    }
    public Reply(Integer taskId, String replyContent, Date createTime) {
        this.taskId = taskId;
        this.replyContent = replyContent;
        this.createTime = createTime;
    }

    // Getters and setters
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
