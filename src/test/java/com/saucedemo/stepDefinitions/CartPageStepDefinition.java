package com.saucedemo.stepDefinitions;

import org.testng.Assert;

import com.saucedemo.constants.ApplicationConstants;

import io.cucumber.java.en.And;

public class CartPageStepDefinition extends BaseTest{
	
	private BaseTest baseTest;

    public CartPageStepDefinition(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

	@And("Proceeds to checkout the purchase")
	public void proceedsToCheckoutThePurchase() {
		commonPage.navigateToCartPage();
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CART_PAGE_HEADER.getMessage(),
				"Cart page header is incorrect");
		Assert.assertTrue(cartPage.verifyProductsCountInCart(baseTest.getProductsAddedInCart().size()),
				"The number of products in the cart page does not match with the expected");
		cartPage.verifyProductsInCart(baseTest.getProductsAddedInCart());
		cartPage.navigateToCheckoutPage();
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CHECKOUT_STEP_ONE_PAGE_HEADER.getMessage(),
				"Checkout step one page header is incorrect");
		checkoutInfoPage.enterCheckOutDetailsAndContinue(faker.name().firstName(), faker.name().lastName(),
				faker.address().zipCode());
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CHECKOUT_STEP_TWO_PAGE_HEADER.getMessage(),
				"Checkout step two page header is incorrect");
		checkoutOverview.checkPriceInformations(baseTest.getProductsAddedInCart());
		checkoutOverview.completePayment();
	}
}
