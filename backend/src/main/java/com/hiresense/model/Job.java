package com.hiresense.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private String employmentType;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String requiredSkills;

    @Column(length = 2000)
    private String preferredSkills;

    private String experienceLevel;
    private String salaryRange;
}
