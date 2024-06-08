package com.saucedemo.testcases;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class ScenarioTwoSortProductItems extends BaseTest {
	Faker faker = new Faker();
	@Test
	public void sortProductItems() {
		loginPage.performLogin("standard_user", "secret_sauce");
		// Define the sorting orders to test
		List<String> sortingOrders = List.of("Name (A to Z)", "Name (Z to A)", "Price (low to high)",
				"Price (high to low)");
		for (String sortingOrder : sortingOrders) {
			Map<String, Map<String, String>> productsAddedInCart = productsPage.sortAndValidate(sortingOrder);
			Assert.assertEquals(commonPage.getCartBadgeNumbers(), productsPage.getTotalNumberOfRandomProducts(),
					"The number of products in the cart icon does not match with the expected");
			commonPage.navigateToCartPage();
			Assert.assertTrue(cartPage.verifyProductsCountInCart(productsAddedInCart),
					"The number of products in the cart page does not match with the expected");
			cartPage.verifyProductsInCart(productsAddedInCart);
			cartPage.clickOnCheckOutButton();

			checkoutInfoPage.enterCheckOutDetailsAndContinue(faker.name().firstName(), faker.name().lastName(),
					faker.address().zipCode());

			checkoutOverview.checkPriceInformations(productsAddedInCart);
			checkoutOverview.completePayment();
			
			checkoutCompletePage.isCompletedMessageDisplayed();
			
			checkoutCompletePage.navigateBackToHome();
		}
		
	}
}
