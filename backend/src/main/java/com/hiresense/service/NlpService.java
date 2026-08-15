package com.hiresense.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NlpService {

    private static final List<String> SKILLS = List.of(
            "java", "python", "spring boot", "spring", "hibernate", "react.js",
            "react", "javascript", "typescript", "html", "css", "bootstrap",
            "sql", "mysql", "oracle", "mongodb", "rest api", "rest apis",
            "git", "github", "aws", "linux", "docker", "kubernetes",
            "microservices", "machine learning", "nlp", "natural language processing",
            "data structures", "algorithms", "jwt", "spring security"
    );

    private static final Map<String, String> NORMALIZATION = Map.ofEntries(
            Map.entry("react", "React.js"),
            Map.entry("react.js", "React.js"),
            Map.entry("spring", "Spring Boot"),
            Map.entry("rest api", "REST APIs"),
            Map.entry("rest apis", "REST APIs"),
            Map.entry("natural language processing", "NLP"),
            Map.entry("nlp", "NLP"),
            Map.entry("javascript", "JavaScript"),
            Map.entry("java", "Java"),
            Map.entry("python", "Python"),
            Map.entry("sql", "SQL"),
            Map.entry("mysql", "MySQL"),
            Map.entry("mongodb", "MongoDB"),
            Map.entry("spring boot", "Spring Boot"),
            Map.entry("hibernate", "Hibernate"),
            Map.entry("aws", "AWS"),
            Map.entry("git", "Git"),
            Map.entry("docker", "Docker"),
            Map.entry("kubernetes", "Kubernetes"),
            Map.entry("microservices", "Microservices"),
            Map.entry("jwt", "JWT"),
            Map.entry("spring security", "Spring Security")
    );

    public List<String> extractSkills(String text) {
        String normalized = normalize(text);
        Set<String> found = new LinkedHashSet<>();

        for (String skill : SKILLS) {
            if (normalized.contains(skill)) {
                found.add(NORMALIZATION.getOrDefault(skill, titleCase(skill)));
            }
        }
        return new ArrayList<>(found);
    }

    public List<String> extractProfileKeywords(String text) {
        String normalized = normalize(text);
        List<String> keywords = new ArrayList<>();
        String[] roleTerms = {
                "software engineer", "software developer", "full stack",
                "java developer", "backend developer", "frontend developer",
                "web developer", "intern", "developer", "engineer"
        };

        for (String term : roleTerms) {
            if (normalized.contains(term)) {
                keywords.add(term);
            }
        }
        return keywords;
    }

    public String normalize(String text) {
        return text == null ? "" :
                text.toLowerCase(Locale.ROOT)
                        .replaceAll("[^a-z0-9+#.\\s]", " ")
                        .replaceAll("\\s+", " ")
                        .trim();
    }

    public Set<String> tokenize(String text) {
        return Pattern.compile("\\s+")
                .splitAsStream(normalize(text))
                .filter(token -> token.length() > 1)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private String titleCase(String input) {
        return Arrays.stream(input.split(" "))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }
}
