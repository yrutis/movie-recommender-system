/// <reference types="Cypress" />

describe("actor rating test", () => {

  it.only("rate the actors", () => {
    cy.login()
    cy.get(':nth-child(2) > .nav-link').click()
    cy.intercept('GET', '/api/actors/3/5', {
      fixture: 'actorList'
    })
    cy.get('app-rater.ng-tns-c146-8 > .w-100 > .mt-1 > .col-12 > .float-left').click()
    cy.intercept('POST', '/api/members/actorRating', {
      fixture: 'actorRating'
    })
    cy.intercept('GET', '/api/actors/1/5', {
      fixture: 'oneActor'
    })

  })
});