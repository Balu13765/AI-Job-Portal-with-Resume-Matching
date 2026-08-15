package com.hiresense.controller;

import com.hiresense.dto.MatchResult;
import com.hiresense.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/resume/{resumeId}")
    public List<MatchResult> match(@PathVariable Long resumeId) {
        return matchingService.recommend(resumeId);
    }

    @GetMapping("/resume/{resumeId}/recommendations")
    public List<MatchResult> recommendations(@PathVariable Long resumeId) {
        return matchingService.recommend(resumeId);
    }
}
