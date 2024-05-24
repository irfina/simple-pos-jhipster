import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TenantService from './tenant.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITenant, Tenant } from '@/shared/model/tenant.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TenantUpdate',
  setup() {
    const tenantService = inject('tenantService', () => new TenantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tenant: Ref<ITenant> = ref(new Tenant());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, tenant as any);
    v$.value.$validate();

    return {
      tenantService,
      alertService,
      tenant,
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
      if (this.tenant.id) {
        this.tenantService()
          .update(this.tenant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mobilePosApp.tenant.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.tenantService()
          .create(this.tenant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mobilePosApp.tenant.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
