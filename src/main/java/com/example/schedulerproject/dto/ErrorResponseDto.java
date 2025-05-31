package com.example.schedulerproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}
