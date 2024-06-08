package com.saucedemo.pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.design.Locators;
import com.testng.base.CommonMethods;

public class CartPageObjects extends CommonMethods{

	//Locators - className
	private static final String totalNumberOfProductsInCartElement = "cart_item";
	private static final String productNameInCartElement = "inventory_item_name";
	private static final String productDescriptionInCartElement = "inventory_item_desc";
	private static final String productPriceInCartElement = "inventory_item_price";
	//Locator - xPath
	private static final String checkOutButtonElement = "//button[@data-test='checkout']";
	
	
	public CartPageObjects(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean verifyProductsCountInCart(Map<String, Map<String, String>> expectedProducts) {
		if(getElementSize(locateElements(Locators.CLASS_NAME, totalNumberOfProductsInCartElement))==
				expectedProducts.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void verifyProductsInCart(Map<String, Map<String, String>> expectedProducts) {
		List<WebElement> cartItems = locateElements(Locators.CLASS_NAME, totalNumberOfProductsInCartElement);
		for (WebElement eachCartItem : cartItems) {
			String productNameInCart = getElementText(locateElement(Locators.CLASS_NAME, eachCartItem, productNameInCartElement));
			String productDescriptionInCart = getElementText(locateElement(Locators.CLASS_NAME, eachCartItem, productDescriptionInCartElement));
			String productPriceInCart = getElementText(locateElement(Locators.CLASS_NAME, eachCartItem, productPriceInCartElement));
			
			if(expectedProducts.containsKey(productNameInCart)) {
				Map<String, String> productDetails = expectedProducts.get(productNameInCart);

				if (productDetails.get("price").equals(productPriceInCart)
						&& productDetails.get("description").equals(productDescriptionInCart)) {
					System.out.println("Product verified in cart: " + productNameInCart + " with price: " + productPriceInCart
							+ " and description: " + productDescriptionInCart);
				} else {
					System.err.println("Product verification failed for: " + productNameInCart + " with price: " + productPriceInCart
							+ " and description: " + productDescriptionInCart);
				}
			} else {
				System.err.println("Product not found in the expected products list: " + productNameInCart);
			}
		}
	}
	
	public void clickOnCheckOutButton() {
		click(locateElement(Locators.XPATH, checkOutButtonElement));
	}
	
}
