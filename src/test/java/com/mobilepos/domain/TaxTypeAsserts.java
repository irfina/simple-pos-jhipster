package com.mobilepos.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TaxTypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaxTypeAllPropertiesEquals(TaxType expected, TaxType actual) {
        assertTaxTypeAutoGeneratedPropertiesEquals(expected, actual);
        assertTaxTypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaxTypeAllUpdatablePropertiesEquals(TaxType expected, TaxType actual) {
        assertTaxTypeUpdatableFieldsEquals(expected, actual);
        assertTaxTypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaxTypeAutoGeneratedPropertiesEquals(TaxType expected, TaxType actual) {
        assertThat(expected)
            .as("Verify TaxType auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaxTypeUpdatableFieldsEquals(TaxType expected, TaxType actual) {
        assertThat(expected)
            .as("Verify TaxType relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getValue()).as("check value").isEqualTo(actual.getValue()))
            .satisfies(e -> assertThat(e.getNotes()).as("check notes").isEqualTo(actual.getNotes()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTaxTypeUpdatableRelationshipsEquals(TaxType expected, TaxType actual) {}
}