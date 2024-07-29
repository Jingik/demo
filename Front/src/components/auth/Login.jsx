import { useState } from "react";
import { fetchLogin } from "../api/AuthApi";
import './Auth.css';
import '../ui/Sidebar';
import Sidebar from "../ui/Sidebar";

const Login = () => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async(event) => {
    event.preventDefault(); // 기본 폼 제출 동작 방지

    try {
      const response = await fetchLogin(userId, password);
      if (response.data.authenticated) {
        console.log('로그인 성공');
        // 메인 화면으로 리디렉션 또는 다른 처리
      } else {
        console.log('로그인 실패');
      }
    } catch (error) {
      console.log("로그인 중 에러 발생", error);
    }
  };

  // 소셜 로그인 버튼 클릭 핸들러
  const handleSocialLogin = (provider) => {
    // provider에 따라 해당 소셜 미디어의 로그인 처리 로직을 추가
    console.log(`소셜 로그인 (${provider}) 클릭`);
  };
  
  return (
    <>
      <Sidebar />
      <div className="form-container">
        <h1>로그인</h1>
        <form onSubmit={handleSubmit}>
          <div>
            <label>ID:</label>
            <input type="text" value={userId} onChange={(e) => setUserId(e.target.value)} />
          </div>
          <div>
            <label>Password:</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <button type="submit">로그인</button>
        </form>

        {/* 소셜 로그인 버튼 추가 */}
        <div className="social-login">
          <button onClick={() => handleSocialLogin('google')}>구글 계정으로 로그인</button>
        </div>
      </div>
    </>
  );
}

export default Login;
