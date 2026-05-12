package com.interview_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiEvaluationResponse {

    private Integer interviewScore;

    private Integer technicalScore;

    private Integer communicationScore;

    private Integer hrScore;

    private String strengths;

    private String weaknesses;

    private String feedback;

    private Integer totalInterviews;
}