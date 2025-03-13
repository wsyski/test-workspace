import ClayButton from "@clayui/button";
import classNames from "classnames";
import React, {MouseEvent, PropsWithChildren, useCallback, useMemo, useState} from "react";
import { WithTranslation, withTranslation } from "react-i18next";

import useLiferayParams from "../hooks/useLiferayParams";
import MiscUtil from "../utils/MiscUtil";

interface Props extends WithTranslation {
  children: React.ReactNode;
  className?: string | undefined | (string | undefined)[];
  ellipsis?: string;
  maxElements?: number;
  buttonClassName?: string | undefined | (string | undefined)[];
  buttonWrapperClassName?: string | undefined | (string | undefined)[];
}

const MAX_ELEMENTS_DEFAULT = 10;

export const ShowMoreListWithT: React.FC<PropsWithChildren<Props>> = ({
                                                                        buttonClassName,
                                                                        buttonWrapperClassName,
                                                                        children,
                                                                        className,
                                                                        ellipsis = "\u2026",
                                                                        maxElements = MAX_ELEMENTS_DEFAULT,
                                                                        t
                                                                      }) => {


  const [isShowAll, setShowAll] = useState(false);

  const childrenAsArray = React.Children.toArray(children);
  const isShort = childrenAsArray.length <= maxElements;

  const buttonLabel = isShowAll ? t("txtShowLess") : t("txtShowMore");
  const liferayParams = useLiferayParams();
  const buttonWrapperId = useMemo(() => liferayParams.portletNamespace + "_showMoreList_" + MiscUtil.randomString(),
    [liferayParams.portletNamespace]);
  const buttonWrapperAttributes = {
    "aria-expanded": isShort ? undefined : isShowAll
  };
  const [isScroll, setScroll] = useState(false);

  const containerRef = useCallback((element: HTMLElement) => {
    if (element) {
      if (isScroll) {
        const elementTop = element.getBoundingClientRect().top + window.scrollY;
        const viewportHeight = window.innerHeight;
        const y = elementTop - ((viewportHeight) / 2);

        // eslint-disable-next-line no-console
        // console.log("ShowMoreList", "elementTop", elementTop, "viewportHeight", viewportHeight, "y", y);

        window.scrollTo(0, y);
        setScroll(false);
      }

    }
  }, [isScroll, setScroll]);

  const handleClick = useCallback((_event: MouseEvent<HTMLButtonElement>) => {
    if (isShowAll && !isShort) {
      setScroll(() => true);
    }
    setShowAll(() => !isShowAll);
  }, [isShowAll, isShort, setShowAll]);

  return (
    <span {...buttonWrapperAttributes} className={classNames(className, "arena-show-more-container")} id={buttonWrapperId} ref={containerRef}>
            {childrenAsArray.map((child, index) => {

              return (
                <React.Fragment key={index}>
                  {(isShowAll || index < maxElements) && child}
                </React.Fragment>
              );
            })}

      {!isShort && (
        <span className={classNames(buttonWrapperClassName, "arena-ellipsis-button-wrapper")} >
          {!isShowAll && ellipsis}

          <ClayButton
            aria-controls={buttonWrapperId}
            className={classNames(buttonClassName, "arena-ellipsis-button")}
            displayType="link"
            onClick={handleClick}
            small
            type="button"
          >
			{buttonLabel}
          </ClayButton>
        </span>
      )}
      </span>
  );
};

export default withTranslation()(ShowMoreListWithT);
