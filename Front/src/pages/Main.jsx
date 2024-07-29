import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Main.css'
import Sidebar from '../components/ui/Sidebar'
import Calendar from '../components/ui/Calendar';

const Main = () => {
  const navigate = useNavigate();

  const handleLoginClick = ( ) => {
    navigate('/login')
  }

  return (
    <div className="main-container">
      <h1>메인 페이지</h1>
      <div className="button-container">
        {/* <button onClick={handleLoginClick}>
          로그인
        </button>
        <button onClick={() => navigate('/signup')}>
          회원가입
        </button> */}
      </div>
      <Sidebar />
      <Calendar />
    </div>
  );
};

export default Main;
