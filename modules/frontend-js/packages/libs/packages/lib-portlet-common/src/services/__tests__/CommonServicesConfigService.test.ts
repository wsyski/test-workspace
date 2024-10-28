import { TestUtil } from "@arena/lib-portlet-test";
import { cleanup } from "@testing-library/react";
import Axios from "axios";

import { CommonServicesConfig } from "../../index";
import LiferayUtil from "../../utils/LiferayUtil";
import CommonServicesConfigService from "../CommonServicesConfigService";
import commonServicesConfig from "../__mocks__/resources/commonServicesConfig.json";

jest.mock("axios");
const axiosMock = Axios as jest.Mocked<typeof Axios>;

describe("CommonServicesConfigService", () => {
  beforeEach(() => {
    jest.spyOn(LiferayUtil, "getScopeGroupId").mockImplementation(
      () => 39847
    );
    jest.spyOn(LiferayUtil, "getPortalURL").mockImplementation(
      () => "http://localhost:6080"
    );
    jest.spyOn(LiferayUtil, "getPathThemeImages").mockImplementation(
      () => "/theme/images"
    );
    jest.spyOn(LiferayUtil, "getLocale").mockImplementation(() => "en_US");
  });

  afterEach(() => {
    jest.clearAllMocks();
    cleanup();
  });

  test("getConfig", async () => {
    axiosMock.get.mockResolvedValue(
      TestUtil.getAxiosResponse(commonServicesConfig)
    );
    const config: CommonServicesConfig =
      await CommonServicesConfigService.getConfig();
    expect(config.federatedSearchCustomerAlias).toBe(
      "test"
    );

    expect(config.openingHoursApiEndpoint).toBe(
      "https://test.axiell.io/api/openinghours/latest"
    );
  });

  test("getCoverUrl", async () => {
    axiosMock.get.mockResolvedValue(
      TestUtil.getAxiosResponse(commonServicesConfig)
    );
    const config: CommonServicesConfig =
      await CommonServicesConfigService.getConfig();
    const identifier = { isbn: ["isbn0", "isbn1"], issn: ["issn0"] };
    expect(CommonServicesConfigService.getCoverUrl(identifier, config)).toBe(
      "https://test-api.axiell.com/cover/api/covers/worldcat?size=SMALL&coverIds=isbn0&coverIds=isbn1&coverIds=issn0"
    );
  });
});
