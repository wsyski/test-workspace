import { AxChronoUnit } from "../ax/AxChronoUnit";
import { AxDateModel } from "../ax/AxDateModel";
import { AxDateQualifier } from "../ax/AxDateQualifier";

export default interface AxExtendedDate {
  chronoUnit?: AxChronoUnit;
  dateModel?: AxDateModel;
  dateQualifier?: AxDateQualifier;
  duration?: number;
  hasBeginningDate?: AxExtendedDate;
  hasEndDate?: AxExtendedDate;
  normalizedValue?: string;
  textualValue?: string;
  type?: string;
  year?: number;
}
