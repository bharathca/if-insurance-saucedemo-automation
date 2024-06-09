Feature: SauceDemo Application Test

  Scenario: Customer purchases multiple products
    Given Standard customer is logged in
    When The customer adds multiple products to the shopping cart
    And Proceeds to checkout the purchase
    Then Purchase is successful

  Scenario Outline: Customer sorts product items
    Given Standard customer is logged in
    When The customer sorts available products in product view "<sortingOrders>"
    Then The products are ordered according to the chosen sort method

    Examples: 
      | sortingOrders       |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |

  Scenario: User is locked out from the platform
    Given Customer is a "locked out" customer
    When The customer attempts to login using proper credentials
    Then Login fails
    And The customer is presented with error state as "lockedOutUser"

  Scenario: Validations on all the input fields in the platform
    Given The customer tries to login without entering "username" and "password" and "" and required error message displayed
    When Standard customer is logged in
    And The customer adds multiple products to the shopping cart
    And The customer tries to checkout without entering "firstname" and "lastname" and "postal code" and required error message displayed
