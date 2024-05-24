import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TenantService from './tenant.service';
import { type ITenant } from '@/shared/model/tenant.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TenantDetails',
  setup() {
    const tenantService = inject('tenantService', () => new TenantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const tenant: Ref<ITenant> = ref({});

    const retrieveTenant = async tenantId => {
      try {
        const res = await tenantService().find(tenantId);
        tenant.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.tenantId) {
      retrieveTenant(route.params.tenantId);
    }

    return {
      alertService,
      tenant,

      previousState,
      t$: useI18n().t,
    };
  },
});
