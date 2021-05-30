/// <reference types="Cypress" />

describe("test movie rating", () => {

  it("ratemovie test", () => {
    cy.login();
    cy.intercept('POST', '/api/members/movieRating', {
      fixture: 'TheGrandBudapestHotel_rating'
    }).as("rateSecond");
    cy.intercept('GET', '/api/movies/1/5', {
      fixture: 'oneMovie'
    }).as("getMovie");
    cy.get('star-rating').eq(1).shadow().find('span.star:first').click();

  })
});
