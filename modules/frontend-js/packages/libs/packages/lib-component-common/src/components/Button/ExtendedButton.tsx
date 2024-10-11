import React from 'react';

export interface ButtonProps extends React.DetailedHTMLProps<React.ButtonHTMLAttributes<HTMLButtonElement>, HTMLButtonElement>{
  label: string;
}

const ExtendedButton = ({label, ...others}: ButtonProps) => {
  return <button {...others}>{label}</button>;
};

export default ExtendedButton;
