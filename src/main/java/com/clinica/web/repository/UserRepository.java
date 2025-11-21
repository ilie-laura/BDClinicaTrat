package com.clinica.web.repository;

import com.clinica.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository  {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsername(String username){
        String sql="SELECT * FROM Users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User u = new User();
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            return u;
        }, username);

   }

    public User save(User user) {
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        return user;
    }
}
