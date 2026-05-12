package com.interview_service.dto;

import lombok.Data;

@Data
public class EvaluateRequest {

    private String questions;

    private String answers;

    private Long sessionId;
}