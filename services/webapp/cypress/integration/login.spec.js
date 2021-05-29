/// <reference types="Cypress" />

describe("member login test", () => {

  it.only("test login", () => {
    cy.visit("/");
    cy.contains("Custom Recommendation").click();

    cy.get('#mat-input-0').type("testuser");
    cy.get('#mat-input-1').type("123456");
    cy.contains("Sign-In").click();

    cy.intercept('POST', 'api/login', {
      fixture: 'user'
    })
      .as('login')
    cy.wait('@login');
    cy.intercept('GET', '/api/movies/3/5', {
      fixture: 'movieList'
    })
      .as('getMovies')
    cy.wait('@getMovies')
    cy.intercept('GET', 'api/recommendations/lastTrained', {
      fixture: 'localdatetime'
    })
      .as('lastTrained')
    cy.wait(6000)
    cy.wait('@lastTrained')
  })
});
