package com.example.schedulerproject.repository;

import com.example.schedulerproject.dto.PagingRequestDto;
import com.example.schedulerproject.dto.PagingResponseDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;
import com.example.schedulerproject.exception.ScheduleException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor // DI
public class JdbcScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content_todo", schedule.getContentTodo());
        parameters.put("create_date", schedule.getCreateDate());
        parameters.put("update_date", schedule.getUpdateDate());
        parameters.put("user_id", schedule.getUserId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        Schedule existingSchedule = findScheduleByScheduleIdOrElseThrow(key.longValue());
        return new ScheduleResponseDto(existingSchedule);
    }


    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateDate, String username,Long userId) {
        String query =  "SELECT s.schedule_id, s.content_todo, s.create_date, s.update_date, s.user_id, " +
                "u.username, u.email " +
                "FROM schedule s JOIN users u ON s.user_id = u.user_id";
        List<String> values = new ArrayList<>();
        if (updateDate != null && username != null && userId != null) {
            // 3개 조건 모두 있는 경우
            query += " WHERE DATE(s.update_date) = ? AND u.username = ? AND s.user_id = ?";
            values.add(updateDate);
            values.add(username);
            values.add(userId.toString());
        } else if (updateDate != null && username != null) {
            // updateDate + username
            query += " WHERE DATE(s.update_date) = ? AND u.username = ?";
            values.add(updateDate);
            values.add(username);
        } else if (updateDate != null && userId != null) {
            // updateDate + userId
            query += " WHERE DATE(s.update_date) = ? AND s.user_id = ?";
            values.add(updateDate);
            values.add(userId.toString());
        } else if (username != null && userId != null) {
            // username + userId
            query += " WHERE u.username = ? AND s.user_id = ?";
            values.add(username);
            values.add(userId.toString());
        } else if (updateDate != null) {
            // updateDate만
            query += " WHERE DATE(s.update_date) = ?";
            values.add(updateDate);
        } else if (username != null) {
            // username만
            query += " WHERE u.username = ?";
            values.add(username);
        } else if (userId != null) {
            // userId만 (Lv3 추가)
            query += " WHERE s.user_id = ?";
            values.add(userId.toString());
        }
        // 조건이 하나도 없으면 전체 조회

        query += " ORDER BY s.update_date DESC";

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
                        rs.getString("email"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }

    @Override
    public Schedule findScheduleByScheduleIdOrElseThrow(Long scheduleId) {
        String query = "SELECT s.schedule_id, s.content_todo, s.create_date, s.update_date, s.user_id, " +
                "u.username, u.email " +
                "FROM schedule s JOIN users u ON s.user_id = u.user_id " +
                "WHERE s.schedule_id = ?";
        List<Schedule> result = jdbcTemplate.query(query,scheduleRowMapperV2(),scheduleId);
        //Lv 5
        return result.stream().findAny().orElseThrow(() -> new ScheduleException(scheduleId));
    }

    @Override
    public int updateSchedule(Long scheduleId, String contentTodo) {
        return jdbcTemplate.update(
                "UPDATE schedule SET content_todo = ?,  update_date = ? WHERE schedule_id = ?",
                contentTodo,
                LocalDateTime.now(),
                scheduleId
        );
    }

    @Override
    public int deleteSchedule(Long scheduleId) {
        return jdbcTemplate.update("delete from schedule where schedule_id = ?",scheduleId);
    }

    @Override
    public PagingResponseDto findSchedulesPage(PagingRequestDto pagingRequestDto) {
        String query = "SELECT s.schedule_id, s.content_todo, s.create_date, s.update_date, s.user_id, " +
                "u.username, u.email " +
                "FROM schedule s JOIN users u ON s.user_id = u.user_id " +
                "ORDER BY s.update_date DESC " + //내림차순은 유지?
                "LIMIT ? OFFSET ?"; // 가져올 개수 제한 ? 건너뛸 개수
        List<ScheduleResponseDto> schedules = jdbcTemplate.query(
                query,
                scheduleRowMapper(),
                pagingRequestDto.getPageSize(),
                pagingRequestDto.calculateDifference()
        );
        //SELECT * 대신 SELECT COUNT(*)로 단일 숫자 가져오기..
        query = "SELECT COUNT(*) FROM schedule s JOIN users u ON s.user_id = u.user_id";
        // 쿼리,변환기,파라미터값
        int totalSchedules = jdbcTemplate.queryForObject(query, Integer.class);

        int totalPages = totalSchedules / pagingRequestDto.getPageSize();

        return new PagingResponseDto(schedules,totalPages,totalSchedules,pagingRequestDto.getPageNum(),pagingRequestDto.getPageSize());
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("schedule_id"),
                        rs.getString("content_todo"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime(),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("email")
                );
            }
        };
    }
}
