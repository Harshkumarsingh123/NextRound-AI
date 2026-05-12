package com.interview_service.dto;

import lombok.Data;

@Data
public class CodeEvaluationRequest {

    private Long sessionId;

    private String question;

    private String userCode;

    private String language;
}