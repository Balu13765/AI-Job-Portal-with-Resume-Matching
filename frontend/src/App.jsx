import React, { useEffect, useMemo, useState } from "react";
import axios from "axios";
import {
  BrainCircuit, UploadCloud, Search, MapPin, BriefcaseBusiness,
  CheckCircle2, XCircle, ArrowRight, Sparkles, BarChart3,
  FileText, Send, Bookmark, ChevronDown
} from "lucide-react";

const API = "http://localhost:8080/api";

function App() {
  const [jobs, setJobs] = useState([]);
  const [results, setResults] = useState([]);
  const [resume, setResume] = useState(null);
  const [skills, setSkills] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [selected, setSelected] = useState(null);
  const [saved, setSaved] = useState(new Set());
  const [error, setError] = useState("");

  useEffect(() => {
    axios.get(`${API}/jobs`).then(r => setJobs(r.data)).catch(() =>
      setError("Backend is not running. Start Spring Boot on port 8080.")
    );
  }, []);

  const visibleJobs = useMemo(() => {
    const q = search.toLowerCase();
    return jobs.filter(j =>
      `${j.title} ${j.company} ${j.location}`.toLowerCase().includes(q)
    );
  }, [jobs, search]);

  async function uploadResume(file) {
    if (!file) return;
    setLoading(true);
    setError("");
    try {
      const form = new FormData();
      form.append("file", file);
      const analyzed = await axios.post(`${API}/resumes/analyze`, form);
      setResume(analyzed.data);
      setSkills(analyzed.data.extractedSkills);
      const match = await axios.get(
        `${API}/matching/resume/${analyzed.data.resumeId}/recommendations`
      );
      setResults(match.data);
    } catch (e) {
      setError(e.response?.data?.message || "Resume analysis failed.");
    } finally {
      setLoading(false);
    }
  }

  function toggleSave(id) {
    setSaved(prev => {
      const next = new Set(prev);
      next.has(id) ? next.delete(id) : next.add(id);
      return next;
    });
  }

  async function apply(jobId) {
    if (!resume?.resumeId) {
      alert("Upload and analyze your resume first.");
      return;
    }
    await axios.post(`${API}/applications`, {
      resumeId: resume.resumeId,
      jobId,
      status: "APPLIED"
    });
    alert("Application recorded successfully.");
  }

  return (
    <div className="app">
      <header className="nav">
        <div className="brand">
          <div className="brandIcon"><BrainCircuit size={23}/></div>
          <div>
            <strong>HireSense<span> AI</span></strong>
            <small>Intelligent job matching</small>
          </div>
        </div>
        <nav>
          <a href="#jobs">Find Jobs</a>
          <a href="#analysis">Resume AI</a>
          <a href="#how">How It Works</a>
        </nav>
        <button className="navBtn" onClick={() => document.getElementById("upload").click()}>
          Analyze Resume
        </button>
      </header>

      <main>
        <section className="hero">
          <div className="heroCopy">
            <div className="eyebrow"><Sparkles size={15}/> AI-POWERED CAREER DISCOVERY</div>
            <h1>Find jobs that<br/><em>actually fit</em> your skills.</h1>
            <p>
              Upload your resume and HireSense analyzes your skills, role signals,
              and experience to rank the most relevant opportunities.
            </p>
            <div className="heroActions">
              <button className="primary" onClick={() => document.getElementById("upload").click()}>
                <UploadCloud size={18}/> Upload Resume
              </button>
              <a className="secondary" href="#jobs">Explore Jobs <ArrowRight size={17}/></a>
            </div>
            <div className="trust">
              <span><CheckCircle2 size={15}/> Explainable matching</span>
              <span><CheckCircle2 size={15}/> Skill-gap analysis</span>
              <span><CheckCircle2 size={15}/> Ranked recommendations</span>
            </div>
          </div>
          <div className="heroCard">
            <div className="scoreRing">
              <div><strong>{results[0]?.score?.toFixed(0) || "86"}%</strong><span>match</span></div>
            </div>
            <div className="miniJob">
              <span className="pill">TOP MATCH</span>
              <h3>{results[0]?.jobTitle || "Java Full Stack Developer"}</h3>
              <p>{results[0]?.company || "TechNova Systems"} · Bengaluru</p>
            </div>
            <div className="skillBars">
              <SkillBar label="Required skills" value={results[0]?.requiredCoverage || 84}/>
              <SkillBar label="Preferred skills" value={results[0]?.preferredCoverage || 70}/>
              <SkillBar label="Role similarity" value={results[0]?.roleSimilarity || 92}/>
            </div>
          </div>
        </section>

        <section id="analysis" className="analysis">
          <div className="sectionTitle">
            <div>
              <div className="eyebrow">RESUME INTELLIGENCE</div>
              <h2>Turn your resume into a job strategy.</h2>
            </div>
            {resume && <div className="analysisStatus"><CheckCircle2 size={17}/> Resume indexed</div>}
          </div>

          <input
            id="upload"
            type="file"
            accept=".pdf,.txt"
            hidden
            onChange={e => uploadResume(e.target.files?.[0])}
          />

          {!resume ? (
            <label htmlFor="upload" className="dropzone">
              <div className="uploadIcon"><UploadCloud size={28}/></div>
              <h3>{loading ? "Analyzing your resume..." : "Drop your resume here"}</h3>
              <p>PDF or TXT · Up to 5 MB</p>
              <span className="uploadBtn">Choose Resume</span>
            </label>
          ) : (
            <div className="resumePanel">
              <div className="resumeIdentity">
                <div className="fileIcon"><FileText/></div>
                <div><strong>{resume.fileName}</strong><span>Resume parsed successfully</span></div>
              </div>
              <div>
                <label>Detected skills</label>
                <div className="chips">{skills.map(s => <span key={s}>{s}</span>)}</div>
              </div>
              <label htmlFor="upload" className="outlineBtn">Upload another</label>
            </div>
          )}

          {error && <div className="error">{error}</div>}
        </section>

        <section id="jobs" className="jobs">
          <div className="sectionTitle">
            <div>
              <div className="eyebrow">OPPORTUNITIES</div>
              <h2>{results.length ? "Recommended for you" : "Explore open roles"}</h2>
            </div>
            <div className="searchBox"><Search size={17}/><input value={search} onChange={e => setSearch(e.target.value)} placeholder="Search roles, companies, locations"/></div>
          </div>

          {results.length > 0 && (
            <div className="matchGrid">
              {results.map((r, i) => (
                <article className={`jobCard ${i === 0 ? "top" : ""}`} key={r.jobId}>
                  {i === 0 && <div className="topLabel"><Sparkles size={14}/> Best match</div>}
                  <div className="jobTop">
                    <div className="companyLogo">{r.company.slice(0,1)}</div>
                    <button className={saved.has(r.jobId) ? "saved" : "save"} onClick={() => toggleSave(r.jobId)}>
                      <Bookmark size={18} fill={saved.has(r.jobId) ? "currentColor" : "none"}/>
                    </button>
                  </div>
                  <h3>{r.jobTitle}</h3>
                  <p className="company">{r.company}</p>
                  <div className="meta"><span><MapPin size={14}/> India</span><span><BriefcaseBusiness size={14}/> Full-time</span></div>
                  <div className="matchScore"><strong>{r.score.toFixed(0)}%</strong><span>match</span><div className="scoreLine"><i style={{width:`${r.score}%`}}/></div></div>
                  <div className="matched">
                    {r.matchedSkills.slice(0,5).map(s => <span key={s}><CheckCircle2 size={12}/>{s}</span>)}
                  </div>
                  <button className="details" onClick={() => setSelected(r)}>See match details <ArrowRight size={15}/></button>
                </article>
              ))}
            </div>
          )}

          {!results.length && (
            <div className="jobList">
              {visibleJobs.map(job => (
                <article className="jobRow" key={job.id}>
                  <div className="companyLogo">{job.company.slice(0,1)}</div>
                  <div className="jobMain">
                    <h3>{job.title}</h3><p>{job.company}</p>
                    <div className="meta"><span><MapPin size={14}/>{job.location}</span><span><BriefcaseBusiness size={14}/>{job.employmentType}</span></div>
                  </div>
                  <div className="jobSalary">{job.salaryRange}</div>
                  <button className="outlineBtn" onClick={() => alert("Upload a resume to calculate the match score.")}>Match my resume</button>
                </article>
              ))}
            </div>
          )}
        </section>

        <section id="how" className="how">
          <div className="eyebrow">UNDER THE HOOD</div>
          <h2>Matching you can explain in an interview.</h2>
          <p className="howLead">The system turns unstructured resume text into structured signals, then compares them against job requirements.</p>
          <div className="howGrid">
            <Step n="01" icon={<FileText/>} title="Parse" text="Extract text from PDF/TXT resumes using the backend document pipeline."/>
            <Step n="02" icon={<BrainCircuit/>} title="Understand" text="Normalize terms and identify technical skills and role keywords using NLP rules."/>
            <Step n="03" icon={<BarChart3/>} title="Score" text="Weight required skills, preferred skills and role similarity into one explainable score."/>
            <Step n="04" icon={<Send/>} title="Recommend" text="Rank jobs and expose matched skills, gaps and the reasoning behind each result."/>
          </div>
        </section>
      </main>

      {selected && (
        <div className="modalBackdrop" onClick={() => setSelected(null)}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <button className="modalClose" onClick={() => setSelected(null)}>×</button>
            <span className="pill">MATCH EXPLANATION</span>
            <h2>{selected.score.toFixed(0)}% match</h2>
            <p>{selected.explanation}</p>
            <h4>Matched skills</h4>
            <div className="chips">{selected.matchedSkills.map(s => <span key={s}>{s}</span>)}</div>
            <h4>Skill gaps</h4>
            {selected.missingRequiredSkills.length
              ? <div className="gaps">{selected.missingRequiredSkills.map(s => <span key={s}><XCircle size={14}/>{s}</span>)}</div>
              : <p className="good">No required skill gaps detected.</p>}
            <button className="primary wide" onClick={() => apply(selected.jobId)}><Send size={16}/> Apply</button>
          </div>
        </div>
      )}

      <footer>
        <div className="brand"><div className="brandIcon"><BrainCircuit size={20}/></div><strong>HireSense<span> AI</span></strong></div>
        <span>Java · Spring Boot · React.js · SQL · NLP · REST APIs</span>
      </footer>
    </div>
  );
}

function SkillBar({label, value}) {
  return <div className="bar"><div><span>{label}</span><b>{value}%</b></div><div className="track"><i style={{width:`${value}%`}}/></div></div>
}

function Step({n, icon, title, text}) {
  return <div className="step"><div className="stepNo">{n}</div><div className="stepIcon">{icon}</div><h3>{title}</h3><p>{text}</p></div>
}

export default App;
