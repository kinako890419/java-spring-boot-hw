package com.cathaybk.csp.repository;

import com.cathaybk.csp.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing Reply entities.
 */
@Repository
public class ReplyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Finds replies by task ID.
     *
     * @param taskId the ID of the task whose replies are to be found
     * @return a list of replies associated with the specified task ID
     */
    public List<Reply> findByTaskId(Integer taskId) {
        String sql = "SELECT * FROM Reply WHERE task_id = ? ORDER BY create_time";
        return jdbcTemplate.query(sql, new ReplyRowMapper(), taskId);
    }

    /**
     * Finds a reply by its ID.
     *
     * @param replyId the ID of the reply to be found
     * @return an Optional containing the found reply, or empty if not found
     */
    public Optional<Reply> findByReplyId(Integer replyId) {
        String sql = "SELECT * FROM Reply WHERE reply_id = ?";
        List<Reply> replies = jdbcTemplate.query(sql, new ReplyRowMapper(), replyId);
        return replies.isEmpty() ? Optional.empty() : Optional.of(replies.get(0));
    }

    /**
     * Inserts a new reply into the database.
     *
     * @param reply the reply to be inserted
     */
    public void insertReply(Reply reply) {
        String sql = "INSERT INTO Reply (task_id, reply_content, create_time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reply.getTaskId(), reply.getReplyContent(), reply.getCreateTime());
    }

    /**
     * Deletes all replies associated with a specific task ID
     *
     * @param taskId the ID of the task whose replies are to be deleted
     */
    public void deleteAllReplyByTaskId(Integer taskId) {
        String sql = "DELETE FROM Reply WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    /**
     * Deletes a reply by its ID.
     *
     * @param replyId the ID of the reply to be deleted
     */
    public void deleteReply(Integer replyId) {
        String sql = "DELETE FROM Reply WHERE reply_id = ?";
        jdbcTemplate.update(sql, replyId);
    }

    /**
     * Checks if a reply exists by its ID.
     *
     * @param replyId the ID of the reply to check
     * @return true if the reply exists, false otherwise
     */
    public boolean existsById(Integer replyId) {
        String sql = "SELECT COUNT(*) FROM Reply WHERE reply_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, replyId);
        return count > 0;
    }

    /**
     * RowMapper implementation for mapping rows of a ResultSet to Reply objects.
     */
    private static final class ReplyRowMapper implements RowMapper<Reply> {
        @Override
        public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reply reply = new Reply();
            reply.setReplyId(rs.getInt("reply_id"));
            reply.setTaskId(rs.getInt("task_id"));
            reply.setReplyContent(rs.getString("reply_content"));
            reply.setCreateTime(rs.getTimestamp("create_time"));
            return reply;
        }
    }
}