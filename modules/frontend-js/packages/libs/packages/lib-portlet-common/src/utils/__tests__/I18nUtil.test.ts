import { AxCurrencyAmount } from "../../models/domain/ax/AxCurrencyAmount";
import I18nUtil from "../I18nUtil";
import LiferayUtil from "../LiferayUtil";

describe("I18nUtil", () => {
  beforeEach(() => {
    jest.spyOn(LiferayUtil, "getLocale").mockImplementation(() => "en_US");
  });

  test("date2String", () => {
    const dateAsString = I18nUtil.date2String(
      new Date(2018, 11, 21, 0, 0, 0)
    );
    expect(dateAsString).toEqual("12/21/2018");
  });

  test("string2Date", () => {
    const date = I18nUtil.string2Date("12/21/2018");
    expect(date).toEqual(new Date(2018, 11, 21, 12, 0, 0));
  });

  test("currencyAmount", () => {
    const currencyAmount = {
      amount: 10,
      currency: "USD"
    } as AxCurrencyAmount;
    const currencyAmountAsString =
      I18nUtil.currencyAmount2String(currencyAmount);
    expect(currencyAmountAsString).toEqual("USD 10");
  });

  test("date2IsoString", () => {
    const isoString = I18nUtil.date2IsoString(
      new Date(Date.UTC(2018, 11, 21, 12, 0, 0))
    );
    expect(I18nUtil.isoString2Date(isoString)).toEqual(I18nUtil.isoString2Date("2018-12-21T12:00:00.000Z"));
  });

  test("dateTime2IsoString", () => {
    const isoString = I18nUtil.dateTime2IsoString(
      new Date(Date.UTC(2018, 11, 21, 10, 15, 0))
    );
    expect(I18nUtil.isoString2Date(isoString)).toEqual(I18nUtil.isoString2Date("2018-12-21T10:15:00.000Z"));
  });

  test("isoString2Date", () => {
    const date = I18nUtil.isoString2Date("2018-12-21");
    expect(date).toEqual(new Date(2018, 11, 21, 0, 0, 0));
  });

  test("isoDateString2DateString", () => {
    const date = I18nUtil.isoDateString2DateString("2018-12-21T12:00:00.000Z");
    expect(date).toEqual("12/21/2018");
  });

  test("dateString2isoDateString", () => {
    const date = I18nUtil.dateString2isoDateString("12/21/2018");
    expect(date).toEqual("2018-12-21T12:00:00.000Z");
  });
});
