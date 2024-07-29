import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <ul>
        <li>
          <Link to="/">메인</Link>
        </li>
        <li>
          <Link to="/login">로그인</Link>
        </li>
        <li>
          <Link to="/signup">회원가입</Link>
        </li>
      </ul>
      <ul>
        <li>
          <Link to="/notifications">알림</Link>
        </li>
      </ul>
      <ul>
        <li>
          <span>스펙 관리</span>
          <ul>
            <li>
              <Link to="/introduction">자기소개서</Link>
            </li>
            <li>
              <Link to="/education">학력 / 경력</Link>
            </li>
            <li>
              <Link to="/portfolio">포트폴리오</Link>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  )
}

export default Sidebar;
