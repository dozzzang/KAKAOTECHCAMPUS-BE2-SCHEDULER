package com.example.schedulerproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagingRequestDto {
    private int pageNum = 1;
    private int pageSize = 5;

    public PagingRequestDto(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int calculateDifference() {
        return (pageNum - 1) * pageSize;
    }
}
