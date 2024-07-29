import { useState } from 'react'
import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Main from './pages/Main'
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import Introduction from './pages/Introduction';
import Notifications from './pages/Notifications'
import EducationAndExperience from './pages/EducationAndExperience';
import Portfolio from './pages/Portfolio'

function App() {

  return (
    <Routes>
      <Route>
        <Route path="/" element={<Main />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/introduction" element={<Introduction />} />
        <Route path="/notifications" element={<Notifications />} />
        <Route path="/education" element={<EducationAndExperience />} />
        <Route path="/portfolio" element={<Portfolio />} />
      </Route>
    </Routes>
  )
}

export default App
