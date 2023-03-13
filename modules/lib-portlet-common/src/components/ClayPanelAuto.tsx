import ClayButton from "@clayui/button";
import ClayIcon from "@clayui/icon";
import ClayPanel from "@clayui/panel";
import { TInternalStateOnChange, useInternalState } from "@clayui/shared";
import classNames from "classnames";
import React from "react";
import { CSSTransition } from "react-transition-group";


interface IProps extends React.HTMLAttributes<HTMLDivElement> {

  /**
   * Flag to indicate that Panel is collapsable.
   */
  collapsable?: boolean;

  /**
   * Adds classes to the collapse element. Only when `collapsable` is true.
   */
  collapseClassNames?: string;

  /**
   * Flag to indicate the initial value of expanded.
   */
  defaultExpanded?: boolean;

  /**
   * Content to display in Panel Title.
   */
  displayTitle?: React.ReactNode;

  /**
   * Flag to indicate the visual variation of the Panel.
   */
  displayType?: "unstyled" | "secondary";

  /**
   * Determines if menu is expanded or not
   */
  expanded?: boolean;

  /**
   * Callback for when dropdown changes its active state
   */
  onExpandedChange?: TInternalStateOnChange<boolean>;

  /**
   * Flag to toggle collapse icon visibility when `collapsable` is true.
   */
  showCollapseIcon?: boolean;

  /**
   * Path to spritemap for clay icons
   */
  spritemap?: string;
}

const ClayPanelAuto: React.FunctionComponent<IProps> & {
  Body?: typeof ClayPanel.Body;
  Footer?: typeof ClayPanel.Footer;
  Group?: typeof ClayPanel.Group;
  Header?: typeof ClayPanel.Header;
  Title?: typeof ClayPanel.Title;
} = ({
       children,
       className,
       collapsable,
       collapseClassNames,
       defaultExpanded = false,
       displayTitle,
       displayType,
       expanded,
       onExpandedChange,
       showCollapseIcon = true,
       spritemap,
       ...otherProps
     }: IProps) => {
  const [internalExpanded, setInternalExpanded] = useInternalState({
    initialValue: defaultExpanded,
    onChange: onExpandedChange,
    value: expanded
  });

  return (
    <div
      {...otherProps}
      className={classNames("panel", className, {
        [`panel-${displayType}`]: displayType
      })}
      role="tablist"
    >
      {!collapsable && (
        <React.Fragment>
          {displayTitle &&
          (React.isValidElement(displayTitle) ? (
            displayTitle
          ) : (
            <ClayPanel.Header>
								<span className="panel-title">
									{displayTitle}
								</span>
            </ClayPanel.Header>
          ))}

          {children}
        </React.Fragment>
      )}

      {collapsable && (
        <React.Fragment>
          <ClayButton
            aria-expanded={internalExpanded}
            className={classNames(
              "panel-header panel-header-link",
              {
                "collapse-icon": showCollapseIcon,
                "collapse-icon-middle": showCollapseIcon,
                "collapsed": !internalExpanded,
                "show": internalExpanded
              }
            )}
            displayType="unstyled"
            onClick={() => setInternalExpanded(!internalExpanded)}
            role="tab"
          >
            {displayTitle &&
            (React.isValidElement(displayTitle) ? (
              displayTitle
            ) : (
              <span className="panel-title">
									{displayTitle}
								</span>
            ))}

            {showCollapseIcon && (
              <React.Fragment>
								<span className="collapse-icon-closed">
									<ClayIcon
                    spritemap={spritemap}
                    symbol="angle-right"
                  />
								</span>

                <span className="collapse-icon-open">
									<ClayIcon
                    spritemap={spritemap}
                    symbol="angle-down"
                  />
								</span>
              </React.Fragment>
            )}
          </ClayButton>

          <CSSTransition
            className={classNames(
              "panel-collapse",
              collapseClassNames,
              { collapse: !internalExpanded }
            )}
            classNames={{
              enter: "collapsing",
              enterActive: `show`,
              enterDone: "show",
              exit: `show`,
              exitActive: "collapsing"
            }}
            in={internalExpanded}
            onEnter={(element: HTMLElement) => {
              element.style.height = "0px";
            }}
            onEntering={(element: HTMLElement) => {
              element.style.height = "auto";
            }}
            onExit={(element) => {
              element.style.height = "auto";
            }}
            onExiting={(element) => {
              element.style.height = "0px";
            }}
            role="tabpanel"
            timeout={250}
          >
            <div>{children}</div>
          </CSSTransition>
        </React.Fragment>
      )}
    </div>
  );
};


export default ClayPanelAuto;
