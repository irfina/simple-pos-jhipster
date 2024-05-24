<template>
  <div>
    <b-dropdown-item to="/tenant">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.tenant')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.country')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/province">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.province')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/regency">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.regency')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product-category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.productCategory')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/metric">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.metric')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.product')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/tax-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.taxType')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
