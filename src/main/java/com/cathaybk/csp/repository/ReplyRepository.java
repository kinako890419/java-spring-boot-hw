package com.cathaybk.csp.repository;

import com.cathaybk.csp.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
     * Inserts a new reply into the database.
     *
     * @param reply the reply to be inserted
     */
    public void insertReply(Reply reply) {
        String sql = "INSERT INTO Reply (task_id, reply_content, create_time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reply.getTaskId(), reply.getReplyContent(), reply.getCreateTime());
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