<template>
  <div>
    <h2 id="page-heading" data-cy="ProductHeading">
      <span v-text="t$('mobilePosApp.product.home.title')" id="product-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mobilePosApp.product.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ProductCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-product"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mobilePosApp.product.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && products && products.length === 0">
      <span v-text="t$('mobilePosApp.product.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="products && products.length > 0">
      <table class="table table-striped" aria-describedby="products">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('sku')">
              <span v-text="t$('mobilePosApp.product.sku')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sku'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('mobilePosApp.product.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('barcode')">
              <span v-text="t$('mobilePosApp.product.barcode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'barcode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('discountInPercent')">
              <span v-text="t$('mobilePosApp.product.discountInPercent')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'discountInPercent'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('minDiscQty')">
              <span v-text="t$('mobilePosApp.product.minDiscQty')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'minDiscQty'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('sellPrice')">
              <span v-text="t$('mobilePosApp.product.sellPrice')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sellPrice'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isVatApplied')">
              <span v-text="t$('mobilePosApp.product.isVatApplied')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isVatApplied'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isActive')">
              <span v-text="t$('mobilePosApp.product.isActive')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isActive'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isStockable')">
              <span v-text="t$('mobilePosApp.product.isStockable')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isStockable'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="t$('mobilePosApp.product.notes')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="t$('mobilePosApp.product.createdAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="t$('mobilePosApp.product.createdBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdatedAt')">
              <span v-text="t$('mobilePosApp.product.lastUpdatedAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdatedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdatedBy')">
              <span v-text="t$('mobilePosApp.product.lastUpdatedBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdatedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('defaultMetricInv.id')">
              <span v-text="t$('mobilePosApp.product.defaultMetricInv')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'defaultMetricInv.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('defaultMetricSales.id')">
              <span v-text="t$('mobilePosApp.product.defaultMetricSales')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'defaultMetricSales.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('defaultMetricPurchase.id')">
              <span v-text="t$('mobilePosApp.product.defaultMetricPurchase')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'defaultMetricPurchase.id'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category.id')">
              <span v-text="t$('mobilePosApp.product.category')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProductView', params: { productId: product.id } }">{{ product.id }}</router-link>
            </td>
            <td>{{ product.sku }}</td>
            <td>{{ product.name }}</td>
            <td>{{ product.barcode }}</td>
            <td>{{ product.discountInPercent }}</td>
            <td>{{ product.minDiscQty }}</td>
            <td>{{ product.sellPrice }}</td>
            <td>{{ product.isVatApplied }}</td>
            <td>{{ product.isActive }}</td>
            <td>{{ product.isStockable }}</td>
            <td>{{ product.notes }}</td>
            <td>{{ formatDateShort(product.createdAt) || '' }}</td>
            <td>{{ product.createdBy }}</td>
            <td>{{ formatDateShort(product.lastUpdatedAt) || '' }}</td>
            <td>{{ product.lastUpdatedBy }}</td>
            <td>
              <div v-if="product.defaultMetricInv">
                <router-link :to="{ name: 'MetricView', params: { metricId: product.defaultMetricInv.id } }">{{
                  product.defaultMetricInv.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="product.defaultMetricSales">
                <router-link :to="{ name: 'MetricView', params: { metricId: product.defaultMetricSales.id } }">{{
                  product.defaultMetricSales.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="product.defaultMetricPurchase">
                <router-link :to="{ name: 'MetricView', params: { metricId: product.defaultMetricPurchase.id } }">{{
                  product.defaultMetricPurchase.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="product.category">
                <router-link :to="{ name: 'ProductCategoryView', params: { productCategoryId: product.category.id } }">{{
                  product.category.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProductView', params: { productId: product.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(product)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="mobilePosApp.product.delete.question" data-cy="productDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-product-heading" v-text="t$('mobilePosApp.product.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-product"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeProduct()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="products && products.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product.component.ts"></script>
