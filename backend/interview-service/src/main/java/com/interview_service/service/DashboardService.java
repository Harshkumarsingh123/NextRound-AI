package com.interview_service.service;

import com.interview_service.dto.AiEvaluationResponse;
import com.interview_service.entity.InterviewSession;
import com.interview_service.repository.InterviewSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InterviewSessionRepository repository;

    public AiEvaluationResponse getDashboard(
            Long userId
    ) {

        InterviewSession session =
                repository
                        .findTopByUserIdOrderByIdDesc(userId);

        if (session == null) {

            return AiEvaluationResponse.builder()

                    .interviewScore(0)
                    .technicalScore(0)
                    .communicationScore(0)
                    .hrScore(0)

                    .strengths("No data")
                    .weaknesses("No data")
                    .feedback("No interview completed yet")

                    .totalInterviews(0)

                    .build();
        }

        Long totalInterviews =
                repository.countByUserId(userId);

        return AiEvaluationResponse.builder()

                .interviewScore(
                        session.getInterviewScore()
                )

                .technicalScore(
                        session.getTechnicalScore()
                )

                .communicationScore(
                        session.getCommunicationScore()
                )

                .hrScore(
                        session.getHrScore()
                )

                .strengths(
                        session.getStrengths()
                )

                .weaknesses(
                        session.getWeaknesses()
                )

                .feedback(
                        session.getFeedback()
                )

                .totalInterviews(
                        totalInterviews.intValue()
                )

                .build();
    }
}