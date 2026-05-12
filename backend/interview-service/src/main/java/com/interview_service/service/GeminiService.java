package com.interview_service.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final String URL =
            "https://api.groq.com/openai/v1/chat/completions";

    /*
     * GENERATE FIRST QUESTIONS
     */
    public String generateQuestions(
            String role,
            String experience,
            String techStack
    ) {

        String prompt = """
                Generate 10 professional interview questions.

                Role: %s
                Experience: %s
                Tech Stack: %s

                Include:
                - Technical Questions
                - HR Questions
                - Scenario Based Questions

                Return only questions.
                """.formatted(role, experience, techStack);

        return callGroq(prompt);
    }

    /*
     * GENERATE NEXT QUESTIONS
     */
    public String generateNextQuestions(
            String previousQuestions,
            String previousAnswers,
            String role,
            String techStack
    ) {

        String prompt = """
                Based on previous interview round:

                Previous Questions:
                %s

                Candidate Answers:
                %s

                Generate NEXT 10 advanced interview questions.

                Role: %s
                Tech Stack: %s

                Make questions:
                - more advanced
                - scenario based
                - practical

                Return only questions.
                """.formatted(
                previousQuestions,
                previousAnswers,
                role,
                techStack
        );

        return callGroq(prompt);
    }

    /*
     * EVALUATE ANSWERS
     */
    public String evaluateAnswers(
            String questions,
            String answers
    ) {

        String prompt = """
            Evaluate the interview answers.

            Questions:
            %s

            Answers:
            %s

            IMPORTANT:
            Return ONLY valid JSON.

            JSON FORMAT:

            {
              "interviewScore": 85,
              "technicalScore": 90,
              "communicationScore": 75,
              "hrScore": 80,
              "strengths": "Spring Boot, REST APIs",
              "weaknesses": "System Design",
              "feedback": "Good technical knowledge but needs improvement in architecture."
            }

            Do not return markdown.
            Do not return explanation.
            Return only JSON.
            """.formatted(
                questions,
                answers
        );

        return callGroq(prompt);
    }
    /*
     * FINAL INTERVIEW FEEDBACK
     */
    public String generateInterviewFeedback(
            String allQuestions,
            String allAnswers
    ) {

        String prompt = """
                Generate final interview report.

                Questions:
                %s

                Answers:
                %s

                Provide:
                - Final Score
                - Technical Rating
                - Communication Rating
                - HR Rating
                - Hiring Recommendation
                - Improvement Areas

                Return detailed report.
                """.formatted(
                allQuestions,
                allAnswers
        );

        return callGroq(prompt);
    }

    /*
     * COMMON GROQ CALL METHOD
     */
    private String callGroq(String prompt) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(

                "model",
                "llama-3.3-70b-versatile",

                "messages",
                List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ),

                "temperature",
                0.7
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        URL,
                        HttpMethod.POST,
                        entity,
                        Map.class
                );

        List choices =
                (List) response.getBody().get("choices");

        Map choice =
                (Map) choices.get(0);

        Map message =
                (Map) choice.get("message");

        return message.get("content").toString();
    }

    public String callAI(String prompt) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(

                "model",
                "llama-3.3-70b-versatile",

                "messages",
                List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ),

                "temperature",
                0.7
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        URL,
                        HttpMethod.POST,
                        entity,
                        Map.class
                );

        List choices =
                (List) response.getBody().get("choices");

        Map choice =
                (Map) choices.get(0);

        Map message =
                (Map) choice.get("message");

        return message.get("content").toString();
    }

    public String analyzeResume(String resumeText) {

        String prompt = """
    Analyze this resume carefully.

    Resume:
    %s

    IMPORTANT:
    Return ONLY valid JSON.
    Do NOT return markdown.
    Do NOT return explanation.
    Do NOT use ```json.

    JSON FORMAT:

    {
      "atsScore": 85,
      "strengths": [
        "Strong Java backend skills",
        "Good Spring Boot knowledge"
      ],
      "weaknesses": [
        "No cloud deployment experience",
        "Limited testing experience"
      ],
      "suggestions": [
        "Add deployment projects",
        "Improve resume summary"
      ],
      "interviewQuestions": [
        "Explain Spring Security",
        "What is JWT?"
      ],
      "improvedResume": "Improved resume content here"
    }

    Return ONLY pure JSON.
    """.formatted(resumeText);

        return callGroq(prompt);
    }
}