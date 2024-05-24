import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('TaxType e2e test', () => {
  const taxTypePageUrl = '/tax-type';
  const taxTypePageUrlPattern = new RegExp('/tax-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const taxTypeSample = { code: 'seldom bobcat', name: 'compost deceivingly', value: 8159.42 };

  let taxType;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/tax-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/tax-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/tax-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (taxType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tax-types/${taxType.id}`,
      }).then(() => {
        taxType = undefined;
      });
    }
  });

  it('TaxTypes menu should load TaxTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('tax-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TaxType').should('exist');
    cy.url().should('match', taxTypePageUrlPattern);
  });

  describe('TaxType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(taxTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TaxType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/tax-type/new$'));
        cy.getEntityCreateUpdateHeading('TaxType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', taxTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/tax-types',
          body: taxTypeSample,
        }).then(({ body }) => {
          taxType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/tax-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/tax-types?page=0&size=20>; rel="last",<http://localhost/api/tax-types?page=0&size=20>; rel="first"',
              },
              body: [taxType],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(taxTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TaxType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('taxType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', taxTypePageUrlPattern);
      });

      it('edit button click should load edit TaxType page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TaxType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', taxTypePageUrlPattern);
      });

      it('edit button click should load edit TaxType page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TaxType');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', taxTypePageUrlPattern);
      });

      it('last delete button click should delete instance of TaxType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('taxType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', taxTypePageUrlPattern);

        taxType = undefined;
      });
    });
  });

  describe('new TaxType page', () => {
    beforeEach(() => {
      cy.visit(`${taxTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TaxType');
    });

    it('should create an instance of TaxType', () => {
      cy.get(`[data-cy="code"]`).type('huzzah unaccountably');
      cy.get(`[data-cy="code"]`).should('have.value', 'huzzah unaccountably');

      cy.get(`[data-cy="name"]`).type('or dune pfft');
      cy.get(`[data-cy="name"]`).should('have.value', 'or dune pfft');

      cy.get(`[data-cy="value"]`).type('25098.6');
      cy.get(`[data-cy="value"]`).should('have.value', '25098.6');

      cy.get(`[data-cy="notes"]`).type('since');
      cy.get(`[data-cy="notes"]`).should('have.value', 'since');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        taxType = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', taxTypePageUrlPattern);
    });
  });
});
