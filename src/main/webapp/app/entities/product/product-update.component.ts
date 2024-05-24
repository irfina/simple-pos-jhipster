import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProductService from './product.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MetricService from '@/entities/metric/metric.service';
import { type IMetric } from '@/shared/model/metric.model';
import ProductCategoryService from '@/entities/product-category/product-category.service';
import { type IProductCategory } from '@/shared/model/product-category.model';
import { type IProduct, Product } from '@/shared/model/product.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProductUpdate',
  setup() {
    const productService = inject('productService', () => new ProductService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const product: Ref<IProduct> = ref(new Product());

    const metricService = inject('metricService', () => new MetricService());

    const metrics: Ref<IMetric[]> = ref([]);

    const productCategoryService = inject('productCategoryService', () => new ProductCategoryService());

    const productCategories: Ref<IProductCategory[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveProduct = async productId => {
      try {
        const res = await productService().find(productId);
        res.createdAt = new Date(res.createdAt);
        res.lastUpdatedAt = new Date(res.lastUpdatedAt);
        product.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.productId) {
      retrieveProduct(route.params.productId);
    }

    const initRelationships = () => {
      metricService()
        .retrieve()
        .then(res => {
          metrics.value = res.data;
        });
      productCategoryService()
        .retrieve()
        .then(res => {
          productCategories.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      sku: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 3 }).toString(), 3),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      barcode: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      discountInPercent: {},
      minDiscQty: {},
      sellPrice: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      isVatApplied: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      isActive: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      isStockable: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      notes: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      createdAt: {},
      createdBy: {},
      lastUpdatedAt: {},
      lastUpdatedBy: {},
      defaultMetricInv: {},
      defaultMetricSales: {},
      defaultMetricPurchase: {},
      category: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, product as any);
    v$.value.$validate();

    return {
      productService,
      alertService,
      product,
      previousState,
      isSaving,
      currentLanguage,
      metrics,
      productCategories,
      v$,
      ...useDateFormat({ entityRef: product }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.product.id) {
        this.productService()
          .update(this.product)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mobilePosApp.product.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.productService()
          .create(this.product)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mobilePosApp.product.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
