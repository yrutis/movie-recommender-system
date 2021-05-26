// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
Cypress.Commands.add('login', () => {
  cy.visit("/");
  cy.contains("Custom Recommendation").click();
  cy.get('#mat-input-0').type("testuser");
  cy.get('#mat-input-1').type("123456");
  cy.contains("Sign-In").click();
  //cy.hash().should('eq', '#/')
  cy.intercept('POST', 'api/login', {
    fixture: 'user'
  })
    .as('login')
  cy.wait('@login');
  cy.intercept('GET', 'api/recommendations/lastTrained', {
    fixture: 'localdatetime'
  })
    .as('lastTrained')
  cy.wait('@lastTrained')
  cy.intercept('GET', '/api/movies/3/5', {
    fixture: 'movieList'
  })
    .as('getMovies')
  cy.wait('@getMovies')
  })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
