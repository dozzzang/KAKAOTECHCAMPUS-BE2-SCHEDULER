package com.example.schedulerproject.dto;

import com.example.schedulerproject.entity.Schedule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class ScheduleRequestDto {
    @NotNull
    @NotBlank(message = "할 일은 반드시 작성해야합니다.")
    @Size(max = 200, message = "할 일은 최대 200자까지 작성 가능합니다.")
    private String contentTodo;
    @NotBlank(message = "작성자명은 반드시 필요합니다.")
    private String username;
    @NotNull
    @NotBlank(message = "비밀번호는 반드시 필요합니다.")
    private String password;
    @NotNull
    @NotBlank(message = "이메일은 반드시 필요합니다.")
    @Email
    private String email;
//    private LocalDateTime createDate;
//    private LocalDateTime updateDate;

}
