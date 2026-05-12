package com.interview_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String fileName;

    @Column(columnDefinition = "LONGTEXT")
    private String extractedText;

    private Integer atsScore;

    @ElementCollection
    private List<String> strengths;

    @ElementCollection
    private List<String> weaknesses;

    @ElementCollection
    private List<String> suggestions;

    @ElementCollection
    private List<String> interviewQuestions;

    @Column(columnDefinition = "LONGTEXT")
    private String improvedResume;

    private LocalDateTime createdAt;
}