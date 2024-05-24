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

describe('Regency e2e test', () => {
  const regencyPageUrl = '/regency';
  const regencyPageUrlPattern = new RegExp('/regency(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const regencySample = {"name":"virtuous pfft"};

  let regency;
  // let province;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/provinces',
      body: {"name":"password"},
    }).then(({ body }) => {
      province = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/regencies+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/regencies').as('postEntityRequest');
    cy.intercept('DELETE', '/api/regencies/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/provinces', {
      statusCode: 200,
      body: [province],
    });

  });
   */

  afterEach(() => {
    if (regency) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/regencies/${regency.id}`,
      }).then(() => {
        regency = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (province) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/provinces/${province.id}`,
      }).then(() => {
        province = undefined;
      });
    }
  });
   */

  it('Regencies menu should load Regencies page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('regency');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Regency').should('exist');
    cy.url().should('match', regencyPageUrlPattern);
  });

  describe('Regency page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(regencyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Regency page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/regency/new$'));
        cy.getEntityCreateUpdateHeading('Regency');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', regencyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/regencies',
          body: {
            ...regencySample,
            province: province,
          },
        }).then(({ body }) => {
          regency = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/regencies+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/regencies?page=0&size=20>; rel="last",<http://localhost/api/regencies?page=0&size=20>; rel="first"',
              },
              body: [regency],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(regencyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(regencyPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Regency page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('regency');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', regencyPageUrlPattern);
      });

      it('edit button click should load edit Regency page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Regency');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', regencyPageUrlPattern);
      });

      it('edit button click should load edit Regency page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Regency');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', regencyPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Regency', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('regency').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', regencyPageUrlPattern);

        regency = undefined;
      });
    });
  });

  describe('new Regency page', () => {
    beforeEach(() => {
      cy.visit(`${regencyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Regency');
    });

    it.skip('should create an instance of Regency', () => {
      cy.get(`[data-cy="name"]`).type('seaplane justly ick');
      cy.get(`[data-cy="name"]`).should('have.value', 'seaplane justly ick');

      cy.get(`[data-cy="province"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        regency = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', regencyPageUrlPattern);
    });
  });
});
