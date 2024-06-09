package com.saucedemo.stepDefinitions;

import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductsPageStepDefinition extends BaseTest {
	private BaseTest baseTest;

    public ProductsPageStepDefinition(BaseTest baseTest) {
        this.baseTest = baseTest;
    }
	
	Map<String, Map<String, String>> productsAddedInCart = null;
	
	@When("The customer adds multiple products to the shopping cart")
	public void theCustomerAddsMultipleProductsToTheShoppingCart() {
		productsAddedInCart = productsPage.addProductsToCart(
				productsPage.getNamesOfAvailableProducts(), productsPage.getRandomNumberOfProducts(), true);
		baseTest.setProductsAddedInCart(productsAddedInCart);
		Assert.assertEquals(commonPage.getCartBadgeNumbers(), productsPage.getTotalNumberOfRandomProducts(),
				"The number of products in the cart icon does not match with the expected");
	}
	@When("^The customer sorts available products in product view \"([^\"]*)\"$")
	public void theCustomerSortsAvailableProductsInProductView(String sortingOrder) {
		productsAddedInCart = productsPage.sortAndAddProductsInCart(sortingOrder);
		Assert.assertEquals(commonPage.getCartBadgeNumbers(), productsPage.getTotalNumberOfRandomProducts(),
				"The number of products in the cart icon does not match with the expected");
	}
	
	@Then("The products are ordered according to the chosen sort method")
	public void theProductsAreOrderedAccordingToTheChosenSortMethod() {
		commonPage.navigateToCartPage();
		Assert.assertTrue(cartPage.verifyProductsCountInCart(productsAddedInCart.size()),
				"The number of products in the cart page does not match with the expected");
		cartPage.verifyProductsInCart(productsAddedInCart);
		cartPage.navigateToCheckoutPage();

		checkoutInfoPage.enterCheckOutDetailsAndContinue(faker.name().firstName(), faker.name().lastName(),
				faker.address().zipCode());

		checkoutOverview.checkPriceInformations(productsAddedInCart);
		checkoutOverview.completePayment();

	}
}
