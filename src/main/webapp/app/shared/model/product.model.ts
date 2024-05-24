import { type IMetric } from '@/shared/model/metric.model';
import { type IProductCategory } from '@/shared/model/product-category.model';

export interface IProduct {
  id?: string;
  sku?: string | null;
  name?: string;
  barcode?: string | null;
  discountInPercent?: number | null;
  minDiscQty?: number | null;
  sellPrice?: number;
  isVatApplied?: boolean;
  isActive?: boolean;
  isStockable?: boolean;
  notes?: string | null;
  createdAt?: Date | null;
  createdBy?: number | null;
  lastUpdatedAt?: Date | null;
  lastUpdatedBy?: number | null;
  defaultMetricInv?: IMetric | null;
  defaultMetricSales?: IMetric | null;
  defaultMetricPurchase?: IMetric | null;
  category?: IProductCategory;
}

export class Product implements IProduct {
  constructor(
    public id?: string,
    public sku?: string | null,
    public name?: string,
    public barcode?: string | null,
    public discountInPercent?: number | null,
    public minDiscQty?: number | null,
    public sellPrice?: number,
    public isVatApplied?: boolean,
    public isActive?: boolean,
    public isStockable?: boolean,
    public notes?: string | null,
    public createdAt?: Date | null,
    public createdBy?: number | null,
    public lastUpdatedAt?: Date | null,
    public lastUpdatedBy?: number | null,
    public defaultMetricInv?: IMetric | null,
    public defaultMetricSales?: IMetric | null,
    public defaultMetricPurchase?: IMetric | null,
    public category?: IProductCategory,
  ) {
    this.isVatApplied = this.isVatApplied ?? false;
    this.isActive = this.isActive ?? false;
    this.isStockable = this.isStockable ?? false;
  }
}
