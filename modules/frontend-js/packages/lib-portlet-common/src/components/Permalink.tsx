import { ClayButtonWithIcon } from "@clayui/button";
import React, { HTMLAttributes, ReactElement, useCallback, useEffect, useRef, useState } from "react";

import LiferayUtil from '../utils/LiferayUtil';

interface Props {
  labelCopy?: string;
  labelCopied?: string;
  renderAnchor: (anchorRef: React.RefObject<HTMLAnchorElement>) => ReactElement<HTMLAttributes<HTMLAnchorElement>>;
}

const CHANGE_TIMEOUT = 5000;

const Permalink: React.FC<Props> = ({labelCopied, labelCopy, renderAnchor }: Props) => {
  const anchorRef = useRef<HTMLAnchorElement>(null);
  const anchor = renderAnchor(anchorRef);
  const [copied, setCopied] = useState(false);
  useEffect(() => {
    const timeoutHandle = copied ? setTimeout(() => setCopied(false), CHANGE_TIMEOUT) : undefined;

    return () => {
      timeoutHandle && clearTimeout(timeoutHandle);
    };
  }, [copied, setCopied]);

  const onClickCopy = useCallback(() => {
    setCopied(true);
    const url = anchorRef?.current?.href;
    if (url) {
      (async () => {
        await navigator.clipboard.writeText(url);
      })();
    }
  }, [anchorRef]);

  const icon = copied ? "check" : "copy";
  const label = copied ? labelCopied : labelCopy;

  return (
    <React.Fragment>
      {anchor}

      <ClayButtonWithIcon
        aria-label={label ? label : ""}
        displayType="unstyled"
        onClick={onClickCopy}
        small={true}
        spritemap={LiferayUtil.getClaySpritemap()}
        symbol={icon}
        title={label}
      />
    </React.Fragment>
  );
};

export default Permalink;
