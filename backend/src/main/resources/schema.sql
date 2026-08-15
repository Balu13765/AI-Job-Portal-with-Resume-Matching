CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    company VARCHAR(255),
    location VARCHAR(255),
    employment_type VARCHAR(100),
    description VARCHAR(2000),
    required_skills VARCHAR(2000),
    preferred_skills VARCHAR(2000),
    experience_level VARCHAR(255),
    salary_range VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS resumes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_name VARCHAR(255),
    original_file_name VARCHAR(255),
    extracted_text LONGTEXT,
    extracted_skills VARCHAR(4000),
    profile_keywords VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resume_id BIGINT,
    job_id BIGINT,
    status VARCHAR(100)
);
