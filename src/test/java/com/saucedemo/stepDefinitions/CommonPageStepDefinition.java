package com.saucedemo.stepDefinitions;

import io.cucumber.java.en.Then;

public class CommonPageStepDefinition extends BaseTest {

	@Then("^The customer is presented with error state as \"([^\"]*)\"$")
	public void theCustomerIsPresentWithErrorState(String errorState) {
		commonPage.verifyRequiredOrLoginErrorMessage(errorState);
	}
}
