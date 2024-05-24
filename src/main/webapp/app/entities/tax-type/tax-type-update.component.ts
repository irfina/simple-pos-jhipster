import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TaxTypeService from './tax-type.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITaxType, TaxType } from '@/shared/model/tax-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TaxTypeUpdate',
  setup() {
    const taxTypeService = inject('taxTypeService', () => new TaxTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const taxType: Ref<ITaxType> = ref(new TaxType());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTaxType = async taxTypeId => {
      try {
        const res = await taxTypeService().find(taxTypeId);
        taxType.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.taxTypeId) {
      retrieveTaxType(route.params.taxTypeId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      code: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      value: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      notes: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
    };
    const v$ = useVuelidate(validationRules, taxType as any);
    v$.value.$validate();

    return {
      taxTypeService,
      alertService,
      taxType,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.taxType.id) {
        this.taxTypeService()
          .update(this.taxType)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mobilePosApp.taxType.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.taxTypeService()
          .create(this.taxType)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mobilePosApp.taxType.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
