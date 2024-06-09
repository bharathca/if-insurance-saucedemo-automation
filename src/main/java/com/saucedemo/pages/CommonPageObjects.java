package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.saucedemo.constants.ApplicationConstants;
import com.selenium.design.Locators;
import com.testng.base.ApplicationSpecificMethods;

public class CommonPageObjects extends ApplicationSpecificMethods {

	// Locators - xPath
	private final static String cartIcon = "//a[@data-test='shopping-cart-link']";
	private final static String cartIconBadge = "//span[@data-test='shopping-cart-badge']";
	private final static String inputFieldRequiredErrorMessage = "//h3[@data-test='error']";
	private final static String pageHeader = "//span[@data-test='title']";

	
	public CommonPageObjects(WebDriver driver) {
		this.driver = driver;
	}
	
	public void navigateToCartPage() {
		click(locateElement(Locators.XPATH, cartIcon));
		logInfo("Navigated to the Cart page");
	}

	public int getCartBadgeNumbers() {
		return Integer.valueOf(getElementText(locateElement(Locators.XPATH, cartIconBadge)));
	}

	public boolean verifyRequiredOrLoginErrorMessage(String inputField) {
		String actualErrorMessage = getElementText(locateElement(Locators.XPATH, inputFieldRequiredErrorMessage));
		String expectedErrorMessage = null;

		switch (inputField) {
		case "username":
			expectedErrorMessage = ApplicationConstants.USERNAME_REQUIRED.getMessage();
			break;
		case "password":
			expectedErrorMessage = ApplicationConstants.PASSWORD_REQUIRED.getMessage();
			break;
		case "lockedOutUser":
			expectedErrorMessage = ApplicationConstants.LOCKED_OUT_USER.getMessage();
			break;
		case "firstname":
			expectedErrorMessage = ApplicationConstants.FIRST_NAME_REQUIRED.getMessage();
			break;
		case "lastname":
			expectedErrorMessage = ApplicationConstants.LAST_NAME_REQUIRED.getMessage();
			break;
		case "postalCode":
			expectedErrorMessage = ApplicationConstants.POSTAL_CODE_REQUIRED.getMessage();
			break;
		case "incorrectUser":
			expectedErrorMessage = ApplicationConstants.INCORRECT_USER.getMessage();
			break;
		default:
			logError("Given input field "+inputField+" is incorrect for the verification of input field");
			break;
		}

		if (actualErrorMessage.equals(expectedErrorMessage)
				&& verifyDisplayed(locateElement(Locators.XPATH, inputFieldRequiredErrorMessage))) {
			logInfo(expectedErrorMessage +" is displayed");
			return true;
		} else {
			logError(expectedErrorMessage +" is not displayed");
			return false;
		}
	}
	
	public boolean verifyErrorMessageDisplayed() {
		return verifyDisplayed(locateElement(Locators.XPATH, inputFieldRequiredErrorMessage));
	}

	public boolean verifyLandingPageURL(String expectedURL) {
		return verifyUrl(expectedURL);
	}

	public String verifyLandingPageHeader() {
		return getElementText(locateElement(Locators.XPATH, pageHeader));
	}
	
	public boolean verifyApplicationPageTitle(String applicationPageTitle) {
		return verifyTitle(applicationPageTitle);
	}
}
