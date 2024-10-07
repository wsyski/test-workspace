import React from 'react';

const SimpleInput = (props) => {
    const {
        id,
        label,
        name,
        value,
        ...restProps
    } = props;

    const inputId = id || name;

    return (
        <div>
            {label && <label htmlFor={inputId}>{label}</label>}

            <input
                {...restProps}
                id={inputId}
                name={name}
                value={value}
            />
        </div>
    );
}

export default SimpleInput;
