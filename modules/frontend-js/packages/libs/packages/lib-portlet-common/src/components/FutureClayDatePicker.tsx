import React from "react";

import { AxChronoUnit } from "../models/domain/ax/AxChronoUnit";
import I18nUtil from "../utils/I18nUtil";
import LiferayUtil from "../utils/LiferayUtil";
import EnabledDatesClayDatePicker from "./clay-date-picker/EnabledDatesClayDatePicker";

type IProps = React.ComponentProps<typeof EnabledDatesClayDatePicker> & {
  startDate?: Date;
};

const FutureClayDatePicker = React.forwardRef<
  HTMLInputElement,
  IProps
>(
  (
    {
      startDate = new Date(),
      value,
      ...rest
    }: IProps,
    ref
  ) => {
    const localizationParams = I18nUtil.getLocalizationParams();
    const now = new Date();
    let initValue;
    try {
      initValue = I18nUtil.string2Date(value) || now;
    } catch (ex) {
      initValue = now;
    }
    const enabledDays = React.useCallback((date: Date) => date >= startDate,
      [startDate]
    );

    const startYear = now.getFullYear();
    const endYear = startYear + 10;

    return (
      <EnabledDatesClayDatePicker
        dateFormat={localizationParams.dateFormat[AxChronoUnit.DAYS]}
        enabledDays={enabledDays}
        firstDayOfWeek={localizationParams.firstDayOfWeek}
        initialMonth={initValue}
        inputName="datePicker"
        months={localizationParams.months}
        placeholder={localizationParams.dateFormat[AxChronoUnit.DAYS]}
        ref={ref}
        spritemap={LiferayUtil.getClaySpritemap()}
        value={value}
        weekdaysShort={localizationParams.weekdaysShort}
        years={{
          end: endYear,
          start: startYear
        }}
        {...rest}
      />
    );
  });

export default FutureClayDatePicker;
