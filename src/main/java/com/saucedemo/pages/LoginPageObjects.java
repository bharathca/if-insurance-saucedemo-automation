package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.CommonMethods;

public class LoginPageObjects extends CommonMethods {
	// Locators - id
	private final String userNameField = "user-name";
	private final String passwordField = "password";
	private final String loginButton = "login-button";
	// Locators - xPath
	private final String loginError = "//h3[@data-test='error']";

	public LoginPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	public void enterUserName(String userName) {
		clearAndType(locateElement(Locators.ID, userNameField), userName);
	}

	public void enterPassword(String password) {
		clearAndType(locateElement(Locators.ID, passwordField), password);
	}

	public void clickLoginButton() {
		click(locateElement(Locators.ID, loginButton));
	}
	
	public boolean verifyUserIsLockedOut() {
		return verifyDisplayed(locateElement(Locators.XPATH, loginError));
		
	}

	public ProductsPageObjects performLogin(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickLoginButton();
		return new ProductsPageObjects(driver);
	}
}
