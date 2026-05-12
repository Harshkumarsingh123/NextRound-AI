package com.interview_service.repository;

import com.interview_service.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession, Long> {

    InterviewSession findTopByUserIdOrderByIdDesc(
            Long userId
    );

    Long countByUserId(Long userId);
}