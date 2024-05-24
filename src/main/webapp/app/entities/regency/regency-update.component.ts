import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RegencyService from './regency.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProvinceService from '@/entities/province/province.service';
import { type IProvince } from '@/shared/model/province.model';
import { type IRegency, Regency } from '@/shared/model/regency.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RegencyUpdate',
  setup() {
    const regencyService = inject('regencyService', () => new RegencyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const regency: Ref<IRegency> = ref(new Regency());

    const provinceService = inject('provinceService', () => new ProvinceService());

    const provinces: Ref<IProvince[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      provinceService()
        .retrieve()
        .then(res => {
          provinces.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      province: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, regency as any);
    v$.value.$validate();

    return {
      regencyService,
      alertService,
      regency,
      previousState,
      isSaving,
      currentLanguage,
      provinces,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.regency.id) {
        this.regencyService()
          .update(this.regency)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mobilePosApp.regency.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.regencyService()
          .create(this.regency)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mobilePosApp.regency.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
