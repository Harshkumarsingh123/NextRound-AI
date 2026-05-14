package com.interview_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeAnalysisResponse {

    private Integer atsScore;

    private List<String> strengths;

    private List<String> weaknesses;

    private List<String> suggestions;

    private List<String> interviewQuestions;

    private String improvedResume;
}