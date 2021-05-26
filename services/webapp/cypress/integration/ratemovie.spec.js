/// <reference types="Cypress" />

describe("test movie rating", () => {

  it("ratemovie test", () => {
    cy.login()
    cy.get('app-rater.ng-tns-c146-5 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.intercept('POST', '/api/members/movieRating', {
      fixture: 'TheGrandBudapestHotel_rating'
    })
      .as("rateSecond")
    cy.wait("@rateSecond")
    cy.intercept('GET', '/api/movies/1/5', {
      fixture: 'oneMovie'
    })
      .as("getMovie")
    cy.wait("@getMovie")

  })

  it("not seen the first movie", () => {
    cy.get('app-rater.ng-tns-c146-4 > .w-100 > .mt-1 > .col-12 > .mat-focus-indicator > .mat-button-wrapper').click()
    cy.intercept('GET', '/api/movies/1/5', {
      fixture: 'oneMovie'
    })
      .as("getMovie")
    cy.wait("@getMovie")
  })

  it("rate the third movie", () => {
    cy.get('app-rater.ng-tns-c146-6 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.intercept('POST', '/api/members/movieRating', {
      fixture: 'TheUsualSuspects_rating'
    })
      .as("rateThird")
    cy.wait("@rateThird")
    cy.intercept('GET', '/api/movies/1/5', {
      fixture: 'oneMovie'
    })
      .as("getMovie")
    cy.wait("@getMovie")
  })

  it("change popularity", () => {
    cy.get('.ngx-slider-full-bar > .ngx-slider-span').click()
    cy.intercept('GET', '/api/movies/3/6', {
      fixture: 'MovieList'
    })
      .as("getMovie")
    cy.wait("@getMovie")
  })
});
