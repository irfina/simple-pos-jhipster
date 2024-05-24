<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="metric">
        <h2 class="jh-entity-heading" data-cy="metricDetailsHeading">
          <span v-text="t$('mobilePosApp.metric.detail.title')"></span> {{ metric.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mobilePosApp.metric.name')"></span>
          </dt>
          <dd>
            <span>{{ metric.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.metric.notes')"></span>
          </dt>
          <dd>
            <span>{{ metric.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="metric.id" :to="{ name: 'MetricEdit', params: { metricId: metric.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./metric-details.component.ts"></script>
