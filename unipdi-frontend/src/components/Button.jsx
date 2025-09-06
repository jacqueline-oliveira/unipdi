import React from 'react';
import s from './Button.module.css';

export default function Button({
  children, onClick, type='button',
  variant='solid', size='normal', block=false
}) {
  const classes = [s.btn];
  if (variant==='ghost') classes.push(s.ghost);
  if (variant==='danger') classes.push(s.danger);
  if (size==='small') classes.push(s.small);
  if (block) classes.push(s.block);

  return <button type={type} className={classes.join(' ')} onClick={onClick}>{children}</button>;
}
