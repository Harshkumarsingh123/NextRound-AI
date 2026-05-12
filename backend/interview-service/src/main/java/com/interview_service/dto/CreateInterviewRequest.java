package com.interview_service.dto;

import lombok.Data;

@Data
public class CreateInterviewRequest {

    private Long userId;

    private String role;

    private String experienceLevel;

    private String techStack;
}