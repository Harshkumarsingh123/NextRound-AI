package com.interview_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview_service.dto.*;
import com.interview_service.entity.InterviewSession;
import com.interview_service.repository.InterviewSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewSessionRepository sessionRepo;

    private final GeminiService geminiService;

    private final ObjectMapper objectMapper;

    public StartInterviewResponse createInterview(
            CreateInterviewRequest request
    ) {

        InterviewSession session =
                InterviewSession.builder()
                        .userId(request.getUserId())
                        .role(request.getRole())
                        .experienceLevel(request.getExperienceLevel())
                        .techStack(request.getTechStack())
                        .status("STARTED")
                        .createdAt(LocalDateTime.now())
                        .build();

        sessionRepo.save(session);

        String questions =
                geminiService.generateQuestions(
                        request.getRole(),
                        request.getExperienceLevel(),
                        request.getTechStack()
                );

        return StartInterviewResponse.builder()
                .sessionId(session.getId())
                .questions(questions)
                .build();
    }
    public String generateNextQuestions(
            NextQuestionRequest request
    ) {

        return geminiService.generateNextQuestions(
                request.getPreviousQuestions(),
                request.getPreviousAnswers(),
                request.getRole(),
                request.getTechStack()
        );
    }

    public AiEvaluationResponse evaluateAnswers(
            EvaluateRequest request
    ) {

        try {

            String aiResponse =
                    geminiService.evaluateAnswers(
                            request.getQuestions(),
                            request.getAnswers()
                    );

            AiEvaluationResponse response =
                    objectMapper.readValue(
                            aiResponse,
                            AiEvaluationResponse.class
                    );

            InterviewSession session =
                    sessionRepo
                            .findById(request.getSessionId())
                            .orElseThrow();

            session.setInterviewScore(
                    response.getInterviewScore()
            );

            session.setTechnicalScore(
                    response.getTechnicalScore()
            );

            session.setCommunicationScore(
                    response.getCommunicationScore()
            );

            session.setHrScore(
                    response.getHrScore()
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

            sessionRepo.save(session);

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to evaluate answers"
            );
        }
    }

    public InterviewSession getDashboard(
            Long userId
    ) {

        return sessionRepo
                .findTopByUserIdOrderByIdDesc(userId);
    }
}