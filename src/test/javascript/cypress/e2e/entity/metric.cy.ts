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

describe('Metric e2e test', () => {
  const metricPageUrl = '/metric';
  const metricPageUrlPattern = new RegExp('/metric(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const metricSample = { name: 'fill' };

  let metric;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/metrics+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/metrics').as('postEntityRequest');
    cy.intercept('DELETE', '/api/metrics/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (metric) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/metrics/${metric.id}`,
      }).then(() => {
        metric = undefined;
      });
    }
  });

  it('Metrics menu should load Metrics page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('metric');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Metric').should('exist');
    cy.url().should('match', metricPageUrlPattern);
  });

  describe('Metric page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(metricPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Metric page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/metric/new$'));
        cy.getEntityCreateUpdateHeading('Metric');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/metrics',
          body: metricSample,
        }).then(({ body }) => {
          metric = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/metrics+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [metric],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(metricPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Metric page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('metric');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricPageUrlPattern);
      });

      it('edit button click should load edit Metric page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Metric');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricPageUrlPattern);
      });

      it('edit button click should load edit Metric page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Metric');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricPageUrlPattern);
      });

      it('last delete button click should delete instance of Metric', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('metric').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricPageUrlPattern);

        metric = undefined;
      });
    });
  });

  describe('new Metric page', () => {
    beforeEach(() => {
      cy.visit(`${metricPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Metric');
    });

    it('should create an instance of Metric', () => {
      cy.get(`[data-cy="name"]`).type('wince');
      cy.get(`[data-cy="name"]`).should('have.value', 'wince');

      cy.get(`[data-cy="notes"]`).type('outside');
      cy.get(`[data-cy="notes"]`).should('have.value', 'outside');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        metric = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', metricPageUrlPattern);
    });
  });
});
