import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Home from './pages/Home';
import PessoasPage from './pages/PessoaPage';
import PdiPage from './pages/PdiPage';
import CurriculoPage from './pages/CurriculoPage'
import './styles/global.css';
import './styles/reset.css';

export default function App(){
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/pessoas" element={<PessoasPage />} />
      <Route path="/pessoas/:matricula" element={<PdiPage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
      <Route path="/curriculo/:matricula" element={<CurriculoPage />} />
    </Routes>
  );
}
