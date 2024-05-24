<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mobilePosApp.regency.home.createOrEditLabel"
          data-cy="RegencyCreateUpdateHeading"
          v-text="t$('mobilePosApp.regency.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="regency.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="regency.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.regency.name')" for="regency-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="regency-name"
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
            <label class="form-control-label" v-text="t$('mobilePosApp.regency.province')" for="regency-province"></label>
            <select class="form-control" id="regency-province" data-cy="province" name="province" v-model="regency.province" required>
              <option v-if="!regency.province" v-bind:value="null" selected></option>
              <option
                v-bind:value="regency.province && provinceOption.id === regency.province.id ? regency.province : provinceOption"
                v-for="provinceOption in provinces"
                :key="provinceOption.id"
              >
                {{ provinceOption.id }}
              </option>
            </select>
          </div>
          <div v-if="v$.province.$anyDirty && v$.province.$invalid">
            <small class="form-text text-danger" v-for="error of v$.province.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./regency-update.component.ts"></script>
