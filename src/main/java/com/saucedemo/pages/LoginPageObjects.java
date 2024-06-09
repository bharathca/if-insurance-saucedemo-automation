package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.ApplicationSpecificMethods;

public class LoginPageObjects extends ApplicationSpecificMethods {
	// Locators - id
	private final String userNameField = "user-name";
	private final String passwordField = "password";
	private final String loginButton = "login-button";

	public LoginPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	public void enterUserName(String userName) {
		clearAndType(locateElement(Locators.ID, userNameField), userName);
		logInfo("Username is entered");
	}

	public void enterPassword(String password) {
		clearAndType(locateElement(Locators.ID, passwordField), password);
		logInfo("Password is entered");
	}

	public void clickLoginButton() {
		click(locateElement(Locators.ID, loginButton));
		logInfo("Login button is clicked");
	}
	
	public void performLogin(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickLoginButton();
	}
}
