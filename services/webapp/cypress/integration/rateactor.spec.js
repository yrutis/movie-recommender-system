/// <reference types="Cypress" />

describe("actor rating test", () => {

  it.only("rate the actors", () => {
    cy.login();
    cy.intercept('GET', '/api/actors/3/5', {
      fixture: 'actorList'
    }).as('getActors');
    cy.intercept('POST', '/api/members/actorRating', {
      fixture: 'actorRating'
    }).as('rateActor');
    cy.intercept('GET', '/api/actors/1/5', {
      fixture: 'oneActor'
    }).as('getActor');

    cy.get(':nth-child(2) > .nav-link').click();
    cy.wait('@getActors');
    cy.get('star-rating').eq(0).shadow().find('span.star:first').click();
  })
});
