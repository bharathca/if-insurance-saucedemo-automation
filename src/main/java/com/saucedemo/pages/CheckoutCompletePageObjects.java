package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.CommonMethods;

public class CheckoutCompletePageObjects extends CommonMethods{

	public CheckoutCompletePageObjects(WebDriver driver) {
		this.driver = driver;
	}
	private static final String completedMessage = "//h2[@data-test='complete-header']";
	private static final String backToHome = "//button[@data-test='back-to-products']";
	
	public boolean isCompletedMessageDisplayed() {
		return verifyDisplayed(locateElement(Locators.XPATH, completedMessage));
	}
	
	public void navigateBackToHome() {
		click(locateElement(Locators.XPATH, backToHome));
	}
}
