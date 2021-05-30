describe("free recommendation test", () => {

  it("visits free recommendation page", () => {
    cy.intercept('GET', '/api/movies/3/5', {
      fixture: 'movieList'
    }).as('getMovies');
    cy.visit("/");
    cy.contains("Free Recommendation").click();
    cy.wait('@getMovies');
  });

  it("rate movie", () => {
    cy.intercept('GET', '/api/actors/3/5', {
      fixture: 'actorList'
    }).as("getActors");
    cy.wait(500);
    cy.get('star-rating').eq(0).shadow().find('span.star:first').click();
    cy.wait(200);
    cy.get('star-rating').eq(1).shadow().find('span.star:first').click();
    cy.wait(200);
    cy.get('star-rating').eq(2).shadow().find('span.star:first').click();
    cy.wait(200);
  });

  it("rate actors and get the recommendations", () => {
    cy.intercept('POST', '/api/recommendations/', {
      fixture: 'recommendation'
    }).as("getRecommendation");
    cy.get('star-rating').eq(0).shadow().find('span.star:first').click();
    cy.wait(200);
    cy.get('star-rating').eq(1).shadow().find('span.star:first').click();
    cy.wait(200);
    cy.get('star-rating').eq(2).shadow().find('span.star:first').click();
    cy.wait(200);
  });

  it("filter the recommendations", () => {
    cy.get('.mat-focus-indicator').click();
    cy.get(':nth-child(1) > .ngx-slider > .ngx-slider-selection-bar > .ngx-slider-span').click();
    cy.get(':nth-child(3) > .genre-list > :nth-child(1) > :nth-child(1)').click();
    cy.get(':nth-child(4) > :nth-child(3) > [src="https://unpkg.com/language-icons@0.3.0/icons/en.svg"]').click();
    cy.get(':nth-child(9) > .ngx-slider > .ngx-slider-selection-bar > .ngx-slider-span').click();
    cy.get('.ml-2').click();
  });
});
