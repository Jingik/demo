import React, { useState, useEffect } from 'react';
import './Introduction.css';
import Sidebar from '../components/ui/Sidebar'
import {
  createIntroduction,
  updateIntroduction,
  getIntroduction,
  getMyIntroductions,
  deleteIntroduction,
  deleteSection
} from '../components/api/IntroductionApi';


const Introduction = () => {
  const [resumes, setResumes] = useState([{ id: 1, name: "자소서 1", questions: [] }]);
  const [newQuestion, setNewQuestion] = useState("");
  const [currentResumeId, setCurrentResumeId] = useState(1);
  const [isAddingResume, setIsAddingResume] = useState(false);
  const [newResumeName, setNewResumeName] = useState("");
  const currentUserId = 1;

  // 백, 프론트 동기화 (사용자 정보 마운트할때 가져오기)
  useEffect(() => {
    getMyIntroductions(currentUserId).then(data => {
      setResumes(data);
      if (data.length > 0) {
        setCurrentResumeId(data[0].id);
      }
    }).catch(error => {
      console.error('Failed to fetch resumes:', error);
    });
  }, [currentUserId]);


  // 문항 추가 (프론트: question , 백 : session)
  const addQuestion = async () => {
    if (newQuestion.trim() !== "") {
      const updatedResumes = resumes.find(r => r.id === currentResumeId)
      const updatedQuestions = [
        ...updatedResumes.questions,
        {question:newQuestion, answer:"", isEditing:false, isOpen:false}
      ]
      try{
        await updateIntroduction(currentResumeId, {questions:updatedQuestions})
        setResumes(resumes.map(r => 
          r.id === currentResumeId 
            ? { ...r, questions: updatedQuestions } :r
        ));
        setNewQuestion("");
      } catch (error) {
        console.log('문항 추가 실패',error)
      }
     
    }
  };

  // 문항 수정
  const updateQuestion = async (resumeId, index, updates) => {
    const updatedResume = resumes.find(r => r.id === resumeId)
    const updatedQuestions = updatedResume.questions.map((q, i) => 
      i === index ? { ...q, ...updates } : q
    )
    try {
      await updateIntroduction(resumeId, { questions: updatedQuestions })
      setResumes(resumes.map(r =>
        r.id === resumeId ? { ...r, questions: updatedQuestions } : r
      ));
    } catch (error) {
      console.error('문항 수정 실패', error);
    }
  };

  // 자소서 추가 함수
  const addResume = async () => {
    if (newResumeName.trim() !== "") {
      try {
        const newResume = await createIntroduction(currentUserId, newResumeName, []);
        // Use the ID from the backend response
        setResumes([...resumes, { id: newResume.id, name: newResume.title, questions: newResume.sections }]);
        setCurrentResumeId(newResume.id);
        setNewResumeName("");
        setIsAddingResume(false);
      } catch (error) {
        console.error('자소서 추가 실패', error);
      }
    }
  };


  // 자소서 삭제
  const deleteResume = async (resumeId) => {
    try {
      await deleteIntroduction(resumeId);
      const updatedResumes = resumes.filter(r => r.id !== resumeId);
      setResumes(updatedResumes);
      if (resumeId === currentResumeId && updatedResumes.length > 0) {
        setCurrentResumeId(updatedResumes[0].id);
      } else if (updatedResumes.length === 0) {
        setCurrentResumeId(null);
      }
    } catch (error) {
      console.error('자소서 삭제 실패', error);
    }
  };

  return (
    <>
    <div className="resume-container">
      <h1>자기소개서</h1>
      <div className="add-resume-section">
        {isAddingResume && (
          <div className="popup">
            <div className="popup-inner">
              <h2>새 자소서 추가</h2>
              <input
                type="text"
                value={newResumeName}
                onChange={(e) => setNewResumeName(e.target.value)}
                placeholder="자소서 이름을 입력하세요"
              />
              <div className="popup-buttons">
                <button onClick={addResume}>추가</button>
                <button onClick={() => setIsAddingResume(false)}>취소</button>
              </div>
            </div>
          </div>
        )}
        <button className="add-resume-button" onClick={() => setIsAddingResume(true)}>새 자소서 추가</button>
      </div>
      <div className="resume-tabs">
        {resumes.map(resume => (
          <div key={resume.id} className="resume-tab-container">
            <button 
              onClick={() => setCurrentResumeId(resume.id)}
              className={`resume-tab ${currentResumeId === resume.id ? 'active' : ''}`}
            >
              {resume.name}
            </button>
            {resumes.length > 1 && (
              <button onClick={() => deleteResume(resume.id)} className="delete-resume-button">삭제</button>
            )}
          </div>
        ))}
      </div>
      {currentResumeId && (
        <>
          <div className="add-question">
            <input
              type="text"
              value={newQuestion}
              onChange={(e) => setNewQuestion(e.target.value)}
              placeholder="새 문항을 입력하세요"
            />
            <button onClick={addQuestion}>문항 추가</button>
          </div>
          <div className="questions-list">
            {resumes.find(r => r.id === currentResumeId).questions.map((q, index) => (
              <div key={index} className="question-item">
                <div className="question-header" onClick={() => updateQuestion(currentResumeId, index, { isOpen: !q.isOpen })}>
                  <h3>{q.question}</h3>
                  <button>{q.isOpen ? "접기" : "열기"}</button>
                </div>
                {q.isOpen && (
                  <div className="answer-section">
                    {q.isEditing ? (
                      <>
                        <textarea
                          value={q.answer}
                          onChange={(e) => updateQuestion(currentResumeId, index, { answer: e.target.value })}
                          placeholder="답변을 작성하세요"
                        />
                        <div className="answer-buttons">
                          <button onClick={() => updateQuestion(currentResumeId, index, { isEditing: false })}>저장</button>
                          <button onClick={() => updateQuestion(currentResumeId, index, { isEditing: false })}>취소</button>
                        </div>
                      </>
                    ) : (
                      <>
                        <p>{q.answer}</p>
                        <button onClick={() => updateQuestion(currentResumeId, index, { isEditing: true })} className="edit-button">수정</button>
                      </>
                    )}
                  </div>
                )}
              </div>
            ))}
          </div>
        </>
      )}
    </div>
    <Sidebar />
    </>
  );
};

export default Introduction;
