/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RegencyDetails from './regency-details.vue';
import RegencyService from './regency.service';
import AlertService from '@/shared/alert/alert.service';

type RegencyDetailsComponentType = InstanceType<typeof RegencyDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const regencySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Regency Management Detail Component', () => {
    let regencyServiceStub: SinonStubbedInstance<RegencyService>;
    let mountOptions: MountingOptions<RegencyDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      regencyServiceStub = sinon.createStubInstance<RegencyService>(RegencyService);

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
          regencyService: () => regencyServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        regencyServiceStub.find.resolves(regencySample);
        route = {
          params: {
            regencyId: '' + 123,
          },
        };
        const wrapper = shallowMount(RegencyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.regency).toMatchObject(regencySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        regencyServiceStub.find.resolves(regencySample);
        const wrapper = shallowMount(RegencyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
