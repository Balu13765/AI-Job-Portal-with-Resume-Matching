package com.hiresense.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resumes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String originalFileName;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String extractedText;

    @Column(length = 4000)
    private String extractedSkills;

    @Column(length = 2000)
    private String profileKeywords;
}
