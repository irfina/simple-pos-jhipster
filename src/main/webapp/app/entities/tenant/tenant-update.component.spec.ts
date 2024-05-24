/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TenantUpdate from './tenant-update.vue';
import TenantService from './tenant.service';
import AlertService from '@/shared/alert/alert.service';

type TenantUpdateComponentType = InstanceType<typeof TenantUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const tenantSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TenantUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Tenant Management Update Component', () => {
    let comp: TenantUpdateComponentType;
    let tenantServiceStub: SinonStubbedInstance<TenantService>;

    beforeEach(() => {
      route = {};
      tenantServiceStub = sinon.createStubInstance<TenantService>(TenantService);
      tenantServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          tenantService: () => tenantServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TenantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.tenant = tenantSample;
        tenantServiceStub.update.resolves(tenantSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tenantServiceStub.update.calledWith(tenantSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        tenantServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TenantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.tenant = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tenantServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        tenantServiceStub.find.resolves(tenantSample);
        tenantServiceStub.retrieve.resolves([tenantSample]);

        // WHEN
        route = {
          params: {
            tenantId: '' + tenantSample.id,
          },
        };
        const wrapper = shallowMount(TenantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.tenant).toMatchObject(tenantSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        tenantServiceStub.find.resolves(tenantSample);
        const wrapper = shallowMount(TenantUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
