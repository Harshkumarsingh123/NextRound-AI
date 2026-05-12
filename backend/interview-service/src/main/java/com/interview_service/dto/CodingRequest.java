package com.interview_service.dto;

import lombok.Data;

@Data
public class CodingRequest {

    private Long userId;

    private String topic;

    private String difficulty;

    private String company;

    private Integer count;
}