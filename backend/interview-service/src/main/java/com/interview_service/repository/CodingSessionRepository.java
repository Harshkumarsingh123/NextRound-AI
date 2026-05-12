package com.interview_service.repository;

import com.interview_service.entity.CodingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodingSessionRepository
        extends JpaRepository<CodingSession, Long> {

    CodingSession findTopByUserIdOrderByIdDesc(
            Long userId
    );

    Long countByUserId(Long userId);
}