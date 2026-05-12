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
public class CodingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String topic;

    private String difficulty;

    private String company;

    private Integer totalQuestions;

    /*
     * AI SCORES
     */

    private Integer codingScore;

    private Integer logicScore;

    private Integer optimizationScore;

    private Integer syntaxScore;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String weaknesses;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private LocalDateTime createdAt;
}