import { type IProvince } from '@/shared/model/province.model';

export interface IRegency {
  id?: number;
  name?: string;
  province?: IProvince;
}

export class Regency implements IRegency {
  constructor(
    public id?: number,
    public name?: string,
    public province?: IProvince,
  ) {}
}
