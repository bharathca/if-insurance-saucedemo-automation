package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.CommonMethods;

public class CheckoutYourInformationPageObjects extends CommonMethods {

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
	}
	
	public void enterLastName(String lastNameData) {
		clearAndType(locateElement(Locators.ID, lastNameField), lastNameData);
	}
	
	public void enterPostalCode(String postalCodeData) {
		clearAndType(locateElement(Locators.ID, postalCodeField), postalCodeData);
	}
	
	public void clickOnContinue() {
		click(locateElement(Locators.ID, continueButton));
	}
	
	public void enterCheckOutDetailsAndContinue(String firstNameData, String lastNameData, String postalCodeData) {
		enterFirstName(firstNameData);
		enterLastName(lastNameData);
		enterPostalCode(postalCodeData);
		clickOnContinue();
	}
}
