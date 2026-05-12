package com.interview_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview_service.dto.ResumeAnalysisResponse;
import com.interview_service.entity.ResumeAnalysis;
import com.interview_service.repository.ResumeAnalysisRepository;

import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final GeminiService geminiService;

    private final ResumeAnalysisRepository repository;

    private final ObjectMapper objectMapper;

    public ResumeAnalysisResponse analyzeResume(
            MultipartFile file,
            Long userId
    ) {

        try {

            InputStream inputStream =
                    file.getInputStream();

            PDDocument document =
                    PDDocument.load(inputStream);

            PDFTextStripper stripper =
                    new PDFTextStripper();

            String resumeText =
                    stripper.getText(document);

            document.close();

            String aiResponse =
                    geminiService.analyzeResume(resumeText);

            System.out.println(aiResponse);

            aiResponse = aiResponse
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            ResumeAnalysisResponse response =
                    objectMapper.readValue(
                            aiResponse,
                            ResumeAnalysisResponse.class
                    );;

            ResumeAnalysis analysis =
                    ResumeAnalysis.builder()

                            .userId(userId)

                            .fileName(file.getOriginalFilename())

                            .extractedText(resumeText)

                            .atsScore(response.getAtsScore())

                            .strengths(response.getStrengths())

                            .weaknesses(response.getWeaknesses())

                            .suggestions(response.getSuggestions())

                            .interviewQuestions(
                                    response.getInterviewQuestions()
                            )

                            .improvedResume(
                                    response.getImprovedResume()
                            )

                            .createdAt(LocalDateTime.now())

                            .build();

            repository.save(analysis);

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to analyze resume: " + e.getMessage()
            );
        }
    }
}