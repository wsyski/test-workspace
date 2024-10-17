import React from 'react';

class FormatUtil {
	static formatFieldValueAsString(value) {
		if (Array.isArray(value)) {
			return <>{value.join(', ')}</>;
		} else {
			return <>{value}</>;
		}
	}

	static formatFieldValueAsUrl(value) {
		if (value) {
			if (Array.isArray(value)) {
				return (
					<>
						{value.map((url, index) => {
							return (
								<React.Fragment key={index}>
									<a
										className="field-value"
										href={url}
										title={url}
									>
										{url}
									</a>
								</React.Fragment>
							);
						})}
					</>
				);
			} else {
				return (
					<>
						<a className="field-value" href={value} title={value}>
							{value}
						</a>
					</>
				);
			}
		} else {
			return <></>;
		}
	}
}

export default FormatUtil;
