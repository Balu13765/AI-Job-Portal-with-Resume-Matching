package com.hiresense.service;

import com.hiresense.dto.ResumeAnalysisResponse;
import com.hiresense.model.Resume;
import com.hiresense.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final NlpService nlpService;

    public ResumeAnalysisResponse analyze(MultipartFile file) throws IOException {
        String text = extractText(file);
        List<String> skills = nlpService.extractSkills(text);
        List<String> keywords = nlpService.extractProfileKeywords(text);

        Resume resume = Resume.builder()
                .candidateName("Candidate")
                .originalFileName(file.getOriginalFilename())
                .extractedText(text)
                .extractedSkills(String.join(", ", skills))
                .profileKeywords(String.join(", ", keywords))
                .build();

        Resume saved = resumeRepository.save(resume);

        return new ResumeAnalysisResponse(
                saved.getId(),
                saved.getOriginalFileName(),
                skills,
                keywords,
                "Resume parsed and indexed successfully."
        );
    }

    private String extractText(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename() == null ? "" : file.getOriginalFilename().toLowerCase();
        if (name.endsWith(".pdf")) {
            try (PDDocument document = Loader.loadPDF(file.getBytes())) {
                return new PDFTextStripper().getText(document);
            }
        }
        return new String(file.getBytes());
    }
}
