package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

import com.selenium.design.Locators;
import com.testng.base.CommonMethods;

public class CommonPageObjects extends CommonMethods {

	private static final String cartIcon = "//a[@data-test='shopping-cart-link']";
	private static final String cartIconBadge = "//span[@data-test='shopping-cart-badge']";
	
	public CommonPageObjects(WebDriver driver) {
		this.driver = driver;
	}
	
	public void navigateToCartPage() {
		click(locateElement(Locators.XPATH, cartIcon));
	}
	
	public int getCartBadgeNumbers() {
		return Integer.valueOf(getElementText(locateElement(Locators.XPATH, cartIconBadge)));
	}	
	
}
