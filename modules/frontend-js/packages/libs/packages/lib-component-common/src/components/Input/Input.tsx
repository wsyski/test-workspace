import {ClayInput} from '@clayui/form';
import React from 'react';

import "./Input.scss";


const Input: React.FC<typeof ClayInput> = (props: typeof ClayInput) => {
  return <ClayInput type="text" {...props} />;
};

export default Input;
