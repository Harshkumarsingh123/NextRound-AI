package com.interview_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiCodingEvaluation {

    private Integer codingScore;

    private Integer logicScore;

    private Integer optimizationScore;

    private Integer syntaxScore;

    private String strengths;

    private String weaknesses;

    private String feedback;
}