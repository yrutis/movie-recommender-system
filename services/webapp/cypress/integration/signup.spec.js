describe("signup test", () => {

  it("signup test", () => {
    cy.visit("/");
    cy.intercept('GET', '/api/signup/checkUsernameAvailable/*', {
      body: true
    });
    cy.intercept('POST', '/api/signup', {
      fixture: 'signup'
    }).as('signup');
    cy.contains("Custom Recommendation").click();
    cy.contains("Create Account").click();
    cy.get('#mat-input-2').type("testuser");

    cy.get('#mat-input-3').type("123456");
    cy.get('#mat-input-4').type("123456");
    cy.contains("Create!").click();

  })
});
