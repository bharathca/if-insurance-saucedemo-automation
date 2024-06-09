package com.saucedemo.testcases;

import org.testng.annotations.Test;

public class ScenarioThreeLockedOutUser extends BaseTest {

	@Test
	public void loginWithLockedOutCredentials() {
		loginPage.performLogin(properties.getProperty("lockedOut.username"), properties.getProperty("valid.password"));
		loginPage.performLogin("locked_out_user", "secret_sauce");
		commonPage.verifyRequiredOrLoginErrorMessage("lockedOutUser");
	}
	
	@Test
	public void loginWithIncorrectCredentials() {
		loginPage.performLogin(faker.name().username(), properties.getProperty("invalid.password"));
		loginPage.performLogin("wrongUserName", "wrongPassword");
		commonPage.verifyRequiredOrLoginErrorMessage("incorrectUser");
	}
}
