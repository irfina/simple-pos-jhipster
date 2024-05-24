export interface ITaxType {
  id?: number;
  code?: string;
  name?: string;
  value?: number;
  notes?: string | null;
}

export class TaxType implements ITaxType {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public value?: number,
    public notes?: string | null,
  ) {}
}
