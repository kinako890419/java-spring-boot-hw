package com.cathaybk.csp.repository;

import com.cathaybk.csp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repository class for managing User entities.
 */
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Finds a User by their account.
     *
     * @param account the account of the User to find
     * @return the User with the specified account
     */
    public User findByAccount(String account) {
        String sql = "SELECT * FROM User WHERE account = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), account);
    }

    /**
     * Inserts a new User into the database.
     *
     * @param user the User to insert
     */
    public void insertUser(User user) {
        String sql = "INSERT INTO User (account, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword());
    }

    /**
     * Checks if a User exists by their account.
     *
     * @param account the account to check
     * @return true if a User with the specified account exists, false otherwise
     */
    public boolean existsByAccount(String account) {
        String sql = "SELECT COUNT(*) FROM User WHERE account = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, account);
        return count != null && count > 0;
    }

    /**
     * RowMapper implementation for mapping rows of a ResultSet to User objects.
     */
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setAccount(rs.getString("account"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}