import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MetricService from './metric.service';
import { type IMetric } from '@/shared/model/metric.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Metric',
  setup() {
    const { t: t$ } = useI18n();
    const metricService = inject('metricService', () => new MetricService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const metrics: Ref<IMetric[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMetrics = async () => {
      isFetching.value = true;
      try {
        const res = await metricService().retrieve();
        metrics.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMetrics();
    };

    onMounted(async () => {
      await retrieveMetrics();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMetric) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMetric = async () => {
      try {
        await metricService().delete(removeId.value);
        const message = t$('mobilePosApp.metric.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMetrics();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      metrics,
      handleSyncList,
      isFetching,
      retrieveMetrics,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMetric,
      t$,
    };
  },
});
