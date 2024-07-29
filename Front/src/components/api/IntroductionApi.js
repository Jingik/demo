import axios from 'axios'
import axiosInstance from './AxiosInstance';

const baseUrl = "http://localhost:8080"

// 자소서 추가
export const createIntroduction = (userId, title, sections) => {
  return axiosInstance.post('/api/introduction' ,{
    userId, 
    title,
    sections
  })
  .then(response => response.data)
  .catch(error => {
    throw error
  })
}

// 자소서 수정
export const updateIntroduction = (introductionId, updateParam) => {
  return axiosInstance.put(`/api/introduction/${introductionId}`, updateParam)
  .then(response => response.data)
  .catch(error => {
    throw error;
  });
};

// 자소서 조회
export const getIntroduction = (introductionId) => {
  return axiosInstance.get(`/api/introduction/${introductionId}`)
  .then(response => response.data)
  .catch(error => {
    throw error;
  });
};


// 내 자소서 목록 조회 get
export const getMyIntroductions = (userId) => {
  return axiosInstance.get(`/api/introductions/${userId}`)
  .then(response => response.data)
  .catch(error => {
    throw error
  })
}

// 자소서 삭제
export const deleteIntroduction = (introductionId) => {
  return axiosInstance.delete(`/api/introduction/{id}`)
  .then(response => response.data)
  .catch(error=>{
    throw error
  })
}

// 소개글 섹션 삭제
export const deleteSection = (sectionId) => {
  return axiosInstance.delete(`/api/introduction/section/${sectionId}`)
  .then(response => response.data)
  .catch(error => {
    throw error;
  });
};