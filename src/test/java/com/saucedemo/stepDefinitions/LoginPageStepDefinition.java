package com.saucedemo.stepDefinitions;

import java.util.Properties;

import org.testng.Assert;

import com.saucedemo.constants.ApplicationConstants;
import com.saucedemo.utils.ConfigurationFileReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageStepDefinition extends BaseTest {
	private Properties properties;
	private String username;
	private String password;

	public LoginPageStepDefinition() {
		this.properties = ConfigurationFileReader.getInstance().getProperties();
	}

	@Given("Standard customer is logged in")
	public void standardCustomerIsLoggedIn() {
		Assert.assertTrue(
				commonPage.verifyApplicationPageTitle(ApplicationConstants.APPLICATION_PAGE_TITLE.getMessage()),
				"Sauce Demo Application Title is incorrect");
		loginPage.performLogin(properties.getProperty("valid.username"), properties.getProperty("valid.password"));
		Assert.assertEquals(commonPage.verifyLandingPageHeader(),
				ApplicationConstants.PRODUCTS_PAGE_HEADER.getMessage(), "Products page header is incorrect");
	}

	@Given("^Customer is a \"([^\"]*)\" customer$")
	public void customerIsALockedOutCustomer(String typeOfCustomer) {
		if (typeOfCustomer.equals("locked out")) {
			username = properties.getProperty("lockedOut.username");
			password = properties.getProperty("valid.password");
		} else if (typeOfCustomer.equals("invalid")) {
			username = faker.name().username();
			password = properties.getProperty("invalid.password");
		}
	}

	@And("^The customer attempts to login using (proper|invalid) credentials$")
	public void theCustomerAttemptsToLoginUsingProperCredentials(String typeOfCredentials) {
		loginPage.performLogin(username, password);
	}

	@Then("Login fails")
	public void loginFails() {
		Assert.assertTrue(commonPage.verifyErrorMessageDisplayed(),
				"User is logged in and landed in the Products Page");
	}

	@When("^The customer tries to (login|checkout) (without|with) entering \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and required error message displayed$")
	public void theCustomerTriedToLoginWithoutEntering(String page, String withOrWithOut, String inputFieldOne,
			String inputFieldTwo, String inputFieldThree) {

		if (page.equals("login")) {

			loginPage.clickLoginButton();

			commonPage.verifyRequiredOrLoginErrorMessage("username");

			loginPage.enterUserName(properties.getProperty("valid.username"));
			loginPage.clickLoginButton();

			commonPage.verifyRequiredOrLoginErrorMessage("password");
			loginPage.enterPassword(properties.getProperty("valid.password"));

		} else if (page.equals("checkout")) {
			commonPage.navigateToCartPage();
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
			
		}
	}
}
