import DOMPurify from "dompurify";
import isEmpty from "lodash/isEmpty";
import React from "react";
import isEqual from "react-fast-compare";

export default class MiscUtil {
  static isEmpty(
    value: any
  ): boolean {
    if (typeof value === "undefined" || value === null) {
      return true;
    }

    if (Array.isArray(value)) {
      return (
        !value.length ||
        value.every((v) => MiscUtil.isEmpty(v))
      );
    } else if (value instanceof URLSearchParams) {
      let result = true;
      value.forEach(() => {
        result = false;
      });

      return result;
    } else if (typeof value === "string") {
      return value.trim() === "";
    } else if (typeof value === "boolean") {
      return false;
    } else {
      return isEmpty(value);
    }
  }

  static createMarkup(html: string | undefined = ""): { __html: string } {
    return { __html: MiscUtil.sanitize(html, {}) };
  }

  static appendAll(target: URLSearchParams, source: URLSearchParams): void {
    source.forEach((value, key) => {
      target.append(key, value);
    });
  }

  static recordValues<T = any>(
    record: Record<string, T[]> | undefined,
    key?: string
  ): T[] {
    if (record) {
      let values: T[] = [];
      Object.entries(record).forEach(([k, v]) => {
        values = !key || k === key ? values.concat(v) : values;
      });

      return values;
    } else {
      return [];
    }
  }

  static firstValue<T = any>(
    values: T[] | undefined
  ): T | undefined {
    return (values && values.length) ? values[0] : undefined;
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

    return result;
  }

  static isIOS = () => {
    return (
        /iPhone/.test(navigator.userAgent)
    );
  };

  static setDatePickerFocus(ref: React.RefObject<HTMLInputElement>) {
    if (ref.current?.nextSibling?.childNodes?.item(0)) {
      (ref.current.nextSibling.childNodes.item(0) as HTMLButtonElement).focus();
    }
  }

  static isHtml(s: string | undefined): boolean {
    if (s) {
      const doc = new DOMParser().parseFromString(s, "text/html");

      return [].slice.call(doc.body.childNodes).some(node => node["nodeType"] === 1);
    }

    return false;
  }

  static getPostfix(s: string): string {
    const pos = s.lastIndexOf(".");
    if (pos === -1) {
      return s;
    } else {
      return s.substring(pos + 1);
    }
  };

  static randomString(len: number = 10): string {
    return Array(len).fill(0).map(() => Math.random().toString(36)[2]).join("");
  }

  static normalize(s: string | undefined): string {
    return s ? s.normalize("NFD").replace(/[\u0300-\u036f]/g, "") : "";
  }

  static sanitize(s: string, config: object): string {
    return DOMPurify.sanitize(s, config);
  }

  static isPositiveInteger(s: string | null): boolean {
    return (s) ? /^\+?([1-9]\d*)$/.test(s) : false;
  }

  static getByteByLength(s: string | undefined): number {
    const textEncoder = new TextEncoder();
    const byteArray = textEncoder.encode(s);

    return s ? byteArray.length : 0;
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
