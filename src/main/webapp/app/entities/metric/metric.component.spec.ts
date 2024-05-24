/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Metric from './metric.vue';
import MetricService from './metric.service';
import AlertService from '@/shared/alert/alert.service';

type MetricComponentType = InstanceType<typeof Metric>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Metric Management Component', () => {
    let metricServiceStub: SinonStubbedInstance<MetricService>;
    let mountOptions: MountingOptions<MetricComponentType>['global'];

    beforeEach(() => {
      metricServiceStub = sinon.createStubInstance<MetricService>(MetricService);
      metricServiceStub.retrieve.resolves({ headers: {} });

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
          metricService: () => metricServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        metricServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Metric, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(metricServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.metrics[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: MetricComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Metric, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        metricServiceStub.retrieve.reset();
        metricServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        metricServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeMetric();
        await comp.$nextTick(); // clear components

        // THEN
        expect(metricServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(metricServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
