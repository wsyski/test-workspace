/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isEqual from "react-fast-compare";

export default class MiscUtil {
  static isEmpty(
    value:
      | string
      | number
      | boolean
      | (string | boolean | number)[]
      | Date
      | URLSearchParams
      | null
      | undefined
  ): boolean {
    if (typeof value === "undefined") {
      return true;
    }
    if (value instanceof URLSearchParams) {
      let isEmpty = true;
      value.forEach(() => {
        isEmpty = false;
      });

      return isEmpty;
    } else {
      if (Array.isArray(value)) {
        return (
          !value.length ||
          value.every((v) => MiscUtil.isEmpty(v))
        );
      } else {
        return (
          typeof value === "undefined" ||
          value === null ||
          (typeof value === "string" && value.trim() === "")
        );
      }
    }
  }

  static createMarkup(html: string | undefined = ""): { __html: string } {
    return { __html: html };
  }

  static appendAll(target: URLSearchParams, source: URLSearchParams): void {
    source.forEach((value, key) => {
      target.append(key, value);
    });
  }

  static recordValues(
    record: Record<string, string[]>,
    key?: string
  ): string[] | undefined {
    if (record) {
      let values: string[] = [];
      Object.entries(record).forEach(([k, v]) => {
        values = !key || k === key ? values.concat(v) : values;
      });

      return values;
    }
  }

  static template(pattern: string, parameters: any): string {
    return pattern.replace(/\${(.*?)}/g, (_x, g) => parameters[g]);
  }

  static isEqual<T = any>(object0: T, object1: T): boolean {
    let result;
    if (Array.isArray(object0) && Array.isArray(object1)) {
      const array0 = [...object0].sort();
      const array1 = [...object1].sort();
      result = MiscUtil.isArrayEqual(array0, array1);
    } else {
      result = isEqual(object0, object1);
    }

    // if (!result) {
    //    console.log(`${object0} !== ${object1}`);
    // }

    return result;
  }

  static isHtml(s: string): boolean {
    const a = document.createElement("div");
    a.innerHTML = s;

    for (let c = a.childNodes, i = c.length; i--;) {
      if (c[i].nodeType === 1) {
        return true;
      }
    }

    return false;
  }

  static randomString(len: number = 10): string {
    const s = Array(len).fill(0).map(() => Math.random().toString(36)[2]).join("");

    return s;
  }

  static normalize(s: string | undefined): string {
    return s ? s.normalize("NFD").replace(/[\u0300-\u036f]/g, "") : "";
  }

  private static isArrayEqual<T = any>(
    array0: T[] | undefined = [],
    array1: T[] | undefined = []
  ): boolean {
    if (array0.length !== array1.length) {
      return false;
    }

    return array0.every((value, index) => {
      return isEqual(value, array1[index]);
    });
  }
}
