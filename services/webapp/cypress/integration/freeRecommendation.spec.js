/// <reference types="Cypress" />

describe("free recommendation test", () => {

  it("visits free recommendation page", () => {
    cy.visit("/");
    cy.contains("Free Recommendation").click();
    cy.intercept('GET', '/api/movies/3/5', {
      fixture: 'movieList'
    })
      .as('getMovies')
    cy.wait('@getMovies')
  });

  it("rate movie", () => {
    cy.get('app-rater.ng-tns-c146-1 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.get('app-rater.ng-tns-c146-2 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.get('app-rater.ng-tns-c146-3 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.intercept('GET', '/api/actors/3/5', {
      fixture: 'actorList'
    })
      .as("getActors")
    cy.wait("@getActors")
  });

  it("rate actors and get the recommendations", () => {
    cy.get('app-rater.ng-tns-c146-4 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.get('app-rater.ng-tns-c146-5 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.get('app-rater.ng-tns-c146-6 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.intercept('POST', '/api/recommendations/', {
      fixture: 'recommendation'
    })
      .as("getRecommendation")
    cy.wait("@getRecommendation")
  });

  it("filter the recommendations", () => {
    cy.get('.mat-focus-indicator').click()
    cy.get(':nth-child(1) > .ngx-slider > .ngx-slider-selection-bar > .ngx-slider-span').click()
    cy.get(':nth-child(3) > .genre-list > :nth-child(1) > :nth-child(1)').click()
    cy.get(':nth-child(4) > :nth-child(3) > [src="https://unpkg.com/language-icons@0.3.0/icons/en.svg"]').click()
    cy.get(':nth-child(9) > .ngx-slider > .ngx-slider-selection-bar > .ngx-slider-span').click()
    cy.get('.ml-2').click()
  });
});
