import {ClayInput} from '@clayui/form';
import React from 'react';

const ExtendedInput = ({label, ...others}: any) => {
  return <React.Fragment><label>{label}</label><ClayInput  {...others} /></React.Fragment>;
};

export default ExtendedInput;
