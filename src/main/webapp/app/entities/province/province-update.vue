<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mobilePosApp.province.home.createOrEditLabel"
          data-cy="ProvinceCreateUpdateHeading"
          v-text="t$('mobilePosApp.province.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="province.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="province.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.province.name')" for="province-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="province-name"
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
            <label class="form-control-label" v-text="t$('mobilePosApp.province.country')" for="province-country"></label>
            <select class="form-control" id="province-country" data-cy="country" name="country" v-model="province.country" required>
              <option v-if="!province.country" v-bind:value="null" selected></option>
              <option
                v-bind:value="province.country && countryOption.isoCode === province.country.isoCode ? province.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.isoCode"
              >
                {{ countryOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.country.$anyDirty && v$.country.$invalid">
            <small class="form-text text-danger" v-for="error of v$.country.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./province-update.component.ts"></script>
