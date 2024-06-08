package com.saucedemo.testcases;

import org.testng.annotations.Test;

public class ScenarioThreeLockedOutUser extends BaseTest {

	@Test
	public void loginWithLockedOutUser() {
		loginPage.performLogin("locked_out_user", "secret_sauce");
		loginPage.verifyUserIsLockedOut();
	}
}
