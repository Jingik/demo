import React, { useState,useEffect } from 'react';
import './EducationAndExperience.css';
import Sidebar from '../components/ui/Sidebar'
import {
  createEmployment,
  updateEmployment,
  getEmployment,
  deleteEmployment,
  createEducation,
  updateEducation,
  getEducation,
  deleteEducation
} from '../components/api/EducationAndExperience';

const EducationAndExperience = () => {
  

  const [education, setEducation] = useState([]);
  const [experience, setExperience] = useState([]);
  
  const [newEducation, setNewEducation] = useState({ school: '', degree: '', major:'', startDate: '', graduateDate:'' });
  const [newExperience, setNewExperience] = useState({ company: '', title: '', years: '' });
  
  // 마운트될때 학력 정보 가져옴
  useEffect(() => {
    const fetchEducation = async () => {
      try {
        const educationData = await getEducation();
        setEducation(educationData);
      } catch (error) {
        console.error('학력 정보를 가져오는 데 실패했습니다:', error);
      }
    };

    fetchEducation();
  }, []);

  // 학력 추가
  const addEducation = async () => {
    try {
      if (newEducation.school.trim() !== "" && newEducation.degree.trim() !== "") {
        const createdEducation = await createEducation(newEducation.school, newEducation.degree, newEducation.major, newEducation.startDate, newEducation.graduateDate);
        setEducation([...education, createdEducation]);
        setNewEducation({ school: '', degree: '', major:'', startDate: '', graduateDate:'' });
      } else {
        console.error('학교 이름과 학위는 필수 입력 사항입니다.');
      }
    } catch (error) {
      console.error('학력 추가 실패:', error);
    }
  };

  const addExperience = () => {
    setExperience([...experience, newExperience]);
    setNewExperience({ company: '', title: '', years: '' });
  };

  return (
    <>
    <div className="education-experience-container">
      <h1>학력 및 경력</h1>
      
      <div className="section">
        <h2>학력</h2>
        <input 
          type="text" 
          placeholder="학교 이름" 
          value={newEducation.school}
          onChange={(e) => setNewEducation({ ...newEducation, school: e.target.value })}
        />
        <input 
          type="text" 
          placeholder="학위" 
          value={newEducation.degree}
          onChange={(e) => setNewEducation({ ...newEducation, degree: e.target.value })}
        />
        <input 
          type="text" 
          placeholder="학과" 
          value={newEducation.major}
          onChange={(e) => setNewEducation({ ...newEducation, major: e.target.value })}
        />
        <input 
          type="date" 
          placeholder="입학 연도" 
          value={newEducation.startDate}
          onChange={(e) => setNewEducation({ ...newEducation, startDate: e.target.value })}
        />
        <input 
          type="date" 
          placeholder="졸업 연도" 
          value={newEducation.graduateDate}
          onChange={(e) => setNewEducation({ ...newEducation, graduateDate: e.target.value })}
        />
        <button className="styled-button" onClick={addEducation}>추가</button>

        <ul>
          {education.map((edu, index) => (
            <li key={index}>{edu.school} - {edu.degree} ({edu.year})</li>
          ))}
        </ul>
      </div>

      <div className="section">
        <h2>경력</h2>
        <input 
          type="text" 
          placeholder="회사 이름" 
          value={newExperience.company}
          onChange={(e) => setNewExperience({ ...newExperience, company: e.target.value })}
        />
        <input 
          type="text" 
          placeholder="직책" 
          value={newExperience.title}
          onChange={(e) => setNewExperience({ ...newExperience, title: e.target.value })}
        />
        <input 
          type="text" 
          placeholder="근무 기간" 
          value={newExperience.years}
          onChange={(e) => setNewExperience({ ...newExperience, years: e.target.value })}
        />
        <button className="styled-button" onClick={addExperience}>추가</button>
        <ul>
          {experience.map((exp, index) => (
            <li key={index}>
              {exp.school} - {exp.degree} in {exp.major} ({exp.startDate} - {exp.graduateDate})
            </li>
          ))}
        </ul>
      </div>
    </div>
    <Sidebar />
    </>
  );
};

export default EducationAndExperience;
