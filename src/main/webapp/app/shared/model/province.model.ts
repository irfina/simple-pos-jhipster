import { type ICountry } from '@/shared/model/country.model';

export interface IProvince {
  id?: number;
  name?: string;
  country?: ICountry;
}

export class Province implements IProvince {
  constructor(
    public id?: number,
    public name?: string,
    public country?: ICountry,
  ) {}
}
