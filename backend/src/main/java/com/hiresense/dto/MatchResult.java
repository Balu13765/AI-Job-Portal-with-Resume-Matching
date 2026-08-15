package com.hiresense.dto;

import java.util.List;

public record MatchResult(
        Long jobId,
        String jobTitle,
        String company,
        double score,
        int requiredCoverage,
        int preferredCoverage,
        int roleSimilarity,
        List<String> matchedSkills,
        List<String> missingRequiredSkills,
        List<String> matchedPreferredSkills,
        String explanation
) {}
