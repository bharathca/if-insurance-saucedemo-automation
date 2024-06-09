package com.saucedemo.pages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.selenium.design.Locators;
import com.testng.base.ApplicationSpecificMethods;

public class CheckoutOverviewPageObjects extends ApplicationSpecificMethods {
	// Locators - xPath
	private static final String sauceCardElement = "//div[@data-test='payment-info-value']";
	private static final String totalPriceOfItemsElement = "//div[@data-test='subtotal-label']";
	private static final String taxElement = "//div[@data-test='tax-label']";
	private static final String totalPriceOfItemsInclusiveOfTaxElement = "//div[@data-test='total-label']";

	// Locator - id
	private static final String completePayment = "finish";

	// Variables for calculations & verifications
	private String sauceCard;
	private BigDecimal totalPriceOfItems = BigDecimal.ZERO;
	private BigDecimal tax = BigDecimal.ZERO;
	private BigDecimal totalPriceOfItemsInclusiveOfTax = BigDecimal.ZERO;
	private BigDecimal expectedTotalPriceOfItemsWithoutTax = BigDecimal.ZERO;

	public CheckoutOverviewPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	public void setSauceCardText() {
		sauceCard = getElementText(locateElement(Locators.XPATH, sauceCardElement));
	}

	public void setTotalPriceOfItems() {
		totalPriceOfItems = convertToBigDecimal(getElementText(locateElement(Locators.XPATH, totalPriceOfItemsElement)));
	}

	public void setTax() {
		tax = convertToBigDecimal(getElementText(locateElement(Locators.XPATH, taxElement)));
	}

	public void setTotalPriceOfItemsInclusiveOfTax() {
		totalPriceOfItemsInclusiveOfTax = convertToBigDecimal(getElementText(locateElement(Locators.XPATH, totalPriceOfItemsInclusiveOfTaxElement)));
	}

	public void checkPriceInformations(Map<String, Map<String, String>> addedProducts) {
		setSauceCardText();
		setTotalPriceOfItems();
		setTax();
		setTotalPriceOfItemsInclusiveOfTax();

		// Initialize expectedTotalPriceOfItemsWithoutTax to 0
		expectedTotalPriceOfItemsWithoutTax = BigDecimal.ZERO;

		for (Map.Entry<String, Map<String, String>> entry : addedProducts.entrySet()) {
			expectedTotalPriceOfItemsWithoutTax = expectedTotalPriceOfItemsWithoutTax.add(
	                convertToBigDecimal(entry.getValue().get("price").replace("$","")));
		}

		BigDecimal expectedTax = totalPriceOfItemsInclusiveOfTax.subtract(expectedTotalPriceOfItemsWithoutTax)
				 .setScale(2, RoundingMode.HALF_UP);
;

		Assert.assertTrue(sauceCard.matches("SauceCard #\\d+"),
				"Sauce Card information contains alphebets instead of numbers");
		logInfo("Sauce Card Information contains the number");

		assertBigDecimalEquals(totalPriceOfItems, expectedTotalPriceOfItemsWithoutTax, "Total price of items without tax is incorrect");
		logInfo("Total price of items without tax is correct");

		assertBigDecimalEquals(tax, expectedTax, "Tax is incorrect");
		logInfo("Tax is correct");
	}

	public void completePayment() {
		click(locateElement(Locators.ID, completePayment));
		logInfo("Navigated to Checkout Page - Complete");
	}
	
	private BigDecimal convertToBigDecimal(String value) {
        return new BigDecimal(value.replaceAll("[^\\d.]", "")).setScale(2, RoundingMode.HALF_UP);
    }

    private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual, String message) {
        if (expected.compareTo(actual) != 0) {
            Assert.fail(message + " expected [" + expected + "] but found [" + actual + "]");
        }
    }
}
