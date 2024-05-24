/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RegencyUpdate from './regency-update.vue';
import RegencyService from './regency.service';
import AlertService from '@/shared/alert/alert.service';

import ProvinceService from '@/entities/province/province.service';

type RegencyUpdateComponentType = InstanceType<typeof RegencyUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const regencySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RegencyUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Regency Management Update Component', () => {
    let comp: RegencyUpdateComponentType;
    let regencyServiceStub: SinonStubbedInstance<RegencyService>;

    beforeEach(() => {
      route = {};
      regencyServiceStub = sinon.createStubInstance<RegencyService>(RegencyService);
      regencyServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          regencyService: () => regencyServiceStub,
          provinceService: () =>
            sinon.createStubInstance<ProvinceService>(ProvinceService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RegencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.regency = regencySample;
        regencyServiceStub.update.resolves(regencySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regencyServiceStub.update.calledWith(regencySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        regencyServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RegencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.regency = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regencyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        regencyServiceStub.find.resolves(regencySample);
        regencyServiceStub.retrieve.resolves([regencySample]);

        // WHEN
        route = {
          params: {
            regencyId: '' + regencySample.id,
          },
        };
        const wrapper = shallowMount(RegencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.regency).toMatchObject(regencySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        regencyServiceStub.find.resolves(regencySample);
        const wrapper = shallowMount(RegencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
