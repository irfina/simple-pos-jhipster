import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MetricService from './metric.service';
import { type IMetric } from '@/shared/model/metric.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MetricDetails',
  setup() {
    const metricService = inject('metricService', () => new MetricService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const metric: Ref<IMetric> = ref({});

    const retrieveMetric = async metricId => {
      try {
        const res = await metricService().find(metricId);
        metric.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.metricId) {
      retrieveMetric(route.params.metricId);
    }

    return {
      alertService,
      metric,

      previousState,
      t$: useI18n().t,
    };
  },
});
