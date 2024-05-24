/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TenantDetails from './tenant-details.vue';
import TenantService from './tenant.service';
import AlertService from '@/shared/alert/alert.service';

type TenantDetailsComponentType = InstanceType<typeof TenantDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const tenantSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Tenant Management Detail Component', () => {
    let tenantServiceStub: SinonStubbedInstance<TenantService>;
    let mountOptions: MountingOptions<TenantDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      tenantServiceStub = sinon.createStubInstance<TenantService>(TenantService);

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
          tenantService: () => tenantServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        tenantServiceStub.find.resolves(tenantSample);
        route = {
          params: {
            tenantId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(TenantDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.tenant).toMatchObject(tenantSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        tenantServiceStub.find.resolves(tenantSample);
        const wrapper = shallowMount(TenantDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
