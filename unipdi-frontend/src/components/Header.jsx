import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import Button from './Button';
import s from './Header.module.css';

export default function Header(){
  const { pathname } = useLocation();
  return (
    <div className={s.wrap}>
      <div className={s.header}>
        <div className={s.brand}>UniPDI</div>
        <div className={s.crumb}>{pathname === '/' ? 'Home' : pathname}</div>
        <div className={s.spacer} />
        <nav className={s.nav}>
          <Link to="/" className="link"><Button variant="ghost" size="small">Home</Button></Link>
          <Link to="/pessoas" className="link"><Button variant="ghost" size="small">Pessoas</Button></Link>
        </nav>
      </div>
    </div>
  );
}
