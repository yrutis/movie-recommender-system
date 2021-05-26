/// <reference types="Cypress" />

describe("member recommendation test", () => {

  it.only("get the recommendation", () => {
    cy.login()
    cy.get(':nth-child(3) > .nav-link').click()
    cy.intercept('GET', '/api/recommendations', {
      fixture: 'recommendation'
    })
    cy.get(':nth-child(4) > .ng-star-inserted > :nth-child(2)').click()

    //Check the training recommendations
    cy.get('.col-12 > .pointer').click()
    cy.intercept('GET', '/api/recommendations/train', {
      fixture: 'recommendation'
    })
  })
});
