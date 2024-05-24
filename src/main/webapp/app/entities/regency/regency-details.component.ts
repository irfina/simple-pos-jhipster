import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RegencyService from './regency.service';
import { type IRegency } from '@/shared/model/regency.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RegencyDetails',
  setup() {
    const regencyService = inject('regencyService', () => new RegencyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const regency: Ref<IRegency> = ref({});

    const retrieveRegency = async regencyId => {
      try {
        const res = await regencyService().find(regencyId);
        regency.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.regencyId) {
      retrieveRegency(route.params.regencyId);
    }

    return {
      alertService,
      regency,

      previousState,
      t$: useI18n().t,
    };
  },
});
