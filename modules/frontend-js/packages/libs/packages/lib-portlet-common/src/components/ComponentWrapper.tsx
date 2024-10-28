import React, { FC } from "react";

function ComponentWrapper<T>(ReactComponent: FC<T>) {
  function ComponentWrapper(props: T & JSX.IntrinsicAttributes) {
    return <ReactComponent {...props} />
  }

  return ComponentWrapper;
}

export default ComponentWrapper;
