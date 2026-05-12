package com.interview_service.controller;

import com.interview_service.dto.*;
import com.interview_service.service.CodingService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coding")
@RequiredArgsConstructor
public class CodingController {

    private final CodingService codingService;

    /*
     * GENERATE QUESTIONS
     */
    @PostMapping("/generate")
    public String generateQuestions(
            @RequestBody CodingRequest request
    ) {

        return codingService.generateQuestions(request);
    }

    /*
     * GENERATE SOLUTION
     */
    @PostMapping("/solution")
    public String generateSolution(
            @RequestBody SolutionRequest request
    ) {

        return codingService.generateSolution(request);
    }

    /*
     * EVALUATE CODE
     */
    @PostMapping("/evaluate")
    public AiCodingEvaluation evaluateCode(
            @RequestBody CodeEvaluationRequest request
    ) {

        return codingService.evaluateCode(request);
    }
}