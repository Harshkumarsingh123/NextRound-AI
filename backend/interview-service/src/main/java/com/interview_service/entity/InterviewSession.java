package com.interview_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewSession {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long userId;

        private String role;

        private String experienceLevel;

        private String techStack;

        private Integer totalQuestions;

        private Integer score;

        private String status;

        private LocalDateTime createdAt;

        /*
         * AI ANALYTICS
         */

        private Integer interviewScore;

        private Integer technicalScore;

        private Integer communicationScore;

        private Integer hrScore;

        @Column(columnDefinition = "TEXT")
        private String strengths;

        @Column(columnDefinition = "TEXT")
        private String weaknesses;

        @Column(columnDefinition = "TEXT")
        private String feedback;
    }