import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TaxTypeService from './tax-type.service';
import { type ITaxType } from '@/shared/model/tax-type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TaxTypeDetails',
  setup() {
    const taxTypeService = inject('taxTypeService', () => new TaxTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const taxType: Ref<ITaxType> = ref({});

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

    return {
      alertService,
      taxType,

      previousState,
      t$: useI18n().t,
    };
  },
});
