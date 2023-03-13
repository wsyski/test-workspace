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
    <React.Fragment>
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
    </React.Fragment>
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
    <React.Fragment>
			<span className="inline-item-after reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>

      <span className="hide-accessible">{t('txtRequired')}</span>
    </React.Fragment>
  );
};

const SelectOptions: React.FC<{options: React.ComponentProps<typeof Select.Option>[]}> = ({options}: {options: React.ComponentProps<typeof Select.Option>[]}) => {
  return (
    <React.Fragment>
      {options.map((option, i) => {
        return (
          <option key={i} value={option.value}>
            {option.label}
          </option>
        );
      })}
    </React.Fragment>
  )
}

export { getFieldFeedbackId, ErrorFeedback, HelpMessage, RequiredMark, SelectOptions };
