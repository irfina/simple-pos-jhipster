export interface ICountry {
  id?: string;
  name?: string;
}

export class Country implements ICountry {
  constructor(
    public id?: string,
    public name?: string,
  ) {}
}
