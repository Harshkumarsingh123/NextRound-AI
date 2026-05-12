package com.interview_service.controller;

import com.interview_service.dto.*;

import com.interview_service.entity.InterviewSession;
import com.interview_service.service.InterviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    /*
     * START INTERVIEW
     */
    @PostMapping("/start")
    public StartInterviewResponse startInterview(
                                                 @RequestBody CreateInterviewRequest request
    ) {

        return interviewService.createInterview(request);
    }

    /*
     * NEXT QUESTIONS
     */
    @PostMapping("/next")
    public String nextQuestions(
            @RequestBody NextQuestionRequest request
    ) {

        return interviewService.generateNextQuestions(request);
    }

    /*
     * EVALUATE ANSWERS
     */
    @PostMapping("/evaluate")
    public AiEvaluationResponse evaluateAnswers(
            @RequestBody EvaluateRequest request
    ) {

        return interviewService.evaluateAnswers(request);
    }

    @GetMapping("/dashboard/{userId}")
    public InterviewSession getDashboard(
            @PathVariable Long userId
    ) {

        return interviewService.getDashboard(userId);
    }
}