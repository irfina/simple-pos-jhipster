export interface IProductCategory {
  id?: string;
  code?: string;
  name?: string;
  notes?: string | null;
}

export class ProductCategory implements IProductCategory {
  constructor(
    public id?: string,
    public code?: string,
    public name?: string,
    public notes?: string | null,
  ) {}
}
