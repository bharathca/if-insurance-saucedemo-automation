package com.saucedemo.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.CommonMethods;

public class CheckoutOverviewPageObjects extends CommonMethods {
	// Locators - xPath
	private static final String sauceCardElement = "//div[@data-test='payment-info-value']";
	private static final String totalPriceOfItemsElement = "//div[@data-test='subtotal-label']";
	private static final String taxElement = "//div[@data-test='tax-label']";
	private static final String totalPriceOfItemsInclusiveOfTaxElement = "//div[@data-test='total-label']";
	
	//Locator - id 
	private static final String completePayment  = "finish";

	// Variables for calculations & verifications
	private String sauceCard;
	private float totalPriceOfItems;
	private float tax;
	private float totalPriceOfItemsInclusiveOfTax;
	private float expectedTotalPriceOfItemsWithoutTax;

	public CheckoutOverviewPageObjects(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setSauceCardText() {
		sauceCard = getElementText(locateElement(Locators.XPATH, sauceCardElement));
	}
	
	public void setTotalPriceOfItems() {
		totalPriceOfItems = Float.parseFloat(getElementText(locateElement(Locators.XPATH, totalPriceOfItemsElement)).replaceAll("[^\\d.]", ""));
	}
	
	public void setTax() {
		tax = Float.parseFloat(getElementText(locateElement(Locators.XPATH, taxElement)).replaceAll("[^\\d.]", ""));
	}
	
	public void setTotalPriceOfItemsInclusiveOfTax() {
		totalPriceOfItemsInclusiveOfTax = Float.parseFloat(getElementText(locateElement(Locators.XPATH, totalPriceOfItemsInclusiveOfTaxElement)).replaceAll("[^\\d.]", ""));
	}
	
	public void checkPriceInformations(Map<String, Map<String, String>> addedProducts) {
		setSauceCardText();
		setTotalPriceOfItems();
		setTax();
		setTotalPriceOfItemsInclusiveOfTax();
		
		for (Map.Entry<String, Map<String, String>> entry : addedProducts.entrySet()) {
			expectedTotalPriceOfItemsWithoutTax += Float.parseFloat(entry.getValue().get("price").replace("$", ""));
		}
		double expectedTax = totalPriceOfItemsInclusiveOfTax - expectedTotalPriceOfItemsWithoutTax;
		 if(sauceCard.matches("SauceCard #\\d+") && (totalPriceOfItems==expectedTotalPriceOfItemsWithoutTax) && (expectedTax == tax)) {
			 System.out.println("All price validations are correct");
		 } else {
			 System.err.println("There is an incorrect outcome on the price validations");
		 }
	}
	
	public void completePayment() {
		click(locateElement(Locators.ID, completePayment));
	}
}
