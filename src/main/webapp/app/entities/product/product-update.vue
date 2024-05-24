<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mobilePosApp.product.home.createOrEditLabel"
          data-cy="ProductCreateUpdateHeading"
          v-text="t$('mobilePosApp.product.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="product.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="product.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.sku')" for="product-sku"></label>
            <input
              type="text"
              class="form-control"
              name="sku"
              id="product-sku"
              data-cy="sku"
              :class="{ valid: !v$.sku.$invalid, invalid: v$.sku.$invalid }"
              v-model="v$.sku.$model"
            />
            <div v-if="v$.sku.$anyDirty && v$.sku.$invalid">
              <small class="form-text text-danger" v-for="error of v$.sku.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.name')" for="product-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="product-name"
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
            <label class="form-control-label" v-text="t$('mobilePosApp.product.barcode')" for="product-barcode"></label>
            <input
              type="text"
              class="form-control"
              name="barcode"
              id="product-barcode"
              data-cy="barcode"
              :class="{ valid: !v$.barcode.$invalid, invalid: v$.barcode.$invalid }"
              v-model="v$.barcode.$model"
            />
            <div v-if="v$.barcode.$anyDirty && v$.barcode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.barcode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.discountInPercent')" for="product-discountInPercent"></label>
            <input
              type="number"
              class="form-control"
              name="discountInPercent"
              id="product-discountInPercent"
              data-cy="discountInPercent"
              :class="{ valid: !v$.discountInPercent.$invalid, invalid: v$.discountInPercent.$invalid }"
              v-model.number="v$.discountInPercent.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.minDiscQty')" for="product-minDiscQty"></label>
            <input
              type="number"
              class="form-control"
              name="minDiscQty"
              id="product-minDiscQty"
              data-cy="minDiscQty"
              :class="{ valid: !v$.minDiscQty.$invalid, invalid: v$.minDiscQty.$invalid }"
              v-model.number="v$.minDiscQty.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.sellPrice')" for="product-sellPrice"></label>
            <input
              type="number"
              class="form-control"
              name="sellPrice"
              id="product-sellPrice"
              data-cy="sellPrice"
              :class="{ valid: !v$.sellPrice.$invalid, invalid: v$.sellPrice.$invalid }"
              v-model.number="v$.sellPrice.$model"
              required
            />
            <div v-if="v$.sellPrice.$anyDirty && v$.sellPrice.$invalid">
              <small class="form-text text-danger" v-for="error of v$.sellPrice.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.isVatApplied')" for="product-isVatApplied"></label>
            <input
              type="checkbox"
              class="form-check"
              name="isVatApplied"
              id="product-isVatApplied"
              data-cy="isVatApplied"
              :class="{ valid: !v$.isVatApplied.$invalid, invalid: v$.isVatApplied.$invalid }"
              v-model="v$.isVatApplied.$model"
              required
            />
            <div v-if="v$.isVatApplied.$anyDirty && v$.isVatApplied.$invalid">
              <small class="form-text text-danger" v-for="error of v$.isVatApplied.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.isActive')" for="product-isActive"></label>
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="product-isActive"
              data-cy="isActive"
              :class="{ valid: !v$.isActive.$invalid, invalid: v$.isActive.$invalid }"
              v-model="v$.isActive.$model"
              required
            />
            <div v-if="v$.isActive.$anyDirty && v$.isActive.$invalid">
              <small class="form-text text-danger" v-for="error of v$.isActive.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.isStockable')" for="product-isStockable"></label>
            <input
              type="checkbox"
              class="form-check"
              name="isStockable"
              id="product-isStockable"
              data-cy="isStockable"
              :class="{ valid: !v$.isStockable.$invalid, invalid: v$.isStockable.$invalid }"
              v-model="v$.isStockable.$model"
              required
            />
            <div v-if="v$.isStockable.$anyDirty && v$.isStockable.$invalid">
              <small class="form-text text-danger" v-for="error of v$.isStockable.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.notes')" for="product-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="product-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
            <div v-if="v$.notes.$anyDirty && v$.notes.$invalid">
              <small class="form-text text-danger" v-for="error of v$.notes.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.createdAt')" for="product-createdAt"></label>
            <div class="d-flex">
              <input
                id="product-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !v$.createdAt.$invalid, invalid: v$.createdAt.$invalid }"
                :value="convertDateTimeFromServer(v$.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.createdBy')" for="product-createdBy"></label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="product-createdBy"
              data-cy="createdBy"
              :class="{ valid: !v$.createdBy.$invalid, invalid: v$.createdBy.$invalid }"
              v-model.number="v$.createdBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.lastUpdatedAt')" for="product-lastUpdatedAt"></label>
            <div class="d-flex">
              <input
                id="product-lastUpdatedAt"
                data-cy="lastUpdatedAt"
                type="datetime-local"
                class="form-control"
                name="lastUpdatedAt"
                :class="{ valid: !v$.lastUpdatedAt.$invalid, invalid: v$.lastUpdatedAt.$invalid }"
                :value="convertDateTimeFromServer(v$.lastUpdatedAt.$model)"
                @change="updateInstantField('lastUpdatedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.lastUpdatedBy')" for="product-lastUpdatedBy"></label>
            <input
              type="number"
              class="form-control"
              name="lastUpdatedBy"
              id="product-lastUpdatedBy"
              data-cy="lastUpdatedBy"
              :class="{ valid: !v$.lastUpdatedBy.$invalid, invalid: v$.lastUpdatedBy.$invalid }"
              v-model.number="v$.lastUpdatedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.defaultMetricInv')" for="product-defaultMetricInv"></label>
            <select
              class="form-control"
              id="product-defaultMetricInv"
              data-cy="defaultMetricInv"
              name="defaultMetricInv"
              v-model="product.defaultMetricInv"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  product.defaultMetricInv && metricOption.id === product.defaultMetricInv.id ? product.defaultMetricInv : metricOption
                "
                v-for="metricOption in metrics"
                :key="metricOption.id"
              >
                {{ metricOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mobilePosApp.product.defaultMetricSales')"
              for="product-defaultMetricSales"
            ></label>
            <select
              class="form-control"
              id="product-defaultMetricSales"
              data-cy="defaultMetricSales"
              name="defaultMetricSales"
              v-model="product.defaultMetricSales"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  product.defaultMetricSales && metricOption.id === product.defaultMetricSales.id
                    ? product.defaultMetricSales
                    : metricOption
                "
                v-for="metricOption in metrics"
                :key="metricOption.id"
              >
                {{ metricOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mobilePosApp.product.defaultMetricPurchase')"
              for="product-defaultMetricPurchase"
            ></label>
            <select
              class="form-control"
              id="product-defaultMetricPurchase"
              data-cy="defaultMetricPurchase"
              name="defaultMetricPurchase"
              v-model="product.defaultMetricPurchase"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  product.defaultMetricPurchase && metricOption.id === product.defaultMetricPurchase.id
                    ? product.defaultMetricPurchase
                    : metricOption
                "
                v-for="metricOption in metrics"
                :key="metricOption.id"
              >
                {{ metricOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mobilePosApp.product.category')" for="product-category"></label>
            <select class="form-control" id="product-category" data-cy="category" name="category" v-model="product.category" required>
              <option v-if="!product.category" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  product.category && productCategoryOption.id === product.category.id ? product.category : productCategoryOption
                "
                v-for="productCategoryOption in productCategories"
                :key="productCategoryOption.id"
              >
                {{ productCategoryOption.id }}
              </option>
            </select>
          </div>
          <div v-if="v$.category.$anyDirty && v$.category.$invalid">
            <small class="form-text text-danger" v-for="error of v$.category.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./product-update.component.ts"></script>
