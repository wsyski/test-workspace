import React from "react";
import LiferayUtil from "./LiferayUtil";
import useLazy from "./hooks/useLazy";

function PortalLazyCalendar() {
        const Component = useLazy();
        const packageName = 'react-lazy-load-portlet@1.0.0';
        const props = {};
        return (
            <Component
                module={`${packageName}/Calendar`}
                props={{...props}}
            />
        )
    }

const LazyCalendar =  LiferayUtil.isPortal() ? PortalLazyCalendar: React.lazy(() => import("./Calendar"));

export default LazyCalendar;
