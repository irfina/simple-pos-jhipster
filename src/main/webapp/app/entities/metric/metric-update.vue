<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mobilePosApp.metric.home.createOrEditLabel"
          data-cy="MetricCreateUpdateHeading"
          v-text="t$('mobilePosApp.metric.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="metric.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="metric.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.metric.name')" for="metric-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="metric-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
              required
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.metric.notes')" for="metric-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="metric-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
            <div v-if="v$.notes.$anyDirty && v$.notes.$invalid">
              <small class="form-text text-danger" v-for="error of v$.notes.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./metric-update.component.ts"></script>
