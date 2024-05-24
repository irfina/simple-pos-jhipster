import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Tenant = () => import('@/entities/tenant/tenant.vue');
const TenantUpdate = () => import('@/entities/tenant/tenant-update.vue');
const TenantDetails = () => import('@/entities/tenant/tenant-details.vue');

const Country = () => import('@/entities/country/country.vue');
const CountryUpdate = () => import('@/entities/country/country-update.vue');
const CountryDetails = () => import('@/entities/country/country-details.vue');

const Province = () => import('@/entities/province/province.vue');
const ProvinceUpdate = () => import('@/entities/province/province-update.vue');
const ProvinceDetails = () => import('@/entities/province/province-details.vue');

const Regency = () => import('@/entities/regency/regency.vue');
const RegencyUpdate = () => import('@/entities/regency/regency-update.vue');
const RegencyDetails = () => import('@/entities/regency/regency-details.vue');

const ProductCategory = () => import('@/entities/product-category/product-category.vue');
const ProductCategoryUpdate = () => import('@/entities/product-category/product-category-update.vue');
const ProductCategoryDetails = () => import('@/entities/product-category/product-category-details.vue');

const Metric = () => import('@/entities/metric/metric.vue');
const MetricUpdate = () => import('@/entities/metric/metric-update.vue');
const MetricDetails = () => import('@/entities/metric/metric-details.vue');

const Product = () => import('@/entities/product/product.vue');
const ProductUpdate = () => import('@/entities/product/product-update.vue');
const ProductDetails = () => import('@/entities/product/product-details.vue');

const TaxType = () => import('@/entities/tax-type/tax-type.vue');
const TaxTypeUpdate = () => import('@/entities/tax-type/tax-type-update.vue');
const TaxTypeDetails = () => import('@/entities/tax-type/tax-type-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'tenant',
      name: 'Tenant',
      component: Tenant,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant/new',
      name: 'TenantCreate',
      component: TenantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant/:tenantId/edit',
      name: 'TenantEdit',
      component: TenantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant/:tenantId/view',
      name: 'TenantView',
      component: TenantDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province',
      name: 'Province',
      component: Province,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/new',
      name: 'ProvinceCreate',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/edit',
      name: 'ProvinceEdit',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/view',
      name: 'ProvinceView',
      component: ProvinceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regency',
      name: 'Regency',
      component: Regency,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regency/new',
      name: 'RegencyCreate',
      component: RegencyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regency/:regencyId/edit',
      name: 'RegencyEdit',
      component: RegencyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regency/:regencyId/view',
      name: 'RegencyView',
      component: RegencyDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-category',
      name: 'ProductCategory',
      component: ProductCategory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-category/new',
      name: 'ProductCategoryCreate',
      component: ProductCategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-category/:productCategoryId/edit',
      name: 'ProductCategoryEdit',
      component: ProductCategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-category/:productCategoryId/view',
      name: 'ProductCategoryView',
      component: ProductCategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metric',
      name: 'Metric',
      component: Metric,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metric/new',
      name: 'MetricCreate',
      component: MetricUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metric/:metricId/edit',
      name: 'MetricEdit',
      component: MetricUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'metric/:metricId/view',
      name: 'MetricView',
      component: MetricDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tax-type',
      name: 'TaxType',
      component: TaxType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tax-type/new',
      name: 'TaxTypeCreate',
      component: TaxTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tax-type/:taxTypeId/edit',
      name: 'TaxTypeEdit',
      component: TaxTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tax-type/:taxTypeId/view',
      name: 'TaxTypeView',
      component: TaxTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
