<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="t$('mobilePosApp.product.detail.title')"></span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mobilePosApp.product.sku')"></span>
          </dt>
          <dd>
            <span>{{ product.sku }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.name')"></span>
          </dt>
          <dd>
            <span>{{ product.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.barcode')"></span>
          </dt>
          <dd>
            <span>{{ product.barcode }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.discountInPercent')"></span>
          </dt>
          <dd>
            <span>{{ product.discountInPercent }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.minDiscQty')"></span>
          </dt>
          <dd>
            <span>{{ product.minDiscQty }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.sellPrice')"></span>
          </dt>
          <dd>
            <span>{{ product.sellPrice }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.isVatApplied')"></span>
          </dt>
          <dd>
            <span>{{ product.isVatApplied }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.isActive')"></span>
          </dt>
          <dd>
            <span>{{ product.isActive }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.isStockable')"></span>
          </dt>
          <dd>
            <span>{{ product.isStockable }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.notes')"></span>
          </dt>
          <dd>
            <span>{{ product.notes }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="product.createdAt">{{ formatDateLong(product.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.createdBy')"></span>
          </dt>
          <dd>
            <span>{{ product.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.lastUpdatedAt')"></span>
          </dt>
          <dd>
            <span v-if="product.lastUpdatedAt">{{ formatDateLong(product.lastUpdatedAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.lastUpdatedBy')"></span>
          </dt>
          <dd>
            <span>{{ product.lastUpdatedBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.defaultMetricInv')"></span>
          </dt>
          <dd>
            <div v-if="product.defaultMetricInv">
              <router-link :to="{ name: 'MetricView', params: { metricId: product.defaultMetricInv.id } }">{{
                product.defaultMetricInv.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.defaultMetricSales')"></span>
          </dt>
          <dd>
            <div v-if="product.defaultMetricSales">
              <router-link :to="{ name: 'MetricView', params: { metricId: product.defaultMetricSales.id } }">{{
                product.defaultMetricSales.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.defaultMetricPurchase')"></span>
          </dt>
          <dd>
            <div v-if="product.defaultMetricPurchase">
              <router-link :to="{ name: 'MetricView', params: { metricId: product.defaultMetricPurchase.id } }">{{
                product.defaultMetricPurchase.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('mobilePosApp.product.category')"></span>
          </dt>
          <dd>
            <div v-if="product.category">
              <router-link :to="{ name: 'ProductCategoryView', params: { productCategoryId: product.category.id } }">{{
                product.category.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
