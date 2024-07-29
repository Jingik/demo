import axios from 'axios'
import axiosInstance from './AxiosInstance';

// 로그인
export const fetchLogin = (userId, password) => {
  return axiosInstance.post('/user/login', {
    userId,
    password
  }).then(response => {
    return response;
  }).catch(error => {
    throw error; 
  });
};


// 회원가입 
export const fetchSignup = (id, username, email, password, confirmPassword) => {
  return axiosInstance.post('/user/signup', {
    id,
    username, 
    email, 
    password, 
    confirmPassword
  }).then(response => {
    return response;
  }).catch(error => {
    throw error; 
  });
};

