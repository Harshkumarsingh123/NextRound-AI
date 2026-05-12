package com.interview_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview_service.dto.*;
import com.interview_service.entity.CodingSession;
import com.interview_service.repository.CodingSessionRepository;
import com.interview_service.service.GeminiService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CodingService {

    private final GeminiService geminiService;

    private final CodingSessionRepository repository;

    private final ObjectMapper objectMapper;

    /*
     * GENERATE QUESTIONS
     */
    public String generateQuestions(
            CodingRequest request
    ) {

        CodingSession session =
                CodingSession.builder()
                        .userId(request.getUserId())
                        .topic(request.getTopic())
                        .difficulty(request.getDifficulty())
                        .company(request.getCompany())
                        .totalQuestions(request.getCount())
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(session);

        String prompt = """
                Generate %s most asked coding interview questions.

                Topic: %s
                Difficulty: %s
                Company: %s

                Include:
                - Problem title
                - Problem statement
                - Example
                - Constraints

                Return only questions.
                """.formatted(
                request.getCount(),
                request.getTopic(),
                request.getDifficulty(),
                request.getCompany()
        );

        return geminiService.callAI(prompt);
    }

    /*
     * GENERATE SOLUTION
     */
    public String generateSolution(
            SolutionRequest request
    ) {

        String prompt = """
                Solve this coding problem.

                Question:
                %s

                Language:
                %s

                Return:
                - Optimized Code
                - Explanation
                - Time Complexity
                - Space Complexity
                """.formatted(
                request.getQuestion(),
                request.getLanguage()
        );

        return geminiService.callAI(prompt);
    }

    /*
     * EVALUATE CODE
     */
    public AiCodingEvaluation evaluateCode(
            CodeEvaluationRequest request
    ) {

        try {

            String prompt = """
                    Evaluate this coding solution.

                    Question:
                    %s

                    User Code:
                    %s

                    Language:
                    %s

                    IMPORTANT:
                    Return ONLY valid JSON.

                    {
                      "codingScore": 85,
                      "logicScore": 90,
                      "optimizationScore": 80,
                      "syntaxScore": 95,
                      "strengths": "Good logic building",
                      "weaknesses": "Optimization can improve",
                      "feedback": "Strong coding skills"
                    }
                    """.formatted(
                    request.getQuestion(),
                    request.getUserCode(),
                    request.getLanguage()
            );

            String aiResponse =
                    geminiService.callAI(prompt);

            AiCodingEvaluation response =
                    objectMapper.readValue(
                            aiResponse,
                            AiCodingEvaluation.class
                    );

            CodingSession session =
                    repository
                            .findById(request.getSessionId())
                            .orElseThrow();

            session.setCodingScore(
                    response.getCodingScore()
            );

            session.setLogicScore(
                    response.getLogicScore()
            );

            session.setOptimizationScore(
                    response.getOptimizationScore()
            );

            session.setSyntaxScore(
                    response.getSyntaxScore()
            );

            session.setStrengths(
                    response.getStrengths()
            );

            session.setWeaknesses(
                    response.getWeaknesses()
            );

            session.setFeedback(
                    response.getFeedback()
            );

            repository.save(session);

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to evaluate coding solution"
            );
        }
    }
}