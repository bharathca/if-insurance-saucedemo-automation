package com.saucedemo.stepDefinitions;

import org.testng.Assert;

import com.saucedemo.constants.ApplicationConstants;

import io.cucumber.java.en.Then;

public class CheckoutPagesStepDefinition extends BaseTest {

	@Then("Purchase is successful")
	public void purchaseIsSuccessful() {
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),ApplicationConstants.CHECKOUT_COMPLETE_PAGE_HEADER.getMessage(),
				"Checkout complete page header is incorrect");
		Assert.assertTrue(checkoutCompletePage.isCompletedMessageDisplayed(),"Payment is not successfully paid and completed message is not displayed");
	}
}
