package com.interview_service.controller;

import com.interview_service.dto.ResumeAnalysisResponse;
import com.interview_service.service.ResumeService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/analyze")
    public ResumeAnalysisResponse analyzeResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long userId
    ) {

        return resumeService.analyzeResume(
                file,
                userId
        );
    }

}
