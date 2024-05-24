<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mobilePosApp.taxType.home.createOrEditLabel"
          data-cy="TaxTypeCreateUpdateHeading"
          v-text="t$('mobilePosApp.taxType.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="taxType.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="taxType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.taxType.code')" for="tax-type-code"></label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="tax-type-code"
              data-cy="code"
              :class="{ valid: !v$.code.$invalid, invalid: v$.code.$invalid }"
              v-model="v$.code.$model"
              required
            />
            <div v-if="v$.code.$anyDirty && v$.code.$invalid">
              <small class="form-text text-danger" v-for="error of v$.code.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.taxType.name')" for="tax-type-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="tax-type-name"
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
            <label class="form-control-label" v-text="t$('mobilePosApp.taxType.value')" for="tax-type-value"></label>
            <input
              type="number"
              class="form-control"
              name="value"
              id="tax-type-value"
              data-cy="value"
              :class="{ valid: !v$.value.$invalid, invalid: v$.value.$invalid }"
              v-model.number="v$.value.$model"
              required
            />
            <div v-if="v$.value.$anyDirty && v$.value.$invalid">
              <small class="form-text text-danger" v-for="error of v$.value.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.taxType.notes')" for="tax-type-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="tax-type-notes"
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
<script lang="ts" src="./tax-type-update.component.ts"></script>
