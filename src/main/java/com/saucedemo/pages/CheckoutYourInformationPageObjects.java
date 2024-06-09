package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.ApplicationSpecificMethods;

public class CheckoutYourInformationPageObjects extends ApplicationSpecificMethods {

	//Locators - id
	private static String firstNameField = "first-name";
	private static String lastNameField = "last-name";
	private static String postalCodeField = "postal-code";
	private static String continueButton = "continue";
	
	public CheckoutYourInformationPageObjects(WebDriver  driver) {
		this.driver = driver;
	}
	
	public void enterFirstName(String firstNameData) {
		clearAndType(locateElement(Locators.ID, firstNameField), firstNameData);
		logInfo("First name is entered");
	}
	
	public void enterLastName(String lastNameData) {
		clearAndType(locateElement(Locators.ID, lastNameField), lastNameData);
		logInfo("Last name is entered");
	}
	
	public void enterPostalCode(String postalCodeData) {
		clearAndType(locateElement(Locators.ID, postalCodeField), postalCodeData);
		logInfo("Postal Code is entered");
	}
	
	public void navigateToCheckoutPageTwo() {
		click(locateElement(Locators.ID, continueButton));
		logInfo("Navigated to Checkout Page - Step Two");
	}
	
	public void enterCheckOutDetailsAndContinue(String firstNameData, String lastNameData, String postalCodeData) {
		enterFirstName(firstNameData);
		enterLastName(lastNameData);
		enterPostalCode(postalCodeData);
		navigateToCheckoutPageTwo();
	}
	
}
