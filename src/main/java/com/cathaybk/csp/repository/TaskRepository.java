package com.cathaybk.csp.repository;

import com.cathaybk.csp.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing Task entities.
 */
@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Finds tasks by user ID.
     *
     * @param userId the ID of the user whose tasks are to be found
     * @return a list of tasks associated with the specified user ID
     */
    public List<Task> findByUserId(Integer userId) {
        String sql = "SELECT * FROM Task WHERE user_id = ? ORDER BY task_id DESC";
        return jdbcTemplate.query(sql, new TaskRowMapper(), userId);
    }

    /**
     * Inserts a new task into the database.
     *
     * @param task the task to be inserted
     */
    public void insertTask(Task task) {
        String sql = "INSERT INTO Task (user_id, task_content, task_status, create_time, update_time) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getUserId(), task.getTaskContent(), task.getTaskStatus(), task.getCreateTime(), task.getUpdateTime());
    }

    /**
     * Updates the content of an existing task.
     *
     * @param task the task with updated content
     */
    public void updateTaskContent(Task task) {
        String sql = "UPDATE Task SET task_content = ?, update_time = ? WHERE task_id = ?";
        jdbcTemplate.update(sql, task.getTaskContent(), task.getUpdateTime(), task.getTaskId());
    }

    /**
     * Updates the status of an existing task.
     *
     * @param task the task with updated status
     */
    public void updateTaskStatus(Task task) {
        String sql = "UPDATE Task SET task_status = ?, update_time = ? WHERE task_id = ?";
        jdbcTemplate.update(sql, task.getTaskStatus(), task.getUpdateTime(), task.getTaskId());
    }

    /**
     * Finds a task by its ID.
     *
     * @param taskId the ID of the task to be found
     * @return an Optional containing the found task, or an empty Optional if no task is found
     */
    public Optional<Task> findByTaskId(Integer taskId) {
        String sql = "SELECT * FROM Task WHERE task_id = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), taskId);
        return tasks.isEmpty() ? Optional.empty() : Optional.of(tasks.get(0));
    }

    /**
     * RowMapper implementation for mapping rows of a ResultSet to Task objects.
     */
    private static final class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setTaskId(rs.getInt("task_id"));
            task.setUserId(rs.getInt("user_id"));
            task.setTaskContent(rs.getString("task_content"));
            task.setTaskStatus(rs.getBoolean("task_status"));
            task.setCreateTime(rs.getTimestamp("create_time"));
            task.setUpdateTime(rs.getTimestamp("update_time"));
            return task;
        }
    }
}