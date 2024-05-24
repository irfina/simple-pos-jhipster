<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="taxType">
        <h2 class="jh-entity-heading" data-cy="taxTypeDetailsHeading">
          <span v-text="t$('mobilePosApp.taxType.detail.title')"></span> {{ taxType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mobilePosApp.taxType.code')"></span>
          </dt>
          <dd>
            <span>{{ taxType.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.taxType.name')"></span>
          </dt>
          <dd>
            <span>{{ taxType.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.taxType.value')"></span>
          </dt>
          <dd>
            <span>{{ taxType.value }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.taxType.notes')"></span>
          </dt>
          <dd>
            <span>{{ taxType.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="taxType.id" :to="{ name: 'TaxTypeEdit', params: { taxTypeId: taxType.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tax-type-details.component.ts"></script>
