/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MetricUpdate from './metric-update.vue';
import MetricService from './metric.service';
import AlertService from '@/shared/alert/alert.service';

type MetricUpdateComponentType = InstanceType<typeof MetricUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const metricSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MetricUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Metric Management Update Component', () => {
    let comp: MetricUpdateComponentType;
    let metricServiceStub: SinonStubbedInstance<MetricService>;

    beforeEach(() => {
      route = {};
      metricServiceStub = sinon.createStubInstance<MetricService>(MetricService);
      metricServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          metricService: () => metricServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MetricUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.metric = metricSample;
        metricServiceStub.update.resolves(metricSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(metricServiceStub.update.calledWith(metricSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        metricServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MetricUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.metric = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(metricServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        metricServiceStub.find.resolves(metricSample);
        metricServiceStub.retrieve.resolves([metricSample]);

        // WHEN
        route = {
          params: {
            metricId: '' + metricSample.id,
          },
        };
        const wrapper = shallowMount(MetricUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.metric).toMatchObject(metricSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        metricServiceStub.find.resolves(metricSample);
        const wrapper = shallowMount(MetricUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
