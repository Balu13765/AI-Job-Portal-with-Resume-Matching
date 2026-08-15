CREATE DATABASE IF NOT EXISTS hiresense;
USE hiresense;

INSERT INTO jobs
(title, company, location, employment_type, description, required_skills, preferred_skills, experience_level, salary_range)
VALUES
('Java Full Stack Developer','TechNova Systems','Bengaluru, India','Full-time',
'Build production web applications across React and Spring Boot services.',
'Java,Spring Boot,React.js,SQL,REST APIs,Git',
'Hibernate,MySQL,Docker,JavaScript','Fresher / 0-1 years','₹4.5–7 LPA'),

('Associate Software Engineer','CloudBridge Labs','Hyderabad, India','Full-time',
'Develop backend services and modern frontend modules for enterprise applications.',
'Java,SQL,REST APIs,Git,JavaScript',
'Spring Boot,React.js,AWS,Docker','Entry level','₹4–6 LPA'),

('Python Full Stack Developer','DataForge Technologies','Chennai, India','Full-time',
'Develop data-driven web products with Python APIs and React interfaces.',
'Python,React.js,SQL,REST APIs,Git',
'NLP,MongoDB,AWS,Docker','0-2 years','₹4–7 LPA'),

('Junior Backend Developer','FinAxis Digital','Bengaluru, India','Full-time',
'Work on secure Java backend services and database-driven APIs.',
'Java,SQL,Spring Boot,REST APIs',
'Hibernate,Spring Security,JWT,Microservices','Fresher','₹4–6.5 LPA');
