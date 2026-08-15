# HireSense AI — AI Job Portal with Resume Matching

A full-stack AI/NLP-inspired job portal built with:

- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- React.js + Vite
- REST APIs
- NLP-based resume parsing and skill extraction
- Explainable resume-to-job matching

## Core workflow

Resume PDF/Text
      ↓
Spring Boot Resume API
      ↓
PDF text extraction
      ↓
NLP preprocessing + skill extraction
      ↓
Weighted skill/role matching
      ↓
Ranked job recommendations
      ↓
React dashboard

## Matching logic

The demo matching engine combines:

- Required skill coverage: 60%
- Preferred skill coverage: 20%
- Role/title similarity: 20%

The result also exposes:
- matched skills
- missing required skills
- matched preferred skills
- role similarity
- overall score
- explanation

This is intentionally explainable instead of presenting a mysterious AI score.

## Prerequisites

- JDK 17+
- Maven 3.9+
- MySQL 8+
- Node.js 18+

## 1. Create the database

```sql
CREATE DATABASE hiresense;
```

Then update:

`backend/src/main/resources/application.properties`

with your MySQL username/password.

## 2. Start backend

```bash
cd backend
mvn spring-boot:run
```

Backend:

`http://localhost:8080`

## 3. Start frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend:

`http://localhost:5173`

## Main REST APIs

### Jobs

GET `/api/jobs`

GET `/api/jobs/{id}`

POST `/api/jobs`

### Resume

POST `/api/resumes/analyze`

Multipart field:
`file`

### Matching

POST `/api/matching/resume/{resumeId}`

GET `/api/matching/resume/{resumeId}/recommendations`

### Applications

POST `/api/applications`

GET `/api/applications`

## Example resume analysis

```json
{
  "candidateName": "Demo Candidate",
  "skills": ["Java", "Spring Boot", "React.js", "SQL", "REST APIs"],
  "experienceKeywords": ["full stack", "backend"],
  "summary": "Java full stack developer profile"
}
```

## Portfolio presentation

Feature this project as:

**AI Job Portal with Resume Matching**

> Developed a full-stack job portal using Java, Spring Boot, React.js, SQL and REST APIs. Implemented NLP-based resume parsing, skill extraction and explainable candidate-job matching to rank relevant jobs and identify skill gaps.

For a production deployment, replace the demo NLP matcher with a model/service such as an embedding or transformer pipeline and add authentication, document storage, queues and observability.
