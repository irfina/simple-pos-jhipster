import { defineComponent, provide } from 'vue';

import TenantService from './tenant/tenant.service';
import CountryService from './country/country.service';
import ProvinceService from './province/province.service';
import RegencyService from './regency/regency.service';
import ProductCategoryService from './product-category/product-category.service';
import MetricService from './metric/metric.service';
import ProductService from './product/product.service';
import TaxTypeService from './tax-type/tax-type.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('tenantService', () => new TenantService());
    provide('countryService', () => new CountryService());
    provide('provinceService', () => new ProvinceService());
    provide('regencyService', () => new RegencyService());
    provide('productCategoryService', () => new ProductCategoryService());
    provide('metricService', () => new MetricService());
    provide('productService', () => new ProductService());
    provide('taxTypeService', () => new TaxTypeService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
