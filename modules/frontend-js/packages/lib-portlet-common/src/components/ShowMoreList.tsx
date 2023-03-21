import ClayButton from '@clayui/button';
import classNames from "classnames";
import React, { PropsWithChildren, useState } from "react";
import { WithTranslation, withTranslation } from "react-i18next";

import MiscUtil from "../utils/MiscUtil";

interface Props extends WithTranslation {
  children: React.ReactNode;
  ellipsis?: string;
  maxElements?: number;
  buttonClassName?: string | undefined | (string | undefined)[];
  buttonWrapperClassName?: string | undefined | (string | undefined)[]
}

const MAX_ELEMENTS_DEFAULT = 10;

export const ShowMoreListWithT: React.FC<PropsWithChildren<Props>> = ({
                                                                        buttonClassName,
                                                                        buttonWrapperClassName,
                                                                        children,
                                                                        ellipsis = '\u2026',
                                                                        maxElements = MAX_ELEMENTS_DEFAULT,
                                                                        t
                                                                      }) => {


  const [isShowAll, setShowAll] = useState(false);

  const childrenAsArray = React.Children.toArray(children);
  const isShort = childrenAsArray.length <= maxElements;

  const handleClick = () => {
    setShowAll((v) => !v);
  };

  const buttonLabel = isShowAll ? t("txtShowLess") : t("txtShowMore");

  const buttonWrapperId = 'id_' + MiscUtil.randomString();
  const buttonWrapperAttributes = {
    "aria-expanded": isShort ? undefined : isShowAll
  };

  return (
    <React.Fragment>
      {childrenAsArray.map((child, index) => {

        return (
          <React.Fragment key={index}>
            {(isShowAll || index < maxElements) && child}
          </React.Fragment>
        );
      })}

      {!isShort && (
        <span
          className={classNames(buttonWrapperClassName, "arena-ellipsis-button-wrapper")}
          id={buttonWrapperId}
          {...buttonWrapperAttributes}
        >
						{!isShowAll && ellipsis}

          <ClayButton
            aria-controls={buttonWrapperId}
            className={classNames(buttonClassName, 'arena-ellipsis-button')}
            displayType="unstyled"
            onClick={handleClick}
            type="button"
          >
						{buttonLabel}
					</ClayButton>
				</span>
      )}
    </React.Fragment>
  );
};

export default withTranslation()(ShowMoreListWithT);
