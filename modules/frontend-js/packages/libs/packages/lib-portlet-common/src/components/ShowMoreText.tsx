import ClayButton from "@clayui/button";
import classNames from "classnames";
import React, { MouseEvent, PropsWithChildren, useCallback, useEffect, useMemo, useState } from "react";
import { WithTranslation, withTranslation } from "react-i18next";

import useLiferayParams from "../hooks/useLiferayParams";
import MiscUtil from "../utils/MiscUtil";


interface Props extends WithTranslation {
  buttonClassName?: string | undefined | (string | undefined)[];
  buttonWrapperClassName?: string | undefined | (string | undefined)[];
  children: string;
  className?: string | undefined | (string | undefined)[];
  ellipsis?: string;
  isTruncate?: boolean;
  maxCharacters?: number;
}

const MAX_CHARACTERS_DEFAULT = 300;

export const ShowMoreTextWithT: React.FC<PropsWithChildren<Props>> = ({
                                                                        buttonClassName,
                                                                        buttonWrapperClassName,
                                                                        children,
                                                                        className,
                                                                        ellipsis = "\u2026",
                                                                        isTruncate = false,
                                                                        maxCharacters = MAX_CHARACTERS_DEFAULT,
                                                                        t
                                                                      }) => {
  const [isShowAll, setShowAll] = useState(false);
  const [isScroll, setScroll] = useState(false);

  const [text, setText] = useState<string>("");

  const isShort = useMemo(() => children.length <= maxCharacters, [children, maxCharacters]);

  const buttonLabel = isShowAll ? t("txtShowLess") : t("txtShowMore");

  const liferayParams = useLiferayParams();
  const buttonWrapperId = useMemo(() => liferayParams.portletNamespace + "_showMoreText_" + MiscUtil.randomString(),
    [liferayParams.portletNamespace]);
  const buttonWrapperAttributes = {
    "aria-expanded": isShort ? undefined : isShowAll
  };

  const containerRef = useCallback((element: HTMLElement) => {
    if (element) {


      if (isScroll) {
        const elementTop = element.getBoundingClientRect().top + window.scrollY;
        const viewportHeight = window.innerHeight;
        const y = elementTop - ((viewportHeight) / 2);

        // eslint-disable-next-line no-console
        // console.log("ShowMoreText", "elementTop", elementTop, "viewportHeight", viewportHeight, "y", y);

        window.scrollTo(0, y);
        setScroll(false);
      }

    }
  }, [isScroll, setScroll]);

  useEffect(() => {
    if (isShowAll || isShort) {
      setText(children);
    } else {
      for (let i = maxCharacters; i >= 0; i--) {
        if (children.charAt(i) === " ") {
          setText(children.substring(0, i));
          break;
        }
      }
    }
  }, [buttonWrapperId, children, isShort, isShowAll, maxCharacters, setText]);


  const handleClick = useCallback((_event: MouseEvent<HTMLButtonElement>) => {
    if (isShowAll && !isShort) {
      setScroll(() => true);
    }
    setShowAll(() => !isShowAll);
  }, [isShowAll, isShort, setShowAll]);

  return (
    <span {...buttonWrapperAttributes} className={classNames(className, "arena-show-more-container")} id={buttonWrapperId} ref={containerRef}>
        {MiscUtil.isHtml(text) ? (<div dangerouslySetInnerHTML={MiscUtil.createMarkup(text)}/>)
            : (text)
        }

        {!isShort && (
          <span className={classNames(buttonWrapperClassName, "arena-ellipsis-button-wrapper")}>
            {!isShowAll && ellipsis}

            {!isTruncate && <ClayButton
              aria-controls={buttonWrapperId}
              className={classNames(buttonClassName, "arena-ellipsis-button")}
              displayType="link"
              onClick={handleClick}
              small
              type="button"
            >
              {buttonLabel}
            </ClayButton>}
          </span>
        )}
    </span>
  );
};

export default withTranslation()(ShowMoreTextWithT);
