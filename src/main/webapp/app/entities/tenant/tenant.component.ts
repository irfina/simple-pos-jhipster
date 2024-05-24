import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import TenantService from './tenant.service';
import { type ITenant } from '@/shared/model/tenant.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Tenant',
  setup() {
    const { t: t$ } = useI18n();
    const tenantService = inject('tenantService', () => new TenantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tenants: Ref<ITenant[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveTenants = async () => {
      isFetching.value = true;
      try {
        const res = await tenantService().retrieve();
        tenants.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveTenants();
    };

    onMounted(async () => {
      await retrieveTenants();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ITenant) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeTenant = async () => {
      try {
        await tenantService().delete(removeId.value);
        const message = t$('mobilePosApp.tenant.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveTenants();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      tenants,
      handleSyncList,
      isFetching,
      retrieveTenants,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeTenant,
      t$,
    };
  },
});
