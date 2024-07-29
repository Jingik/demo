import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Auth.css'
import { fetchSignup } from "../api/AuthApi"
import Sidebar from "../ui/Sidebar"

const Signup = () => {
  const [id, setId] = useState('');  
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate(); // useNavigate 훅 사용

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }

    try {
      await fetchSignup(id, username, email, password, confirmPassword);
      // 회원가입 성공 처리
      console.log('회원가입 성공');
      navigate('/'); // 메인 화면으로 리디렉션
    } catch (error) {
      // 회원가입 실패 처리
      console.error('회원가입 실패:', error);
    }
  };

  return (
    <>
      <Sidebar />
      <div className="form-container">
        <h1>회원가입</h1>
        <form onSubmit={handleSubmit}>
          <div>
            <label>아이디:</label>
            <input type="text" value={id} onChange={(e) => setId(e.target.value)} />
          </div>
          <div>
            <label>이름:</label>
            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
          </div>
          <div>
            <label>Email:</label>
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div>
            <label>Password:</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <div>
            <label>Confirm Password:</label>
            <input type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
          </div>
          <button type="submit">회원가입</button>
        </form>
      </div>
    </>
  );
}

export default Signup;
