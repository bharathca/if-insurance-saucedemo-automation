package com.saucedemo.testcases;

import org.testng.annotations.Test;
import java.util.Map;

import org.testng.Assert;

import com.saucedemo.constants.ApplicationConstants;

public class ScenarioOnePurchaseOrder extends BaseTest {

	@Test
	public void purchaseMultipleProducts() {
		Assert.assertTrue(commonPage.verifyApplicationPageTitle(ApplicationConstants.APPLICATION_PAGE_TITLE.getMessage()),
				"Sauce Demo Application Title is incorrect");
		loginPage.performLogin(properties.getProperty("valid.username"), properties.getProperty("valid.password"));
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.PRODUCTS_PAGE_HEADER.getMessage(),
				"Products page header is incorrect");
		Map<String, Map<String, String>> productsAddedInCart = productsPage.addProductsToCart(
				productsPage.getNamesOfAvailableProducts(), productsPage.getRandomNumberOfProducts(), true);
		Assert.assertEquals(commonPage.getCartBadgeNumbers(), productsPage.getTotalNumberOfRandomProducts(),
				"The number of products in the cart icon does not match with the expected");
		commonPage.navigateToCartPage();
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CART_PAGE_HEADER.getMessage(),
				"Cart page header is incorrect");
		Assert.assertTrue(cartPage.verifyProductsCountInCart(productsAddedInCart.size()),
				"The number of products in the cart page does not match with the expected");
		cartPage.verifyProductsInCart(productsAddedInCart);
		cartPage.navigateToCheckoutPage();
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CHECKOUT_STEP_ONE_PAGE_HEADER.getMessage(),
				"Checkout step one page header is incorrect");
		checkoutInfoPage.enterCheckOutDetailsAndContinue(faker.name().firstName(), faker.name().lastName(),
				faker.address().zipCode());
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CHECKOUT_STEP_TWO_PAGE_HEADER.getMessage(),
				"Checkout step two page header is incorrect");
		checkoutOverview.checkPriceInformations(productsAddedInCart);
		checkoutOverview.completePayment();
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CHECKOUT_COMPLETE_PAGE_HEADER.getMessage(),
				"Checkout complete page header is incorrect");
		Assert.assertTrue(checkoutCompletePage.isCompletedMessageDisplayed(),"Payment is not successfully paid and completed message is not displayed");
	}
}
