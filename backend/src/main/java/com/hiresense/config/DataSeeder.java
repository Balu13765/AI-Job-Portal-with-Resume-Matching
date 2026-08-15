package com.hiresense.config;

import com.hiresense.model.Job;
import com.hiresense.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedJobs(JobRepository repository) {
        return args -> {
            if (repository.count() > 0) return;

            repository.save(Job.builder()
                    .title("Java Full Stack Developer")
                    .company("TechNova Systems")
                    .location("Bengaluru, India")
                    .employmentType("Full-time")
                    .experienceLevel("Fresher / 0-1 years")
                    .salaryRange("₹4.5–7 LPA")
                    .description("Build production web applications across React and Spring Boot services.")
                    .requiredSkills("Java, Spring Boot, React.js, SQL, REST APIs, Git")
                    .preferredSkills("Hibernate, MySQL, Docker, JavaScript")
                    .build());

            repository.save(Job.builder()
                    .title("Associate Software Engineer")
                    .company("CloudBridge Labs")
                    .location("Hyderabad, India")
                    .employmentType("Full-time")
                    .experienceLevel("Entry level")
                    .salaryRange("₹4–6 LPA")
                    .description("Develop backend services and modern frontend modules for enterprise applications.")
                    .requiredSkills("Java, SQL, REST APIs, Git, JavaScript")
                    .preferredSkills("Spring Boot, React.js, AWS, Docker")
                    .build());

            repository.save(Job.builder()
                    .title("Python Full Stack Developer")
                    .company("DataForge Technologies")
                    .location("Chennai, India")
                    .employmentType("Full-time")
                    .experienceLevel("0-2 years")
                    .salaryRange("₹4–7 LPA")
                    .description("Develop data-driven web products with Python APIs and React interfaces.")
                    .requiredSkills("Python, React.js, SQL, REST APIs, Git")
                    .preferredSkills("NLP, MongoDB, AWS, Docker")
                    .build());

            repository.save(Job.builder()
                    .title("Junior Backend Developer")
                    .company("FinAxis Digital")
                    .location("Bengaluru, India")
                    .employmentType("Full-time")
                    .experienceLevel("Fresher")
                    .salaryRange("₹4–6.5 LPA")
                    .description("Work on secure Java backend services and database-driven APIs.")
                    .requiredSkills("Java, SQL, Spring Boot, REST APIs")
                    .preferredSkills("Hibernate, Spring Security, JWT, Microservices")
                    .build());
        };
    }
}
