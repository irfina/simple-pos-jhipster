/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MetricDetails from './metric-details.vue';
import MetricService from './metric.service';
import AlertService from '@/shared/alert/alert.service';

type MetricDetailsComponentType = InstanceType<typeof MetricDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const metricSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Metric Management Detail Component', () => {
    let metricServiceStub: SinonStubbedInstance<MetricService>;
    let mountOptions: MountingOptions<MetricDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      metricServiceStub = sinon.createStubInstance<MetricService>(MetricService);

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
          metricService: () => metricServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        metricServiceStub.find.resolves(metricSample);
        route = {
          params: {
            metricId: '' + 123,
          },
        };
        const wrapper = shallowMount(MetricDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.metric).toMatchObject(metricSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        metricServiceStub.find.resolves(metricSample);
        const wrapper = shallowMount(MetricDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
