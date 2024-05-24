/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TaxTypeDetails from './tax-type-details.vue';
import TaxTypeService from './tax-type.service';
import AlertService from '@/shared/alert/alert.service';

type TaxTypeDetailsComponentType = InstanceType<typeof TaxTypeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const taxTypeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TaxType Management Detail Component', () => {
    let taxTypeServiceStub: SinonStubbedInstance<TaxTypeService>;
    let mountOptions: MountingOptions<TaxTypeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      taxTypeServiceStub = sinon.createStubInstance<TaxTypeService>(TaxTypeService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          taxTypeService: () => taxTypeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        taxTypeServiceStub.find.resolves(taxTypeSample);
        route = {
          params: {
            taxTypeId: '' + 123,
          },
        };
        const wrapper = shallowMount(TaxTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.taxType).toMatchObject(taxTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        taxTypeServiceStub.find.resolves(taxTypeSample);
        const wrapper = shallowMount(TaxTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
