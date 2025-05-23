package com.example.schedulerproject.repository;

import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class JdbcScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content_todo", schedule.getContentTodo());
        parameters.put("username", schedule.getUsername());
        parameters.put("password", schedule.getPassword());
        parameters.put("create_date", schedule.getCreateDate());
        parameters.put("update_date", schedule.getUpdateDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getContentTodo(), schedule.getUsername(), schedule.getCreateDate(), schedule.getUpdateDate());
    }


    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateDate, String username) {
        String query = "SELECT * FROM schedule";
        List<String> values = new ArrayList<>();
        if (updateDate != null && username != null) {
            query += " WHERE DATE(update_date) = ? AND username = ?";
            values.add(updateDate);
            values.add(username);
        } else if (updateDate != null) {
            query += " WHERE DATE(update_date) = ?";
            values.add(updateDate);
        } else if (username != null) {
            query += " WHERE username = ?";
            values.add(username);
        }

        query += " ORDER BY update_date DESC";

        return jdbcTemplate.query(query, scheduleRowMapper(), values.toArray());
    }
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("schedule_id"),
                        rs.getString("content_todo"),
                        rs.getString("username"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }

    @Override
    public Schedule findScheduleByScheduleIdOrElseThrow(Long scheduleId) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?",scheduleRowMapperV2(),scheduleId);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exists schedule id = " + scheduleId));
    }

    @Override
    public int updateSchedule(Long scheduleId, String contentTodo, String username) {
        return jdbcTemplate.update(
                "UPDATE schedule SET content_todo = ?, username = ?, update_date = ? WHERE schedule_id = ?",
                contentTodo,
                username,
                LocalDateTime.now(),
                scheduleId
        );
    }

    @Override
    public int deleteSchedule(Long scheduleId) {
        return jdbcTemplate.update("delete from schedule where schedule_id = ?",scheduleId);
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("schedule_id"),
                        rs.getString("content_todo"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }
}
