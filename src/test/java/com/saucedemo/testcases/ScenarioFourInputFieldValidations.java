package com.saucedemo.testcases;

import org.testng.annotations.Test;
import java.util.Map;

import org.testng.Assert;


public class ScenarioFourInputFieldValidations extends BaseTest {

	@Test
	public void loginRequiredInputFields() {
		loginPage.clickLoginButton();
		commonPage.verifyRequiredOrLoginErrorMessage("username");
		loginPage.enterUserName(properties.getProperty("valid.username"));
		loginPage.clickLoginButton();
		commonPage.verifyRequiredOrLoginErrorMessage("password");
		loginPage.enterPassword(properties.getProperty("valid.password"));
	}

	@Test
	public void checkoutInformationPageInputFields() {

		loginPage.performLogin(properties.getProperty("valid.username"), properties.getProperty("valid.password"));
		Map<String, Map<String, String>> productsAddedInCart = productsPage.addProductsToCart(
				productsPage.getNamesOfAvailableProducts(), productsPage.getRandomNumberOfProducts(), true);
		Assert.assertEquals(commonPage.getCartBadgeNumbers(), productsPage.getTotalNumberOfRandomProducts(),
				"The number of products in the cart icon does not match with the expected");
		commonPage.navigateToCartPage();
		Assert.assertTrue(cartPage.verifyProductsCountInCart(productsAddedInCart.size()),
				"The number of products in the cart page does not match with the expected");
		cartPage.verifyProductsInCart(productsAddedInCart);
		cartPage.navigateToCheckoutPage();
		checkoutInfoPage.navigateToCheckoutPageTwo();

		commonPage.verifyRequiredOrLoginErrorMessage("firstname");
		checkoutInfoPage.enterFirstName(faker.name().firstName());
		checkoutInfoPage.navigateToCheckoutPageTwo();
		commonPage.verifyRequiredOrLoginErrorMessage("lastname");

		checkoutInfoPage.enterLastName(faker.name().lastName());
		checkoutInfoPage.navigateToCheckoutPageTwo();
		commonPage.verifyRequiredOrLoginErrorMessage("postalCode");

		checkoutInfoPage.enterPostalCode(faker.address().zipCode());
		checkoutInfoPage.navigateToCheckoutPageTwo();
		// Assertions required
	}
}
