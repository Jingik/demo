import React, { useState } from 'react';
import './Portfolio.css';
import Sidebar from '../components/ui/Sidebar'

const Portfolio = () => {
  const [projects, setProjects] = useState([]);
  const [newProject, setNewProject] = useState({ title: '', description: '', link: '' });
  const [error, setError] = useState('');

  const addProject = () => {
    if (!newProject.title || !newProject.description || !newProject.link) {
      setError('제목, 설명 또는 링크를 입력해주세요.');
      return;
    }

    setProjects([...projects, newProject]);
    setNewProject({ title: '', description: '', link: '' });
    setError('');
  };

  const deleteProject = (index) => {
    const updatedProjects = projects.filter((_, i) => i !== index);
    setProjects(updatedProjects);
  };

  return (
    <>
    <div className="portfolio-container">
      <h1>포트폴리오</h1>
      
      <div className="form">
        <input 
          type="text" 
          placeholder="프로젝트 제목" 
          value={newProject.title}
          onChange={(e) => setNewProject({ ...newProject, title: e.target.value })}
        />
        <textarea 
          placeholder="프로젝트 설명" 
          value={newProject.description}
          onChange={(e) => setNewProject({ ...newProject, description: e.target.value })}
        />
        <input 
          type="text" 
          placeholder="프로젝트 링크" 
          value={newProject.link}
          onChange={(e) => setNewProject({ ...newProject, link: e.target.value })}
        />
        <button onClick={addProject}>추가</button>
        {error && <p className="error">{error}</p>}
      </div>
      
      <div className="projects">
        {projects.map((project, index) => (
          <div key={index} className="project">
            <h2>{project.title}</h2>
            <p>{project.description}</p>
            {project.link && <a href={project.link} target="_blank" rel="noopener noreferrer">프로젝트 링크</a>}
            <button className="delete-button" onClick={() => deleteProject(index)}>삭제</button>
          </div>
        ))}
      </div>
    </div>
    <Sidebar />
    </>
  );
};

export default Portfolio;
