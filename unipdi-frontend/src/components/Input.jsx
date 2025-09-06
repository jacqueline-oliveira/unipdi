import React from 'react';
import s from './Input.module.css';

export default function Input({ id, label, ...props }){
  return (
    <div className={s.field}>
      {label && <label className={s.label} htmlFor={id}>{label}</label>}
      <input id={id} className={s.input} {...props} />
    </div>
  );
}

export function Row({ children }){ return <div className={s.row}>{children}</div>; }
