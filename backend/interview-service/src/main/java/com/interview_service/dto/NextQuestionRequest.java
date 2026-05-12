package com.interview_service.dto;

import lombok.Data;

@Data
public class NextQuestionRequest {

    private String previousQuestions;

    private String previousAnswers;

    private String role;

    private String techStack;
}