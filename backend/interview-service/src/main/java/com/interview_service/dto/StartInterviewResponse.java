package com.interview_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartInterviewResponse {

    private Long sessionId;

    private String questions;
}