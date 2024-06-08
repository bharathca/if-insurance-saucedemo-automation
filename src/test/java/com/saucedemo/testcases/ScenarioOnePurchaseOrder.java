package com.saucedemo.testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class ScenarioOnePurchaseOrder extends BaseTest {
	Faker faker = new Faker();

	@Test
	public void purchaseMultipleProducts() {
		loginPage.performLogin("standard_user", "secret_sauce");
		Map<String, Map<String, String>> productsAddedInCart = productsPage.addProductsToCart(
				productsPage.getNamesOfAvailableProducts(), productsPage.getRandomNumberOfProducts(), true);
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
	}
}
