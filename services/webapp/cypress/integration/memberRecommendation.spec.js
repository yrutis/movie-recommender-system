/// <reference types="Cypress" />

describe("member recommendation test", () => {

  it.only("get the recommendation", () => {
    cy.login();
    cy.intercept('GET', '/api/recommendations', {
      fixture: 'recommendation'
    }).as('getRecommendation');

    cy.get(':nth-child(3) > .nav-link').click();
    cy.wait('@getRecommendation');
    cy.get(':nth-child(4) > .ng-star-inserted > :nth-child(2)').click();

  })
});
