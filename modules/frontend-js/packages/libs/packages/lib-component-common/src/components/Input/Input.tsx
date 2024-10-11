import React from "react";
import "./Input.scss";
import {ClayInput} from '@clayui/form';


const InputText:React.FC<typeof ClayInput> = (props: typeof ClayInput) => {
  return <ClayInput type="text" {...props} />;
};

export default InputText;
