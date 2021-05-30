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

  cy.window().then((win) => {
    win.sessionStorage.clear()
  })
  cy.intercept('POST', 'api/login', {
    fixture: 'user'
  }).as('login')
  cy.intercept('GET', '/api/movies/3/5', {
    fixture: 'movieList'
  }).as('getMovies')
  cy.visit("/");

  cy.contains("Custom Recommendation").click();
  cy.get('#mat-input-0').type("testuser");
  cy.get('#mat-input-1').type("123456");
  cy.contains("Sign-In").click();

  cy.wait('@getMovies');
  cy.get('p.text');
})

