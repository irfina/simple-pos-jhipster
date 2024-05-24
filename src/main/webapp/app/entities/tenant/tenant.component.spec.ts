/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Tenant from './tenant.vue';
import TenantService from './tenant.service';
import AlertService from '@/shared/alert/alert.service';

type TenantComponentType = InstanceType<typeof Tenant>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Tenant Management Component', () => {
    let tenantServiceStub: SinonStubbedInstance<TenantService>;
    let mountOptions: MountingOptions<TenantComponentType>['global'];

    beforeEach(() => {
      tenantServiceStub = sinon.createStubInstance<TenantService>(TenantService);
      tenantServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          tenantService: () => tenantServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        tenantServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(Tenant, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(tenantServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.tenants[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: TenantComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Tenant, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        tenantServiceStub.retrieve.reset();
        tenantServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        tenantServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removeTenant();
        await comp.$nextTick(); // clear components

        // THEN
        expect(tenantServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(tenantServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
