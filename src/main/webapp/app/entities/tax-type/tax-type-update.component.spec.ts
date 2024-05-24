/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TaxTypeUpdate from './tax-type-update.vue';
import TaxTypeService from './tax-type.service';
import AlertService from '@/shared/alert/alert.service';

type TaxTypeUpdateComponentType = InstanceType<typeof TaxTypeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const taxTypeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TaxTypeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TaxType Management Update Component', () => {
    let comp: TaxTypeUpdateComponentType;
    let taxTypeServiceStub: SinonStubbedInstance<TaxTypeService>;

    beforeEach(() => {
      route = {};
      taxTypeServiceStub = sinon.createStubInstance<TaxTypeService>(TaxTypeService);
      taxTypeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          taxTypeService: () => taxTypeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TaxTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.taxType = taxTypeSample;
        taxTypeServiceStub.update.resolves(taxTypeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taxTypeServiceStub.update.calledWith(taxTypeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        taxTypeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TaxTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.taxType = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taxTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        taxTypeServiceStub.find.resolves(taxTypeSample);
        taxTypeServiceStub.retrieve.resolves([taxTypeSample]);

        // WHEN
        route = {
          params: {
            taxTypeId: '' + taxTypeSample.id,
          },
        };
        const wrapper = shallowMount(TaxTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.taxType).toMatchObject(taxTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        taxTypeServiceStub.find.resolves(taxTypeSample);
        const wrapper = shallowMount(TaxTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
