package com.example.schedulerproject.repository;

import com.example.schedulerproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("user_id");

        Map<String,Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("email", user.getEmail());
        params.put("create_date", user.getCreateDate());
        params.put("update_date", user.getUpdateDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new User(key.longValue(), user.getUsername(), user.getPassword(), user.getEmail(), user.getCreateDate(), user.getUpdateDate());
    }

    @Override
    public User findUserByUsernameAndEmail(String username, String email) {
        String query = "SELECT * FROM users WHERE username = ? AND email = ?";
        List<User> result = jdbcTemplate.query(query, userRowMapper(), username, email);
        // Service에서 null 체크하므로 null 반환
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public User findUserByUserIdOrElseThrow(Long userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        List<User> result = jdbcTemplate.query(query,userRowMapper(),userId);
        return result.stream().findAny().orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with userId: " + userId));
    }

    @Override
    public int updateUser(Long userId, String username, String email) {
        return jdbcTemplate.update(
                "UPDATE users SET username = ?, email = ?, update_date = ? WHERE user_id = ?",
                username,
                email,
                LocalDateTime.now(),
                userId
        );
    }
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("user_id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getTimestamp("create_date").toLocalDateTime(),
                rs.getTimestamp("update_date").toLocalDateTime()
        );
    }
}
