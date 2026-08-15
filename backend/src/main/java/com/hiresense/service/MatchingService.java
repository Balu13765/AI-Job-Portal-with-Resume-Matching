package com.hiresense.service;

import com.hiresense.dto.MatchResult;
import com.hiresense.model.Job;
import com.hiresense.model.Resume;
import com.hiresense.repository.JobRepository;
import com.hiresense.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final NlpService nlpService;

    public List<MatchResult> recommend(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("Resume not found"));

        List<String> resumeSkills = nlpService.extractSkills(resume.getExtractedText());

        return jobRepository.findAll().stream()
                .map(job -> calculate(resume, resumeSkills, job))
                .sorted(Comparator.comparingDouble(MatchResult::score).reversed())
                .toList();
    }

    public MatchResult calculate(Resume resume, List<String> resumeSkills, Job job) {
        Set<String> required = splitSkills(job.getRequiredSkills());
        Set<String> preferred = splitSkills(job.getPreferredSkills());
        Set<String> candidate = resumeSkills.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<String> matchedRequired = required.stream()
                .filter(s -> candidate.contains(s.toLowerCase()))
                .map(this::display)
                .toList();

        List<String> missingRequired = required.stream()
                .filter(s -> !candidate.contains(s.toLowerCase()))
                .map(this::display)
                .toList();

        List<String> matchedPreferred = preferred.stream()
                .filter(s -> candidate.contains(s.toLowerCase()))
                .map(this::display)
                .toList();

        int requiredCoverage = required.isEmpty() ? 100 :
                (int) Math.round(matchedRequired.size() * 100.0 / required.size());

        int preferredCoverage = preferred.isEmpty() ? 100 :
                (int) Math.round(matchedPreferred.size() * 100.0 / preferred.size());

        int roleSimilarity = roleSimilarity(resume.getExtractedText(), job.getTitle());

        double score = requiredCoverage * 0.60
                + preferredCoverage * 0.20
                + roleSimilarity * 0.20;

        String explanation = "Strongest signals: " +
                matchedRequired.size() + "/" + required.size() +
                " required skills matched, " +
                matchedPreferred.size() + "/" + preferred.size() +
                " preferred skills matched, and " +
                roleSimilarity + "% role similarity.";

        return new MatchResult(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                Math.round(score * 10.0) / 10.0,
                requiredCoverage,
                preferredCoverage,
                roleSimilarity,
                matchedRequired,
                missingRequired,
                matchedPreferred,
                explanation
        );
    }

    private int roleSimilarity(String resumeText, String title) {
        String resume = nlpService.normalize(resumeText);
        String[] terms = nlpService.normalize(title).split(" ");
        if (terms.length == 0) return 0;

        long matches = Arrays.stream(terms)
                .filter(t -> t.length() > 2 && resume.contains(t))
                .count();

        return (int) Math.round(matches * 100.0 / terms.length);
    }

    private Set<String> splitSkills(String raw) {
        if (raw == null || raw.isBlank()) return Set.of();
        return Arrays.stream(raw.split(","))
                .map(nlpService::normalize)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String display(String skill) {
        return skill;
    }
}
