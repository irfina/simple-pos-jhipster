export interface IMetric {
  id?: number;
  name?: string;
  notes?: string | null;
}

export class Metric implements IMetric {
  constructor(
    public id?: number,
    public name?: string,
    public notes?: string | null,
  ) {}
}
