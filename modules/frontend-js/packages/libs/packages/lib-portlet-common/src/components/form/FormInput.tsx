/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import Select from '@clayui/form/lib/Select';
import ClayIcon from '@clayui/icon';
import React from "react";
import { useTranslation } from 'react-i18next';

const getFieldFeedbackId = (fieldId: string): string => {
  return fieldId + '_feedback';
};

const ErrorFeedback = ({
                         error,
                         fieldId,
                       }: {
  error: string | string[] | undefined;
  fieldId: string;
}) => {
  const errors = error ? (Array.isArray(error) ? error : [error]) : [];

  return (
    <>
      {!!errors.length && (
        <ClayForm.FeedbackGroup id={getFieldFeedbackId(fieldId)}>
          {errors.map((error, index) => {
            return (
              <ClayForm.FeedbackItem
                key={index}
                role="alert"
              >
                {error}
              </ClayForm.FeedbackItem>
            );
          })}
        </ClayForm.FeedbackGroup>
      )}
    </>
  );
};

const HelpMessage = ({ message }: { message: string }) => {
  return (
    <span
      className="inline-item-after lfr-portal-tooltip tooltip-icon"
      title={message}
    >
			<ClayIcon symbol="question-circle-full" />
		</span>
  );
};

const RequiredMark: React.FC = () => {
  const { t } = useTranslation();

  return (
    <>
			<span className="inline-item-after reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>

      <span className="hide-accessible">{t('txtRequired')}</span>
    </>
  );
};

const SelectOptions: React.FC<{options: React.ComponentProps<typeof Select.Option>[]}> = ({options}: {options: React.ComponentProps<typeof Select.Option>[]}) => {
  return (
    <>
      {options.map((option, i) => {
        return (
          <option key={i} value={option.value}>
            {option.label}
          </option>
        );
      })}
    </>
  )
}

export { getFieldFeedbackId, ErrorFeedback, HelpMessage, RequiredMark, SelectOptions };
