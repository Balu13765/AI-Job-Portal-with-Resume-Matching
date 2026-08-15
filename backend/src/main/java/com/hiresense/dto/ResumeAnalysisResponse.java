package com.hiresense.dto;

import java.util.List;

public record ResumeAnalysisResponse(
        Long resumeId,
        String fileName,
        List<String> extractedSkills,
        List<String> profileKeywords,
        String message
) {}
