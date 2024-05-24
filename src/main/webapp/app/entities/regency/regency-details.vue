<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="regency">
        <h2 class="jh-entity-heading" data-cy="regencyDetailsHeading">
          <span v-text="t$('mobilePosApp.regency.detail.title')"></span> {{ regency.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mobilePosApp.regency.name')"></span>
          </dt>
          <dd>
            <span>{{ regency.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.regency.province')"></span>
          </dt>
          <dd>
            <div v-if="regency.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: regency.province.id } }">{{
                regency.province.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="regency.id" :to="{ name: 'RegencyEdit', params: { regencyId: regency.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./regency-details.component.ts"></script>
