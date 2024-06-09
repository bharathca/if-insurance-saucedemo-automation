package com.saucedemo.testcases;

import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.saucedemo.constants.ApplicationConstants;

public class ScenarioTwoSortProductItems extends BaseTest {
	@Test
	public void sortProductItems() {
		Assert.assertTrue(commonPage.verifyApplicationPageTitle(ApplicationConstants.APPLICATION_PAGE_TITLE.getMessage()),
				"Sauce Demo Application Title is incorrect");
		loginPage.performLogin(properties.getProperty("valid.username"), properties.getProperty("valid.password"));
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.PRODUCTS_PAGE_HEADER.getMessage(),
				"Products page header is incorrect");
		// Define the sorting orders to test
		List<String> sortingOrders = List.of("Name (A to Z)", "Name (Z to A)", "Price (low to high)",
				"Price (high to low)");
		for (String sortingOrder : sortingOrders) {
			Map<String, Map<String, String>> productsAddedInCart = productsPage.sortAndValidate(sortingOrder);
			Assert.assertEquals(commonPage.getCartBadgeNumbers(), productsPage.getTotalNumberOfRandomProducts(),
					"The number of products in the cart icon does not match with the expected");
			commonPage.navigateToCartPage();
			Assert.assertTrue(cartPage.verifyProductsCountInCart(productsAddedInCart.size()),
					"The number of products in the cart page does not match with the expected");
			cartPage.verifyProductsInCart(productsAddedInCart);
			cartPage.navigateToCheckoutPage();

			checkoutInfoPage.enterCheckOutDetailsAndContinue(faker.name().firstName(), faker.name().lastName(),
					faker.address().zipCode());

			checkoutOverview.checkPriceInformations(productsAddedInCart);
			checkoutOverview.completePayment();
			
			checkoutCompletePage.isCompletedMessageDisplayed();
			
			checkoutCompletePage.navigateBackToHome();
		}
		
	}
}
