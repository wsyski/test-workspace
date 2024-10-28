import React, { AllHTMLAttributes } from "react";

import { AxExternalUrl } from "../models/domain/ax/AxExternalUrl";
import Permalink from "./Permalink";

interface Props extends AllHTMLAttributes<HTMLAnchorElement> {
  labelCopy?: string;
  labelCopied?: string;
  externalUrl: AxExternalUrl;
}

const UrlPermalink: React.FC<Props> = ({ externalUrl, labelCopied,  labelCopy, ...otherProps }: Props) => {

  return (
    <React.Fragment>
      <Permalink labelCopied={labelCopied} labelCopy={labelCopy} renderAnchor={(anchorRef) => (
        <a
          href={externalUrl.url}
          ref={anchorRef}
          title={externalUrl.title ? externalUrl.title : externalUrl.url}
          {...otherProps}
        >
          {externalUrl.linkText ? externalUrl.linkText : externalUrl.url}
        </a>
      )}
      />
    </React.Fragment>
  );
};

export default UrlPermalink;
